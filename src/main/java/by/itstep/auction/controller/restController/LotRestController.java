package by.itstep.auction.controller.restController;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.service.LotService;
import by.itstep.auction.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PrePersist;
import java.security.Principal;

@RestController
@RequestMapping("/api/lots")
public class LotRestController {

    private final LotService lotService;
    private final UserService userService;

    public LotRestController(LotService lotService, UserService userService) {
        this.lotService = lotService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize(value = "hasAuthority('lots:read')")
    public Iterable<Lot> getAll() {
        return lotService.findAllLots();
    }

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('lots:read')")
    public Lot getOne(@PathVariable Long id) {
        return lotService.findLotById(id).orElseThrow();
    }

    @PostMapping
    @PreAuthorize(value = "hasAuthority('lots:write')")
    public Lot create(@RequestBody Long itemId, Principal principal) {
        return lotService.createLotByItemId(itemId, userService.findByEmail(principal.getName()).orElseThrow());
    }

    @PutMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('lots:write')")
    public Lot update(@PathVariable("id") Lot lotFromDb, @RequestBody Lot updatedLot) {
        return lotService.updateLot(lotFromDb, updatedLot);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('lots:write')")
    public void delete(@PathVariable Long id) {
        lotService.deleteLotById(id);
    }
}
