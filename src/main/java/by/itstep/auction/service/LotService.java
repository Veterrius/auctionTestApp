package by.itstep.auction.service;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.User;
import by.itstep.auction.dao.repository.ItemRepository;
import by.itstep.auction.dao.repository.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LotService {

    @Autowired
    private LotRepository lotRepository;

    @Autowired
    private ItemRepository itemRepository;

    public Lot findLotBySeller(User user) {
        return lotRepository.findLotBySeller(user);
    }

    public Lot findLotById(Long id) {
        return lotRepository.findLotById(id);
    }

    public Lot createLot(Lot lot) {
        return lotRepository.save(lot);
    }

    public Iterable<Lot> findAllLots() {
        return lotRepository.findAll();
    }

    public Lot createLotByItemId(Long itemId, User user) {
       Item itemFromDb = itemRepository.findItemById(itemId);
       Lot lot = new Lot();
        if (itemFromDb != null) {
            lot.setItem(itemFromDb);
            lot.setSeller(user);
            lot.setPrice(itemFromDb.getPrice()*itemFromDb.getQuantity());
            lot.setTime(LocalDateTime.now());
        }
        return lotRepository.save(lot);
    }
}
