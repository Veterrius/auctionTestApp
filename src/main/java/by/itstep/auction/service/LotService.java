package by.itstep.auction.service;

import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.User;
import by.itstep.auction.dao.model.enums.LotType;
import by.itstep.auction.service.exceptions.AutoSellException;

import java.util.Optional;

public interface LotService {
    Lot findLotBySeller(User user);

    Optional<Lot> findLotById(Long id);

    Lot createLot(Lot lot);

    void deleteLotById(Long id);

    Iterable<Lot> findAllLots();

    Lot createLotByItemId(Long itemId, User user);

    Lot createLot(Long itemId, LotType type, Long validity);

    Lot updateLot(Lot lotFromDb, Double newPrice);

    Lot placeNewBet(Lot lotFromDb, Double bet, String name);

    void autoSell(Lot lotToSell) throws AutoSellException;
}
