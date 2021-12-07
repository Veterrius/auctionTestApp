package by.itstep.auction.controller.restController;


import by.itstep.auction.dao.model.User;
import by.itstep.auction.service.ItemService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {

    private final ItemService itemService;

    public MainController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();
        data.put("profile", user);
        data.put("items", itemService.findItemsOfUser(user));
        model.addAttribute("frontEndData", data);
        return "index";
    }
}
