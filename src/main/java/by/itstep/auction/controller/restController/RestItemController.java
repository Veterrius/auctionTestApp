package by.itstep.auction.controller.restController;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.User;
import by.itstep.auction.service.ItemService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/item")
public class RestItemController {

    final ItemService itemService;

    public RestItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Item> getAll() {
        return itemService.getAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Item getOne(@PathVariable Long id) {
        return itemService.findItemById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Item createUser(@RequestBody Item item) {
        return itemService.createItemWithoutUser(item);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Item update(@RequestBody Item item) {
        Item itemFromDb = itemService.findItemById(item.getId());
        return itemService.updateItem(itemFromDb, item);
    }
}
