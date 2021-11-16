package by.itstep.auction.service;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.User;
import by.itstep.auction.dao.repository.ItemRepository;
import by.itstep.auction.dao.repository.LotRepository;
import by.itstep.auction.service.exceptions.InvalidItemException;
import by.itstep.auction.service.exceptions.LotAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LotService {

    private final LotRepository lotRepository;

    private final ItemRepository itemRepository;

    public LotService(LotRepository lotRepository, ItemRepository itemRepository) {
        this.lotRepository = lotRepository;
        this.itemRepository = itemRepository;
    }

    public Lot findLotBySeller(User user) {
        return lotRepository.findLotBySeller(user);
    }

    public Lot findLotById(Long id) {
        return lotRepository.findLotById(id);
    }

    public Lot createLot(Lot lot) {
        return lotRepository.save(lot);
    }

    public void deleteLotById(Long id) {
        Lot lotToDelete = lotRepository.findLotById(id);
        lotRepository.delete(lotToDelete);
    }

    public Iterable<Lot> findAllLots() {
        return lotRepository.findAll();
    }

    public void validateLot(Lot lot) {
        Iterable<Lot> allLots = findAllLots();
        for (Lot lotFromDb : allLots) {
            if (lotFromDb.getItem().equals(lot.getItem())) {
                throw new LotAlreadyExistsException("Lot already exists!");
            }
        }
    }

    public void createLotByItemId(Long itemId, User user) {
       Item itemFromDb = itemRepository.findItemById(itemId);
       Lot lot = new Lot();
        if (itemFromDb != null) {
            if (itemFromDb.getUser()==user) {
                lot.setItem(itemFromDb);
                lot.setSeller(user);
                lot.setPrice(itemFromDb.getPrice());
                lot.setTime(LocalDateTime.now());
                validateLot(lot);
                lotRepository.save(lot);
            } else throw new InvalidItemException("You have selected invalid item");
        } else throw new InvalidItemException("You have selected invalid item");
    }
}
