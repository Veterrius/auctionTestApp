package by.itstep.auction.dao.repository;

import by.itstep.auction.dao.model.Lobby;
import by.itstep.auction.dao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    List<User> findAllByLobby(Lobby lobby);
}
