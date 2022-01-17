package by.itstep.auction.controller.restController;

import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.User;
import by.itstep.auction.service.LotService;
import by.itstep.auction.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/transaction")
public class TransactionRestController {

    private final UserService userService;
    private final LotService lotService;

    public TransactionRestController(UserService userService, LotService lotService) {
        this.userService = userService;
        this.lotService = lotService;
    }

    @PostMapping
    @PreAuthorize(value = "hasAuthority('transaction')")
    public User buy(@RequestBody Long id, Principal principal) {
        return userService.purchase(userService.findByEmail(principal.getName()).orElseThrow(),
                lotService.findLotById(id).orElseThrow());
    }
}
