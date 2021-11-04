package by.itstep.auction.controller;


import by.itstep.auction.service.ItemService;
import by.itstep.auction.service.LotService;
import by.itstep.auction.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;

@Controller
public class LotController {

    private final LotService lotService;

    private final UserService userService;

    private final ItemService itemService;

    public LotController(LotService lotService, UserService userService, ItemService itemService) {
        this.lotService = lotService;
        this.userService = userService;
        this.itemService = itemService;
    }

    @GetMapping(value = "/lots")
    public String main(Map<String, Object> model, Principal principal) {
        model.put("lots", lotService.findAllLots());
        model.put("items", itemService.findItemsOfUser(userService.findUserByName(principal.getName())));
        return "lots";
    }

    @PostMapping(value = "/lots/create")
    public String createLot(@RequestParam Long id, Map<String, Object> model, Principal principal) {
        lotService.createLotByItemId(id, userService.findUserByName(principal.getName()));
        model.put("lots", lotService.findAllLots());
        model.put("items", itemService.findItemsOfUser(userService.findUserByName(principal.getName())));
        return "/lots";
    }

}
