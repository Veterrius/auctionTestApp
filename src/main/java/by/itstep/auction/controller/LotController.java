package by.itstep.auction.controller;


import by.itstep.auction.service.LotService;
import by.itstep.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;

@Controller
public class LotController {

    @Autowired
    private LotService lotService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/lots")
    public String main(Map<String, Object> model) {
        model.put("lots", lotService.findAllLots());
        return "lots";
    }

    @PostMapping(value = "/lots")
    public String createLot(@RequestParam Long id, Map<String, Object> model, Principal principal) {
        lotService.createLotByItemId(id, userService.findUserByName(principal.getName()));
        model.put("lots", lotService.findAllLots());
        return "lots";
    }

}
