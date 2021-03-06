package by.itstep.auction.controller;

import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.User;
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
public class VerificationController {

    @Autowired
    private LotService lotService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/verification")
    public String main(@RequestParam Long id, Map<String, Object> model, Principal principal) {
        Lot lot = lotService.findLotById(id);
        model.put("lot", lot);
        model.put("user", userService.findUserByName(principal.getName()));
        return "verification";
    }

    @PostMapping(value = "/verification")
    public String purchase(@RequestParam Long id, Principal principal) {
        Lot lot = lotService.findLotById(id);
        User user = userService.findUserByName(principal.getName());
        userService.purchase(user,lot);
        return "lots";
    }
}
