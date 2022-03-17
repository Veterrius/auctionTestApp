package by.itstep.auction.service.impl;

import by.itstep.auction.dao.model.Lobby;
import by.itstep.auction.dao.model.User;
import by.itstep.auction.dao.model.enums.Role;
import by.itstep.auction.dao.model.enums.Status;
import by.itstep.auction.dao.repository.UserRepository;
import by.itstep.auction.service.UserService;
import by.itstep.auction.service.exceptions.MoneyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final Logger l = LoggerFactory.getLogger(LotServiceImpl.class);

    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public User updateMoney(User user, Double money, Boolean isAdded) {
        if (money <= 0) {
            throw new MoneyException("Invalid amount of money");
        }
        if (isAdded) {
            user.setMoney(user.getMoney() + money);
        } else {
            user.setMoney(user.getMoney() - money);
        }
        userRepository.save(user);
        l.info("User "+user.getEmail()+"#"+user.getId()+" updated money");
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User fullUserUpdate(User user) {
        User userFromDb = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userFromDb.setLobby(user.getLobby());
        userFromDb.setMoney(user.getMoney());
        userFromDb.setPassword(user.getPassword());
        userFromDb.setRole(user.getRole());
        userFromDb.setEmail(user.getEmail());
        userFromDb.setStatus(user.getStatus());
        userFromDb.setUsername(user.getUsername());
        return userRepository.save(userFromDb);
    }

    @Override
    public List<User> findUsersInLobby(Lobby lobby) {
        return userRepository.findAllByLobby(lobby);
    }
}
