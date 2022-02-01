package by.itstep.auction.controller.restController;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.service.impl.ItemServiceImpl;
import by.itstep.auction.service.impl.UserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/item")
public class ItemRestController {

    private final ItemServiceImpl itemService;
    private final UserServiceImpl userService;

    public ItemRestController(ItemServiceImpl itemService, UserServiceImpl userService) {
        this.itemService = itemService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize(value = "hasAuthority('items:read')")
    public Iterable<Item> getAll() {
        return itemService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('items:read')")
    public Item getOne(@PathVariable Long id) {
        return itemService.findItemById(id);
    }

    @PostMapping
    @PreAuthorize(value = "hasAuthority('items:write')")
    public Item createUser(@RequestBody Item item, Principal principal) {
        return itemService.createItem(item, userService.findByEmail(principal.getName()).orElseThrow());
    }

    @PutMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('items:write')")
    public Item update(@PathVariable("id") Item itemFromDb, @RequestBody Item item) {
        return itemService.updateItem(itemFromDb, item);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('items:write')")
    public void delete(@PathVariable("id") Item item) {
        itemService.delete(item);
    }
}
