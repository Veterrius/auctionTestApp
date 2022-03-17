package by.itstep.auction.service;

import by.itstep.auction.dao.model.User;
import by.itstep.auction.dao.repository.UserRepository;
import by.itstep.auction.service.exceptions.MoneyException;
import by.itstep.auction.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Test
    public void updateMoneyTest_Success(){
        User user = new User();
        user.setMoney(0.0);
        userService.updateMoney(user, 2.5, true);
    }

    @Test
    public void updateMoneyTest_MoneyException() {
        User user = new User();
        user.setMoney(0.0);
        Assertions.assertThrows(MoneyException.class, () -> userService.updateMoney(user, -2.5, true));
    }

    @Test
    public void createUserTest_Success() {
        User user = new User();
        user.setPassword("pass");
        userService.createUser(user);
    }
}
