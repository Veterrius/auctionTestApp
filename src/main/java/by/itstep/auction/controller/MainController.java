package by.itstep.auction.controller;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.User;
import by.itstep.auction.service.ItemService;
import by.itstep.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;

@Controller
public class MainController {

    private final ItemService itemService;

    private final UserService userService;

    public MainController(ItemService itemService, UserService userService) {
        this.itemService = itemService;
        this.userService = userService;
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model, Principal principal) {
        Iterable<Item> items = itemService.findItemsOfUser(userService.findUserByName(principal.getName()));
        model.put("items", items);
        model.put("current", principal.getName());
        return "main";
    }

    @PostMapping(value = "/main")
    public String add(Item item, Map<String, Object> model, Principal principal) {
        User user = userService.findUserByName(principal.getName());

//        if (item.getPrice() == null | item.getQuantity() == null) {
//            model.put("message", "Enter price and quantity of your item");
//            Iterable<Item> items = itemService.findItemsOfUser(user);
//            model.put("items", items);
//            model.put("current", principal.getName());
//            return "main";
//        }

        itemService.createItem(item, user);
        Iterable<Item> items = itemService.findItemsOfUser(user);
        model.put("items", items);
        model.put("current", principal.getName());
        return "main";
    }
}
