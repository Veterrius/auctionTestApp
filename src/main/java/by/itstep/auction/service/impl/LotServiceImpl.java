package by.itstep.auction.service.impl;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.User;
import by.itstep.auction.dao.model.enums.LotType;
import by.itstep.auction.dao.repository.ItemRepository;
import by.itstep.auction.dao.repository.LotRepository;
import by.itstep.auction.service.exceptions.*;
import by.itstep.auction.service.threads.LotAutoSellerThread;
import by.itstep.auction.service.LotService;
import by.itstep.auction.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LotServiceImpl implements LotService {

    private final Logger l = LoggerFactory.getLogger(LotServiceImpl.class);

    private final LotRepository lotRepository;
    private final ItemRepository itemRepository;
    private final UserService userService;

    public LotServiceImpl(LotRepository lotRepository, ItemRepository itemRepository, UserService userService) {
        this.lotRepository = lotRepository;
        this.itemRepository = itemRepository;
        this.userService = userService;
    }

    @Override
    public Lot findLotByItemAndType(Item item, LotType lotType) {
        return lotRepository.findLotByItemAndLotType(item, lotType);
    }

    @Override
    public Optional<Lot> findLotById(Long id) {
        return lotRepository.findLotById(id);
    }

    @Override
    public Lot createLot(Lot lot) {
        return lotRepository.save(lot);
    }

    @Override
    public void deleteLotById(Long id) {
        Lot lotToDelete = lotRepository.findLotById(id).orElseThrow();
        lotRepository.delete(lotToDelete);
    }

    @Override
    public void deleteLot(Lot lot) {
        lotRepository.delete(lot);
    }

    @Override
    public Iterable<Lot> findAllLots() {
        return lotRepository.findAll();
    }

    private void validateLot(Lot lot, Long validity) {
        if (!(lot.getLotType().equals(LotType.LOBBY) || lot.getLotType().equals(LotType.DYNAMIC)
        || lot.getLotType().equals(LotType.STATIC))) {
            throw new LotException("Invalid Lot Type");
        }
        if (validity <= 30) {
            throw new LotException("Validity must be 30 minutes or higher");
        }
        Iterable<Lot> allLots = findAllLots();
        for (Lot lotFromDb : allLots) {
            if (lotFromDb.getItem().equals(lot.getItem())) {
                if (lotFromDb.getLotType().equals(lot.getLotType())) {
                    if (LotType.LOBBY.equals(lot.getLotType())) {
                        throw new LobbyException("Lobby already exists!");
                    }
                    throw new LotException("Lot already exists!");
                }
            }
        }
    }

    @Override
    public Lot createLot(Long itemId, LotType type, Long validity, String ownerEmail) {
        Item itemFromDb = itemRepository.findItemById(itemId);
        Lot lot = new Lot();
        if (itemFromDb.getUser().getEmail().equals(ownerEmail)) {
            lot.setItem(itemFromDb);
            lot.setSeller(itemFromDb.getUser());
            lot.setPrice(itemFromDb.getPrice());
            lot.setCreationTime(LocalDateTime.now());
            lot.setLotType(type);
            if (type.equals(LotType.DYNAMIC) || type.equals(LotType.LOBBY)) {
                lot.setLastCustomer(itemFromDb.getUser());
                lot.setExpirationTime(lot.getCreationTime().plusMinutes(validity));
            }
            validateLot(lot, validity);
            l.info("Lot#"+lotRepository.findLotByItemAndLotType(lot.getItem(), lot.getLotType()).getId()+
                    " of "+lot.getLotType()+"type was created");
            return lotRepository.save(lot);
        } else throw new InvalidItemException("You have selected invalid item");
    }

    @Override
    public Lot updateLot(Lot lotFromDb, Double newPrice) {
        lotFromDb.setPrice(newPrice);
        lotFromDb.setCreationTime(LocalDateTime.now());
        l.info("Lot#"+lotFromDb.getId()+" was updated");
        return lotRepository.save(lotFromDb);
    }

    @Override
    public Lot placeNewBet(Lot lotFromDb, Double bet, String userName) {
        User customer = userService.findByEmail(userName).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        if (lotFromDb.getPrice() >= bet) {
            throw new MoneyException("Bet must be higher than previous");
        }
        if (customer.equals(lotFromDb.getItem().getUser())) {
            throw new MoneyException("You cannot place bet on your own lot");
        }
        if (lotFromDb.getLotType().equals(LotType.LOBBY)) {
            lotFromDb.setExpirationTime(lotFromDb.getExpirationTime().plusMinutes(1));
        }
        lotFromDb.setPrice(bet);
        lotFromDb.setLastCustomer(customer);
        lotRepository.save(lotFromDb);
        l.info("User "+customer.getEmail()+" placed "+bet+"on Lot#"+lotFromDb.getId());
        return lotFromDb;
    }

    @Override
    public void autoSell(Lot lotToSell) throws AutoSellException {
        User seller = lotToSell.getSeller();
        seller.setLobby(null);
        User customer = lotToSell.getLastCustomer();
        customer.setLobby(null);
        userService.fullUserUpdate(seller);
        userService.fullUserUpdate(customer);
        if (seller.equals(customer) ||
                (lotToSell.getPrice() > customer.getMoney())) {
            lotRepository.delete(lotToSell);
            throw new AutoSellException("AutoSell failed");
        }
        userService.updateMoney(seller, lotToSell.getPrice(), true);
        userService.updateMoney(customer, lotToSell.getPrice(), false);
        Item lotItem = lotToSell.getItem();
        lotItem.setUser(customer);
        lotRepository.delete(lotToSell);
        lotClear(lotToSell);
        itemRepository.save(lotItem);
        l.info("Item#"+lotItem.getId()+" was sold to "+customer.getEmail()+" with Lot#"+lotToSell.getId());
    }

    @Override
    public User purchase(User customer, Lot lot) {
        User seller = lot.getSeller();
        if (customer.getMoney() >= lot.getPrice()) {
            Item itemToBuy = itemRepository.findItemById(lot.getItem().getId());
            if (!customer.getId().equals(seller.getId())) {
                if (itemToBuy.getUser() == seller) {
                    userService.updateMoney(customer, lot.getPrice(), false);
                    userService.updateMoney(seller, lot.getPrice(), true);
                    itemToBuy.setUser(customer);
                    lotRepository.delete(lot);
                    lotClear(lot);
                }
            }
        } else throw new MoneyException("You have not enough money");
        l.info("Item#"+lot.getItem().getId()+" was sold to "+customer.getEmail()+" with Lot#"+lot.getId());
        return customer;
    }

    @PostConstruct
    private void init() {
        Thread lotSeller = new LotAutoSellerThread(this);
        lotSeller.setDaemon(true);
        lotSeller.start();
    }

    @Override
    public void lotClear(Lot lot) {
        List<Lot> lots = lotRepository.findAll();
        for (Lot lotInDb : lots) {
            if (lot.getItem().getId().equals(lotInDb.getItem().getId())){
                lotRepository.delete(lotInDb);
            }
        }
    }
}
