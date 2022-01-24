package by.itstep.auction.service;

import by.itstep.auction.dao.model.Lobby;
import by.itstep.auction.dao.model.dto.LobbyRequestDTO;

import java.util.List;
import java.util.Optional;

public interface LobbyService {
    
    List<Lobby> getAll();

    Optional<Lobby> getLobbyById(Long id);

    Lobby createLobby(LobbyRequestDTO lobbyRequest);

    void delete(Lobby lobby);
}
