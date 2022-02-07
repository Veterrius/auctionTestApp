package by.itstep.auction.service;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.User;
import by.itstep.auction.dao.repository.ItemRepository;
import by.itstep.auction.service.exceptions.InvalidItemException;
import by.itstep.auction.service.impl.ItemServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    @InjectMocks
    private ItemServiceImpl itemService;
    @Mock
    private ItemRepository itemRepository;

    @Test
    public void getByIdTest_Success() {
        Item validItem = new Item();
        Mockito.when(itemRepository.findItemById(1L)).thenReturn(validItem);
        itemService.findItemById(1L);
    }

    @Test
    public void createItemTest_Success() {
        Item validItem = new Item();
        validItem.setName("ItemTest");
        validItem.setPrice(228.2);
        itemService.createItem(validItem, new User());
    }

    @Test
    public void createItemTest_Exception() {
        Item invalidItem = new Item();
        invalidItem.setName("ItemTest");
        invalidItem.setPrice(-228.2);
        Assertions.assertThrows(InvalidItemException.class, () -> itemService.createItem(invalidItem, new User()));
    }
}
