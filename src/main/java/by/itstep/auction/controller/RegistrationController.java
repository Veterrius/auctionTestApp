package by.itstep.auction.controller;

import by.itstep.auction.dao.model.User;
import by.itstep.auction.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        UserDetails userFromDb = userService.loadUserByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("message", "userExists!");
            return "registration";
        }

        userService.createUser(user);
        return "login";
    }
}
