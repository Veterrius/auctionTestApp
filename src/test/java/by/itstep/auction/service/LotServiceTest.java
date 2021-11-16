package by.itstep.auction.service;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.User;
import by.itstep.auction.dao.repository.ItemRepository;
import by.itstep.auction.dao.repository.LotRepository;
import by.itstep.auction.service.exceptions.InvalidItemException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class LotServiceTest {

    @InjectMocks
    private LotService lotService;
    @Mock
    private LotRepository lotRepository;
    @Mock
    private ItemRepository itemRepository;

    @Test
    public void findLotByIdTest_Success() {
        Long id = 2L;
        lotRepository.findLotById(id);
    }

    @Test
    public void createLotByItemIdTest_Success() {
        Long itemId = 4L;
        User user = new User();
        Item itemFromDb = itemRepository.findItemById(itemId);
        Lot lot = new Lot();
        if (itemFromDb != null) {
            if (itemFromDb.getUser() == user) {
                lot.setItem(itemFromDb);
                lot.setSeller(user);
                lot.setPrice(itemFromDb.getPrice());
                lot.setTime(LocalDateTime.now());
                lotRepository.save(lot);
            }
        }
    }
}
