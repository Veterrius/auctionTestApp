package by.itstep.auction.service.impl;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.Lobby;
import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.User;
import by.itstep.auction.dao.model.dto.LobbyRequestDTO;
import by.itstep.auction.dao.model.enums.LobbyStatus;
import by.itstep.auction.dao.model.enums.LotType;
import by.itstep.auction.dao.repository.LobbyRepository;
import by.itstep.auction.service.ItemService;
import by.itstep.auction.service.LobbyService;
import by.itstep.auction.service.LotService;
import by.itstep.auction.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LobbyServiceImpl implements LobbyService {

    private final LobbyRepository lobbyRepository;
    private final LotService lotService;
    private final ItemService itemService;

    public LobbyServiceImpl(LobbyRepository lobbyRepository, LotService lotService, ItemService itemService) {
        this.lobbyRepository = lobbyRepository;
        this.lotService = lotService;
        this.itemService = itemService;
    }

    @Override
    public List<Lobby> getAll() {
        return lobbyRepository.findAll();
    }

    @Override
    public Optional<Lobby> getLobbyById(Long id) {
        return lobbyRepository.findById(id);
    }

    @Override
    public Lobby createLobby(LobbyRequestDTO lobbyRequest) {
        Item itemToSell = itemService.findItemById(lobbyRequest.getItemId());
        User owner = itemToSell.getUser();
        Lot lobbyLot = lotService.createLot(lobbyRequest.getItemId(), LotType.LOBBY, 1L);
        Lobby lobby = new Lobby();
        lobby.setLot(lobbyLot);
        lobby.setOwner(owner);
        lobby.setMaxUsers(lobbyRequest.getMaxUsers());
        lobby.setLobbyStatus(LobbyStatus.FREE);
        return lobbyRepository.save(lobby);
    }

    @Override
    public void delete(Lobby lobby) {
        lobbyRepository.delete(lobby);
    }
}
