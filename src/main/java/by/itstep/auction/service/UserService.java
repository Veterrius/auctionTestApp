package by.itstep.auction.service;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.User;
//import by.itstep.auction.dao.model.enums.Role;
import by.itstep.auction.dao.repository.ItemRepository;
import by.itstep.auction.dao.repository.LotRepository;
import by.itstep.auction.dao.repository.UserRepository;
import by.itstep.auction.service.exceptions.NotEnoughMoneyException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {//implements UserDetailsService {

    private final UserRepository userRepository;

    private final LotRepository lotRepository;

    private final ItemRepository itemRepository;

    public UserService(UserRepository userRepository, LotRepository lotRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.lotRepository = lotRepository;
        this.itemRepository = itemRepository;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUsername(username);
//    }

    public User findUserByName(String username) {
        return userRepository.findByName(username);
    }

    public User createUser(User user) {
        user.setMoney(0.0);
        user.setId(user.getId());
//        user.setRoles(Collections.singleton(Role.USER));
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public User updateMoney(User user, Double money, Boolean isAdded) {
        if (isAdded) {
            user.setMoney(user.getMoney() + money);
        } else {
            user.setMoney(user.getMoney() - money);
        }
        return userRepository.save(user);
    }

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

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByGoogleId(String googleId) {
        return userRepository.findByGoogleId(googleId);
    }
}
