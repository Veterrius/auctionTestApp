package by.itstep.auction.controller.restController;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.service.ItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
public class RestItemController {

    final ItemService itemService;

    public RestItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('items:read')")
    public Iterable<Item> getAll() {
        return itemService.getAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('items:read')")
    public Item getOne(@PathVariable Long id) {
        return itemService.findItemById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize(value = "hasAuthority('items:write')")
    public Item createUser(@RequestBody Item item) {
        return itemService.createItemWithoutUser(item);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @PreAuthorize(value = "hasAuthority('items:write')")
    public Item update(@PathVariable("id") Item itemFromDb, @RequestBody Item item) {
        return itemService.updateItem(itemFromDb, item);
    }

    @PreAuthorize(value = "hasAuthority('items:write')")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Item item) {
        itemService.delete(item);
    }
}
