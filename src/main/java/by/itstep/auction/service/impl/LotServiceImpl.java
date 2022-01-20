package by.itstep.auction.service.impl;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.User;
import by.itstep.auction.dao.model.enums.LotType;
import by.itstep.auction.dao.repository.ItemRepository;
import by.itstep.auction.dao.repository.LotRepository;
import by.itstep.auction.service.LotService;
import by.itstep.auction.service.exceptions.InvalidItemException;
import by.itstep.auction.service.exceptions.LotAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LotServiceImpl implements LotService {

    private final LotRepository lotRepository;

    private final ItemRepository itemRepository;

    public LotServiceImpl(LotRepository lotRepository, ItemRepository itemRepository) {
        this.lotRepository = lotRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public Lot findLotBySeller(User user) {
        return lotRepository.findLotBySeller(user);
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
    public Iterable<Lot> findAllLots() {
        return lotRepository.findAll();
    }

    private void validateLot(Lot lot) {
        Iterable<Lot> allLots = findAllLots();
        for (Lot lotFromDb : allLots) {
            if (lotFromDb.getItem().equals(lot.getItem())) {
                if (lotFromDb.getLotType().equals(lot.getLotType())) {
                    throw new LotAlreadyExistsException("Lot already exists!");
                }
            }
        }
    }

    @Override
    public Lot createLotByItemId(Long itemId, User user) {
       Item itemFromDb = itemRepository.findItemById(itemId);
       Lot lot = new Lot();
        if (itemFromDb != null) {
            if (itemFromDb.getUser()==user) {
                lot.setItem(itemFromDb);
                lot.setSeller(user);
                lot.setPrice(itemFromDb.getPrice());
                lot.setCreationTime(LocalDateTime.now());
                validateLot(lot);
                lotRepository.save(lot);
            } else throw new InvalidItemException("You have selected invalid item");
        } else throw new InvalidItemException("You have selected invalid item");
        return lot;
    }

    @Override
    public Lot createLot(Long itemId, LotType type, Long validity) {
        Item itemFromDb = itemRepository.findItemById(itemId);
        Lot lot = new Lot();
        if (itemFromDb.getUser() != null) {
            lot.setItem(itemFromDb);
            lot.setSeller(itemFromDb.getUser());
            lot.setPrice(itemFromDb.getPrice());
            lot.setCreationTime(LocalDateTime.now());
            lot.setLotType(type);
            if (type.equals(LotType.DYNAMIC)) {
                lot.setExpirationTime(lot.getCreationTime().plusMinutes(validity));
            }
            validateLot(lot);
            return lotRepository.save(lot);
        } else throw new InvalidItemException("You have selected invalid item");
    }

    @Override
    public Lot updateLot(Lot lotFromDb, Double newPrice) {
        lotFromDb.setPrice(newPrice);
        lotFromDb.setCreationTime(LocalDateTime.now());
        return lotRepository.save(lotFromDb);
    }
}
