package by.itstep.auction.controller.restController;

import by.itstep.auction.dao.model.Lobby;
import by.itstep.auction.dao.model.dto.LobbyRequestDTO;
import by.itstep.auction.service.LobbyService;
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
    public List<Lobby> getAll() {
        return lobbyService.getAll();
    }

    @GetMapping("/{id}")
    public Lobby getOne(@PathVariable Long id) {
        return lobbyService.getLobbyById(id).orElseThrow();
    }

    @PostMapping
    public Lobby create(@RequestBody LobbyRequestDTO lobbyRequestDTO) {
        return lobbyService.createLobby(lobbyRequestDTO);
    }

    @PostMapping("/{id}/start")
    public Lobby startLobby(@PathVariable("id") Lobby lobby,  Principal principal) {
        return lobbyService.startLobby(lobby, principal.getName());
    }

    @PostMapping("/{id}/join")
    public Lobby joinLobby(@PathVariable("id") Lobby lobby, Principal principal) {
        return lobbyService.joinLobby(lobby, principal.getName());
    }

    @PostMapping("/{id}/leave")
    public Lobby leaveLobby(@PathVariable("id") Lobby lobby, Principal principal) {
        return lobbyService.leaveLobby(lobby, principal.getName());
    }

    @PostMapping("/{id}/bet")
    public Lobby placeBet(@PathVariable("id") Lobby lobby, @RequestBody Double bet, Principal principal) {
        return lobbyService.placeBet(lobby, bet, principal);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Lobby lobby) {
        lobbyService.delete(lobby);
    }
}
