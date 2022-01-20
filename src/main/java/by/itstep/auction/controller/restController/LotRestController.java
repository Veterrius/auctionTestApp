package by.itstep.auction.controller.restController;

import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.dto.LotDTO;
import by.itstep.auction.service.impl.LotServiceImpl;
import by.itstep.auction.service.impl.UserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/lots")
public class LotRestController {

    private final LotServiceImpl lotService;
    private final UserServiceImpl userService;

    public LotRestController(LotServiceImpl lotService, UserServiceImpl userService) {
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
    public Lot create(@RequestBody LotDTO lotDTO) {
        return lotService.createLot(lotDTO.getItemId(), lotDTO.getType(), lotDTO.getValidity());
    }

    @PutMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('lots:write')")
    public Lot update(@PathVariable("id") Lot lotFromDb, @RequestBody Double newPrice) {
        return lotService.updateLot(lotFromDb, newPrice);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('lots:write')")
    public void delete(@PathVariable Long id) {
        lotService.deleteLotById(id);
    }
}
