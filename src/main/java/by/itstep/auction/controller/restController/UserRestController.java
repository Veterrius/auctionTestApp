package by.itstep.auction.controller.restController;

import by.itstep.auction.dao.model.User;
import by.itstep.auction.service.impl.UserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final UserServiceImpl userService;

    public UserRestController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize(value = "hasAuthority('users:read')")
    public Iterable<User> getAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('users:read')")
    public User getOne(@PathVariable Long id) {
        return userService.findById(id).orElseThrow();
    }

    @PostMapping
    @PreAuthorize(value = "hasAuthority('users:write')")
    public User post(@RequestBody User user) {
        return userService.createUser(user);
    }

    //Now only money is updated
    @PutMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('users:write')")
    public User update(@PathVariable("id") User userFromDb, @RequestBody User user) {
        return userService.updateUser(userFromDb, user);
    }

    @PostMapping("/m")
    @PreAuthorize(value = "hasAuthority('users:money')")
    public User updateMoney(Principal principal, @RequestBody Double money){
        return userService.updateMoney(userService.findByEmail(principal.getName()).orElseThrow(), money, true);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('users:write')")
    public void delete(@PathVariable("id") User user) {
        userService.deleteUser(user);
    }
}
