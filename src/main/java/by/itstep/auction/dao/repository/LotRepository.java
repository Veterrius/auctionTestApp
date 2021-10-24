package by.itstep.auction.dao.repository;

import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotRepository extends JpaRepository<Lot, Long> {
    Lot findLotBySeller(User user);

    Lot findLotById(Long id);
}
