package by.itstep.auction.service.impl;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.User;
//import by.itstep.auction.dao.model.enums.Role;
import by.itstep.auction.dao.model.enums.Role;
import by.itstep.auction.dao.model.enums.Status;
import by.itstep.auction.dao.repository.ItemRepository;
import by.itstep.auction.dao.repository.LotRepository;
import by.itstep.auction.dao.repository.UserRepository;
import by.itstep.auction.service.UserService;
import by.itstep.auction.service.exceptions.NotEnoughMoneyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final LotRepository lotRepository;
    private final ItemRepository itemRepository;

    public UserServiceImpl(UserRepository userRepository, LotRepository lotRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.lotRepository = lotRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public User createUser(User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setMoney(0.0);
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User userFromDb, User updatedUser) {
        userFromDb.setMoney(updatedUser.getMoney());
        return userRepository.save(userFromDb);
    }

    @Override
    public User updateMoney(User user, Double money, Boolean isAdded) {
        if (isAdded) {
            user.setMoney(user.getMoney() + money);
        } else {
            user.setMoney(user.getMoney() - money);
        }
        return userRepository.save(user);
    }

    @Override
    public User purchase(User customer, Lot lot) {
        User seller = lot.getSeller();
        if (customer.getMoney() >= lot.getPrice()) {
            Item itemToBuy = itemRepository.findItemById(lot.getItem().getId());
            if (!customer.getId().equals(seller.getId())) {
                if (itemToBuy.getUser() == seller) {
                    updateMoney(customer, lot.getPrice(), false);
                    updateMoney(seller, lot.getPrice(), true);
                    itemToBuy.setUser(customer);
                    lotRepository.delete(lot);
                }
            }
        } else throw new NotEnoughMoneyException("You have not enough money");
        return customer;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}