package by.itstep.auction.controller.restController;

import by.itstep.auction.dao.model.Lobby;
import by.itstep.auction.dao.model.dto.LobbyRequestDTO;
import by.itstep.auction.service.LobbyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/lobby")
public class LobbyRestController {

    private final LobbyService lobbyService;

    public LobbyRestController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
    }

    @GetMapping
    @PreAuthorize(value = "hasAuthority('lobby:all')")
    public List<Lobby> getAll() {
        return lobbyService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('lobby:all')")
    public Lobby getOne(@PathVariable Long id) {
        return lobbyService.getLobbyById(id).orElseThrow();
    }

    @PostMapping
    @PreAuthorize(value = "hasAuthority('lobby:all')")
    public Lobby create(@RequestBody LobbyRequestDTO lobbyRequestDTO) {
        return lobbyService.createLobby(lobbyRequestDTO);
    }

    @PostMapping("/{id}/start")
    @PreAuthorize(value = "hasAuthority('lobby:all')")
    public Lobby startLobby(@PathVariable("id") Lobby lobby,  Principal principal) {
        return lobbyService.startLobby(lobby, principal.getName());
    }

    @PostMapping("/{id}/join")
    @PreAuthorize(value = "hasAuthority('lobby:all')")
    public Lobby joinLobby(@PathVariable("id") Lobby lobby, Principal principal) {
        return lobbyService.joinLobby(lobby, principal.getName());
    }

    @PostMapping("/{id}/leave")
    @PreAuthorize(value = "hasAuthority('lobby:all')")
    public Lobby leaveLobby(@PathVariable("id") Lobby lobby, Principal principal) {
        return lobbyService.leaveLobby(lobby, principal.getName());
    }

    @PostMapping("/{id}/bet")
    @PreAuthorize(value = "hasAuthority('lobby:all')")
    public Lobby placeBet(@PathVariable("id") Lobby lobby, @RequestBody Double bet, Principal principal) {
        return lobbyService.placeBet(lobby, bet, principal);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('lobby:all')")
    public void delete(@PathVariable("id") Lobby lobby) {
        lobbyService.deleteWithLot(lobby);
    }
}
