package by.itstep.auction.service;

import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.User;

import java.util.Optional;

public interface LotService {
    Lot findLotBySeller(User user);

    Optional<Lot> findLotById(Long id);

    Lot createLot(Lot lot);

    void deleteLotById(Long id);

    Iterable<Lot> findAllLots();

    void validateLot(Lot lot);

    Lot createLotByItemId(Long itemId, User user);

    Lot updateLot(Lot lotFromDb, Lot updatedLot);
}
