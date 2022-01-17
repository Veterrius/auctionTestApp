package by.itstep.auction.service;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.User;
//import by.itstep.auction.dao.model.enums.Role;
import by.itstep.auction.dao.model.enums.Role;
import by.itstep.auction.dao.model.enums.Status;
import by.itstep.auction.dao.repository.ItemRepository;
import by.itstep.auction.dao.repository.LotRepository;
import by.itstep.auction.dao.repository.UserRepository;
import by.itstep.auction.service.exceptions.NotEnoughMoneyException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

//    public User findUserByName(String username) {
//        return userRepository.findByUsername(username);
//    }

    public User createUser(User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setMoney(0.0);
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
        return userRepository.save(user);
    }

    public User updateUser(User userFromDb, User updatedUser) {
        userFromDb.setMoney(updatedUser.getMoney());
        return userRepository.save(userFromDb);
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

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
