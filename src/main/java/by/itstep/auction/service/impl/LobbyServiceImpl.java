package by.itstep.auction.service.impl;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.Lobby;
import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.User;
import by.itstep.auction.dao.model.dto.LobbyRequestDTO;
import by.itstep.auction.dao.model.enums.LobbyStatus;
import by.itstep.auction.dao.model.enums.LotType;
import by.itstep.auction.dao.repository.LobbyRepository;
import by.itstep.auction.service.*;
import by.itstep.auction.service.exceptions.AutoSellException;
import by.itstep.auction.service.exceptions.LobbyException;
import by.itstep.auction.service.threads.LobbyAutoSellerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LobbyServiceImpl implements LobbyService {

    private final Logger l = LoggerFactory.getLogger(LobbyServiceImpl.class);

    private final LobbyRepository lobbyRepository;
    private final LotService lotService;
    private final ItemService itemService;
    private final UserService userService;

    public LobbyServiceImpl(LobbyRepository lobbyRepository, LotService lotService, ItemService itemService, UserService userService) {
        this.lobbyRepository = lobbyRepository;
        this.lotService = lotService;
        this.itemService = itemService;
        this.userService = userService;
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
        Lot lobbyLot = lotService.createLot(lobbyRequest.getItemId(), LotType.LOBBY, 2628000L);
        Lobby lobby = new Lobby();
        lobby.setLot(lobbyLot);
        lobby.setOwner(owner);
        lobby.setMaxUsers(lobbyRequest.getMaxUsers());
        lobby.setCurrentUsers(1);
        lobby.setLobbyStatus(LobbyStatus.FREE);
        lobbyRepository.save(lobby);
        owner.setLobby(lobby);
        userService.fullUserUpdate(owner);
        l.info(owner.getEmail()+" created Lobby");
        return lobby;
    }

    @Override
    public void delete(Lobby lobby) {
        lobby.setLot(null);
        lobbyRepository.save(lobby);
        clearUsers(lobby);
        lobbyRepository.delete(lobby);
    }

    @Override
    public void deleteWithLot(Lobby lobby) {
        Lot lotToDelete = lobby.getLot();
        lobby.setLot(null);
        lobbyRepository.save(lobby);
        clearUsers(lobby);
        lotService.deleteLot(lotToDelete);
        lobbyRepository.delete(lobby);
    }

    @Override
    public Lobby startLobby(Lobby lobbyFromDb, String email) {
        User principal = userService.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!lobbyFromDb.getOwner().getId().equals(principal.getId())) {
            throw new LobbyException("You don't have such lobby");
        }
        lobbyFromDb.setLobbyStatus(LobbyStatus.STARTED);
        Lot lot = lobbyFromDb.getLot();
        lot.setExpirationTime(LocalDateTime.now().plusMinutes(1));
        lotService.createLot(lot);
        lobbyFromDb.setLot(lot);
        l.info("Lobby#"+lobbyFromDb.getId()+" was started");
        return lobbyRepository.save(lobbyFromDb);
    }

    @Override
    public Lobby joinLobby(Lobby lobbyFromDb, String email) {
        User principal = userService.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (lobbyFromDb.getOwner().getId().equals(principal.getId()) ||
                principal.getLobby() != null) {
            throw new LobbyException("You are already in lobby");
        }
        if (LobbyStatus.FULL.equals(lobbyFromDb.getLobbyStatus())) {
            throw new LobbyException("Lobby is full");
        }
        principal.setLobby(lobbyFromDb);
        userService.fullUserUpdate(principal);
        lobbyFromDb.setCurrentUsers(lobbyFromDb.getCurrentUsers()+1);
        if (lobbyFromDb.getCurrentUsers().equals(lobbyFromDb.getMaxUsers())) {
            lobbyFromDb.setLobbyStatus(LobbyStatus.FULL);
        }
        l.info(principal.getEmail()+" joined Lobby#"+lobbyFromDb.getId());
        return lobbyRepository.save(lobbyFromDb);
    }

    @Override
    public Lobby leaveLobby(Lobby lobbyFromDb, String email) {
        if (LobbyStatus.STARTED.equals(lobbyFromDb.getLobbyStatus())) {
            throw new LobbyException("You cannot leave from the started lobby");
        }
        User principal = userService.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        principal.setLobby(null);
        lobbyFromDb.setCurrentUsers(lobbyFromDb.getCurrentUsers()-1);
        userService.fullUserUpdate(principal);
        l.info(principal.getEmail()+" left Lobby#"+lobbyFromDb.getId());
        return lobbyRepository.save(lobbyFromDb);
    }

    @Override
    public Lobby placeBet(Lobby lobby, Double bet, Principal principal) {
        if (!LobbyStatus.STARTED.equals(lobby.getLobbyStatus())) {
            throw new LobbyException("Lobby is not started yet");
        }
        lotService.placeNewBet(lobby.getLot(), bet, principal.getName());
        l.info(principal.getName()+" placed "+ bet + " in Lobby#"+ lobby.getId());
        return lobby;
    }

    private void clearUsers(Lobby lobby) {
        List<User> usersInLobby = userService.findUsersInLobby(lobby);
        for (User user : usersInLobby) {
            user.setLobby(null);
            userService.fullUserUpdate(user);
        }
    }

    @Override
    public void lobbyAutoSell(Lot lot) throws AutoSellException {
        delete(lot.getSeller().getLobby());
        lotService.autoSell(lot);
    }

    @PostConstruct
    private void init(){
        Thread seller = new LobbyAutoSellerThread(this, lotService);
        seller.setDaemon(true);
        seller.start();
    }
}
