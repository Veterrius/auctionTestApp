package by.itstep.auction.dao.repository;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.User;
import by.itstep.auction.dao.model.enums.LotType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LotRepository extends JpaRepository<Lot, Long> {
    Lot findLotByItemAndLotType(Item item, LotType type);

    Optional<Lot> findLotById(Long id);
}
