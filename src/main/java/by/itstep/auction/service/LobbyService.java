package by.itstep.auction.service;

import by.itstep.auction.dao.model.Lobby;
import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.dto.LobbyRequestDTO;
import by.itstep.auction.service.exceptions.AutoSellException;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface LobbyService {
    
    List<Lobby> getAll();

    Optional<Lobby> getLobbyById(Long id);

    Lobby createLobby(LobbyRequestDTO lobbyRequest);

    void delete(Lobby lobby);

    void deleteWithLot(Lobby lobby);

    Lobby startLobby(Lobby lobby, String email);

    Lobby joinLobby(Lobby lobby, String email);

    Lobby leaveLobby(Lobby lobby, String email);

    Lobby placeBet(Lobby lobby, Double bet, Principal principal);

    void lobbyAutoSell(Lot lot) throws AutoSellException;
}
