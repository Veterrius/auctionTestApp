package by.itstep.auction.controller.restController;

import by.itstep.auction.dao.model.User;
import by.itstep.auction.service.impl.LotServiceImpl;
import by.itstep.auction.service.impl.UserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/transaction")
public class TransactionRestController {

    private final UserServiceImpl userService;
    private final LotServiceImpl lotService;

    public TransactionRestController(UserServiceImpl userService, LotServiceImpl lotService) {
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
