//package by.itstep.auction.controller;
//
//import by.itstep.auction.dao.model.User;
//import by.itstep.auction.service.UserService;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.security.Principal;
//import java.util.Map;
//
//@Controller
//public class ProfileController {
//
//    private final UserService userService;
//
//    public ProfileController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping(value = "/profile")
//    public String main(Map<String, Object> model, Principal principal) {
//        User user = userService.findUserByName(principal.getName());
//        model.put("user", user);
//        return "profile";
//    }
//
//    @PostMapping(value = "/profile")
//    public String addMoney(@RequestParam Double money,
//                           Principal principal, Map<String, Object> model) {
//        User user = userService.findUserByName(principal.getName());
//        user = userService.updateMoney(user, money, true);
//        model.put("user", user);
//        return "profile";
//    }
//}
