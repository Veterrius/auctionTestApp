package by.itstep.auction.service;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.User;
import by.itstep.auction.dao.model.enums.LotType;
import by.itstep.auction.dao.repository.ItemRepository;
import by.itstep.auction.dao.repository.LotRepository;
import by.itstep.auction.dao.repository.UserRepository;
import by.itstep.auction.service.impl.LotServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LotServiceImplTest {

    @InjectMocks
    private LotServiceImpl lotService;
    @Mock
    private LotRepository lotRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    public void findLotByIdTest_Success() {
        Lot validLot = new Lot();
        validLot.setId(1L);
        lotRepository.findLotById(1L);
    }

    @Test
    public void createLotTest_Success() {
        User user = new User();
        user.setId(5L);
        Item item = new Item();
        item.setUser(user);
        item.setId(5L);
        itemRepository.save(item);
        userRepository.save(user);
        lotService.createLot(5L, LotType.LOBBY, 60L, null);
    }

}

