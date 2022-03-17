package by.itstep.auction.dao.repository;

import by.itstep.auction.dao.model.Lobby;
import by.itstep.auction.dao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface LobbyRepository extends JpaRepository<Lobby, Long> {

    Optional<Lobby> findByOwner(User owner);
}
