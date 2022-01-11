package by.itstep.auction.dao.repository;

import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LotRepository extends JpaRepository<Lot, Long> {
    Lot findLotBySeller(User user);

    Optional<Lot> findLotById(Long id);
}
