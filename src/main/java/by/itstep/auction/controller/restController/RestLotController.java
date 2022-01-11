package by.itstep.auction.controller.restController;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.service.LotService;
import by.itstep.auction.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/lots")
public class RestLotController {

    private final LotService lotService;
    private final UserService userService;

    public RestLotController(LotService lotService, UserService userService) {
        this.lotService = lotService;
        this.userService = userService;
    }

    @GetMapping
    public Iterable<Lot> getAll() {
        return lotService.findAllLots();
    }

    @GetMapping("/{id}")
    public Lot getOne(@PathVariable Long id) {
        return lotService.findLotById(id).orElseThrow();
    }

    @PostMapping
    public Lot create(@RequestBody Item item, Principal principal) {
        return lotService.createLotByItemId(item.getId(), userService.findByEmail(principal.getName()).orElseThrow());
    }

    @PutMapping("/{id}")
    public Lot update(@PathVariable("id") Lot lotFromDb, @RequestBody Lot updatedLot) {
        return lotService.updateLot(lotFromDb, updatedLot);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        lotService.deleteLotById(id);
    }
}
