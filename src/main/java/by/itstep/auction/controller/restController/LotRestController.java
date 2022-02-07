package by.itstep.auction.controller.restController;

import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.dto.LotRequestDTO;
import by.itstep.auction.service.impl.LotServiceImpl;
import by.itstep.auction.service.impl.UserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/lot")
public class LotRestController {

    private final LotServiceImpl lotService;

    public LotRestController(LotServiceImpl lotService) {
        this.lotService = lotService;
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
    public Lot create(@RequestBody LotRequestDTO lotRequestDTO, Principal principal) {
        return lotService.createLot(lotRequestDTO.getItemId(), lotRequestDTO.getType(),
                lotRequestDTO.getValidity(), principal.getName());
    }

    @PutMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('lots:write')")
    public Lot update(@PathVariable("id") Lot lotFromDb, @RequestBody Double newPrice) {
        return lotService.updateLot(lotFromDb, newPrice);
    }

    @PutMapping("/{id}/bet")
    public Lot placeNewBet(@PathVariable("id") Lot lotFromDb, @RequestBody Double bet, Principal principal) {
        return lotService.placeNewBet(lotFromDb, bet, principal.getName());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('lots:write')")
    public void delete(@PathVariable Long id) {
        lotService.deleteLotById(id);
    }
}
