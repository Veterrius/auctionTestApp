package by.itstep.auction.service;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.Lobby;
import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.User;
import by.itstep.auction.dao.model.dto.LobbyRequestDTO;
import by.itstep.auction.dao.model.enums.LobbyStatus;
import by.itstep.auction.dao.repository.LobbyRepository;
import by.itstep.auction.service.exceptions.LobbyException;
import by.itstep.auction.service.impl.LobbyServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LobbyServiceImplTest {

    @InjectMocks
    private LobbyServiceImpl lobbyService;
    @Mock
    private LobbyRepository lobbyRepository;
    @Mock
    private ItemService itemService;
    @Mock
    private UserService userService;

    @Test
    public void createLobbyTest_Success() {
        LobbyRequestDTO lobbyRequestDTO = new LobbyRequestDTO();
        lobbyRequestDTO.setItemId(1L);
        lobbyRequestDTO.setMaxUsers(3);
        Item itemToSell = new Item();
        User seller = new User();
        itemToSell.setUser(seller);
        Mockito.when(itemService.findItemById(1L)).thenReturn(Optional.of(itemToSell));
        lobbyService.createLobby(lobbyRequestDTO);
    }

    @Test
    public void createLobbyTest_LobbyException() {
        LobbyRequestDTO lobbyRequestDTO = new LobbyRequestDTO();
        lobbyRequestDTO.setItemId(1L);
        lobbyRequestDTO.setMaxUsers(3);
        Lobby lobby = new Lobby();
        Item itemToSell = new Item();
        User seller = new User();
        itemToSell.setUser(seller);
        Mockito.when(itemService.findItemById(1L)).thenReturn(Optional.of(itemToSell));
        Mockito.when(lobbyRepository.findByOwner(seller)).thenReturn(Optional.of(lobby));
        Assertions.assertThrows(LobbyException.class, () -> lobbyService.createLobby(lobbyRequestDTO));
    }

    @Test
    public void startLobbyTest_Success() {
        Lobby lobbyToStart = new Lobby();
        User owner = new User();
        Lot lobbyLot = new Lot();
        lobbyToStart.setLot(lobbyLot);
        lobbyToStart.setOwner(owner);
        owner.setId(1L);
        owner.setEmail("test");
        Mockito.when(userService.findByEmail("test")).thenReturn(Optional.of(owner));
        lobbyService.startLobby(lobbyToStart, "test");
    }

    @Test
    public void startLobbyTest_LobbyException() {
        Lobby lobbyToStart = new Lobby();
        User owner = new User();
        Lot lobbyLot = new Lot();
        lobbyToStart.setLot(lobbyLot);
        User user = new User();
        user.setId(2L);
        lobbyToStart.setOwner(user);
        owner.setId(1L);
        owner.setEmail("test");
        Mockito.when(userService.findByEmail("test")).thenReturn(Optional.of(owner));
        Assertions.assertThrows(LobbyException.class, () -> lobbyService.startLobby(lobbyToStart, "test"));
    }

    @Test
    public void joinLobbyTest_Success() {
        Lobby lobbyFromDb = new Lobby();
        User owner = new User();
        owner.setId(1L);
        lobbyFromDb.setOwner(owner);
        lobbyFromDb.setCurrentUsers(1);
        User joiner = new User();
        joiner.setId(2L);
        joiner.setEmail("test");
        Mockito.when(userService.findByEmail("test")).thenReturn(Optional.of(joiner));
        lobbyService.joinLobby(lobbyFromDb, "test");
    }

    @Test
    public void joinLobbyTest_LobbyException_LobbyIsFullCase() {
        Lobby lobbyFromDb = new Lobby();
        User owner = new User();
        owner.setId(1L);
        lobbyFromDb.setOwner(owner);
        lobbyFromDb.setCurrentUsers(3);
        lobbyFromDb.setLobbyStatus(LobbyStatus.FULL);
        User joiner = new User();
        joiner.setId(2L);
        joiner.setEmail("test");
        Mockito.when(userService.findByEmail("test")).thenReturn(Optional.of(joiner));
        Assertions.assertThrows(LobbyException.class,() -> lobbyService.joinLobby(lobbyFromDb, "test"));
    }

    @Test
    public void joinLobbyTest_LobbyException_AlreadyInLobbyCase() {
        Lobby lobbyFromDb = new Lobby();
        User owner = new User();
        owner.setId(1L);
        lobbyFromDb.setOwner(owner);
        lobbyFromDb.setCurrentUsers(1);
        User joiner = new User();
        joiner.setId(2L);
        joiner.setLobby(new Lobby());
        joiner.setEmail("test");
        Mockito.when(userService.findByEmail("test")).thenReturn(Optional.of(joiner));
        Assertions.assertThrows(LobbyException.class,() -> lobbyService.joinLobby(lobbyFromDb, "test"));
    }

    @Test
    public void leaveLobbyTest_Success() {
        Lobby lobbyFromDb = new Lobby();
        lobbyFromDb.setCurrentUsers(2);
        User leaver = new User();
        leaver.setEmail("test");
        leaver.setId(1L);
        Mockito.when(userService.findByEmail("test")).thenReturn(Optional.of(leaver));
        lobbyService.leaveLobby(lobbyFromDb, "test");
    }

    @Test
    public void LeaveLobbyTest_LobbyException() {
        Lobby lobbyFromDb = new Lobby();
        lobbyFromDb.setCurrentUsers(2);
        lobbyFromDb.setLobbyStatus(LobbyStatus.STARTED);
        User leaver = new User();
        leaver.setEmail("test");
        leaver.setId(1L);
        Assertions.assertThrows(LobbyException.class, () -> lobbyService.leaveLobby(lobbyFromDb, "test"));
    }
}
