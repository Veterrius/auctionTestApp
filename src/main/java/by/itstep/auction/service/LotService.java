package by.itstep.auction.service;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.Lobby;
import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.User;
import by.itstep.auction.dao.model.enums.LotType;
import by.itstep.auction.service.exceptions.AutoSellException;

import java.util.Optional;

public interface LotService {

    Lot findLotByItemAndType(Item item, LotType lotType);

    Optional<Lot> findLotById(Long id);

    Lot createLot(Lot lot);

    void deleteLotById(Long id);

    void deleteLot(Lot lot);

    Iterable<Lot> findAllLots();

    Lot createLot(Long itemId, LotType type, Long validity, String ownerEmail);

    Lot updateLot(Lot lotFromDb, Double newPrice);

    Lot placeNewBet(Lot lotFromDb, Double bet, String name);

    void autoSell(Lot lotToSell) throws AutoSellException;

    User purchase(User customer, Lot lot);

    void lotClear(Lot lot);
}
