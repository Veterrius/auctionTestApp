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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Lobby lobby) {
        lobbyService.delete(lobby);
    }
}
