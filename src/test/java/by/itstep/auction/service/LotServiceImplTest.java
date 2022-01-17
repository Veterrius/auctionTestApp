package by.itstep.auction.service;

import by.itstep.auction.dao.repository.ItemRepository;
import by.itstep.auction.dao.repository.LotRepository;
import by.itstep.auction.service.impl.LotServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LotServiceImplTest {

    @InjectMocks
    private LotServiceImpl lotService;
    @Mock
    private LotRepository lotRepository;
    @Mock
    private ItemRepository itemRepository;

    @Test
    public void findLotByIdTest_Success() {
        Long id = 2L;
        lotRepository.findLotById(id);
    }
}

//    @Test
//    public void createLotByItemIdTest_Success() {
//        Long itemId = 4L;
//        User user = new User();
//        Item itemFromDb = itemRepository.findItemById(itemId);
//        Lot lot = new Lot();
//        if (itemFromDb != null) {
//            if (itemFromDb.getUser() == user) {
//                lot.setItem(itemFromDb);
//                lot.setSeller(user);
//                lot.setPrice(itemFromDb.getPrice());
//                lot.setTime(LocalDateTime.now());
//                lotRepository.save(lot);
//            }
//        }
//    }
//}
