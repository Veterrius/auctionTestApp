package by.itstep.auction.service;

import by.itstep.auction.dao.model.Lobby;
import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);

    User updateMoney(User user, Double money, Boolean isAdded);

    Optional<User> findByEmail(String email);

    User fullUserUpdate(User user);

    List<User> findUsersInLobby(Lobby lobby);
}
