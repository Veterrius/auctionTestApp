package by.itstep.auction.service;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.User;
import by.itstep.auction.dao.model.enums.LotType;
import by.itstep.auction.dao.repository.ItemRepository;
import by.itstep.auction.dao.repository.LotRepository;
import by.itstep.auction.dao.repository.UserRepository;
import by.itstep.auction.service.exceptions.AutoSellException;
import by.itstep.auction.service.exceptions.InvalidItemException;
import by.itstep.auction.service.exceptions.LotException;
import by.itstep.auction.service.exceptions.MoneyException;
import by.itstep.auction.service.impl.LotServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
    private UserService userService;

    @Test
    public void findLotByIdTest_Success() {
        Lot validLot = new Lot();
        validLot.setId(1L);
        lotRepository.findLotById(1L);
    }

    @Test
    public void createLotTest_Success() {
        Item item = new Item();
        item.setPrice(22.0);
        User user = new User();
        user.setEmail("test@e");
        item.setUser(user);
        Mockito.when(itemRepository.findItemById(5L)).thenReturn(Optional.of(item));
        Mockito.when(lotRepository.findLotByItemAndLotType(item, LotType.LOBBY)).thenReturn(new Lot());
        lotService.createLot(5L, LotType.LOBBY, 60L, "test@e");
    }

    @Test
    public void createLotTest_InvalidItemException() {
        Assertions.assertThrows(InvalidItemException.class, () -> lotService.createLot(5L, LotType.LOBBY, 60L, null));
    }

    @Test
    public void createLotTest_LotException_WrongValidity() {
        Item item = new Item();
        item.setPrice(22.0);
        User user = new User();
        user.setEmail("test@e");
        item.setUser(user);
        Mockito.when(itemRepository.findItemById(5L)).thenReturn(Optional.of(item));
        Assertions.assertThrows(LotException.class,
                () ->lotService.createLot(5L, LotType.DYNAMIC, 1L, "test@e"));
    }

    @Test
    public void placeNewBetTest_Success() {
        Lot lotFromDb = new Lot();
        lotFromDb.setSeller(new User());
        lotFromDb.setPrice(24.0);
        lotFromDb.setLotType(LotType.STATIC);
        User customer = new User();
        customer.setMoney(26.0);
        Mockito.when(userService.findByEmail("user")).thenReturn(Optional.of(customer));
        lotService.placeNewBet(lotFromDb, 25.2, "user");
    }

    @Test
    public void placeNewBetTest_MoneyException_MustBeHigherThanPreviousCase() {
        Lot lotFromDb = new Lot();
        lotFromDb.setSeller(new User());
        lotFromDb.setPrice(29.0);
        lotFromDb.setLotType(LotType.STATIC);
        User customer = new User();
        customer.setMoney(30.0);
        Mockito.when(userService.findByEmail("user")).thenReturn(Optional.of(customer));
        Assertions.assertThrows(MoneyException.class, () -> lotService.placeNewBet(lotFromDb, 25.2, "user"));
    }

    @Test
    public void placeNewBetTest_MoneyException_NotEnoughMoneyCase() {
        Lot lotFromDb = new Lot();
        lotFromDb.setSeller(new User());
        lotFromDb.setPrice(29.0);
        lotFromDb.setLotType(LotType.STATIC);
        User customer = new User();
        customer.setMoney(30.0);
        Mockito.when(userService.findByEmail("user")).thenReturn(Optional.of(customer));
        Assertions.assertThrows(MoneyException.class, () -> lotService.placeNewBet(lotFromDb, 31.0, "user"));
    }

    @Test
    public void placeNewBetTest_LotException_CannotBetOnYourOwnLotCase() {
        Lot lotFromDb = new Lot();
        lotFromDb.setPrice(29.0);
        lotFromDb.setLotType(LotType.STATIC);
        User customer = new User();
        lotFromDb.setSeller(customer);
        customer.setMoney(35.0);
        Mockito.when(userService.findByEmail("user")).thenReturn(Optional.of(customer));
        Assertions.assertThrows(LotException.class, () -> lotService.placeNewBet(lotFromDb, 31.0, "user"));
    }

    @Test
    public void placeNewBetTest_UserNotFoundException() {
        Lot lotFromDb = new Lot();
        Assertions.assertThrows(UsernameNotFoundException.class, () -> lotService.placeNewBet(lotFromDb, 31.0, "user"));
    }

    @Test
    public void autoSellTest_Success() {
        Lot lotToSell = new Lot();
        lotToSell.setSeller(new User());
        lotToSell.setItem(new Item());
        lotToSell.setPrice(7.0);
        User customer = new User();
        customer.setMoney(7.0);
        lotToSell.setLastCustomer(customer);
        lotService.autoSell(lotToSell);
    }

    @Test
    public void autoSellTest_AutoSellException_NotEnoughMoneyCase() {
        Lot lotToSell = new Lot();
        lotToSell.setSeller(new User());
        lotToSell.setItem(new Item());
        lotToSell.setPrice(7.0);
        User customer = new User();
        customer.setMoney(6.0);
        lotToSell.setLastCustomer(customer);
        Assertions.assertThrows(AutoSellException.class, () -> lotService.autoSell(lotToSell));
    }

    @Test
    public void autoSellTest_AutoSellException_NoBetsCase() {
        User owner = new User();
        Lot lotToSell = new Lot();
        lotToSell.setSeller(owner);
        lotToSell.setItem(new Item());
        lotToSell.setPrice(7.0);
        lotToSell.setLastCustomer(owner);
        Assertions.assertThrows(AutoSellException.class, () -> lotService.autoSell(lotToSell));
    }

    @Test
    public void purchaseTest_Success() {
        Item itemToBuy = new Item();
        Lot lotToPurchase = new Lot();
        lotToPurchase.setPrice(5.0);
        User owner = new User();
        owner.setId(1L);
        lotToPurchase.setSeller(owner);
        lotToPurchase.setItem(itemToBuy);
        itemToBuy.setUser(owner);
        User customer = new User();
        customer.setMoney(5.0);
        customer.setId(2L);
        lotService.purchase(customer, lotToPurchase);
    }

    @Test
    public void purchaseTest_MoneyException() {
        Item itemToBuy = new Item();
        Lot lotToPurchase = new Lot();
        lotToPurchase.setPrice(5.0);
        User owner = new User();
        owner.setId(1L);
        lotToPurchase.setSeller(owner);
        lotToPurchase.setItem(itemToBuy);
        itemToBuy.setUser(owner);
        User customer = new User();
        customer.setMoney(4.0);
        customer.setId(2L);
        Assertions.assertThrows(MoneyException.class, () -> lotService.purchase(customer, lotToPurchase));
    }

    @Test
    public void purchaseTest_LotException() {
        Item itemToBuy = new Item();
        Lot lotToPurchase = new Lot();
        lotToPurchase.setPrice(5.0);
        User owner = new User();
        owner.setId(1L);
        owner.setMoney(5.0);
        lotToPurchase.setSeller(owner);
        lotToPurchase.setItem(itemToBuy);
        itemToBuy.setUser(owner);
        Assertions.assertThrows(LotException.class, () -> lotService.purchase(owner, lotToPurchase));
    }
}


