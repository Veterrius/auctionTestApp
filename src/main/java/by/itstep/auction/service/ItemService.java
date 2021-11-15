package by.itstep.auction.service;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.User;
import by.itstep.auction.dao.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item findItemByName(String name) {
        return itemRepository.findItemByName(name);
    }

    public Item createItem(Item item, User user) {
        item.setUser(user);
        return itemRepository.save(item);
    }

    public Iterable<Item> findAllItems() {
        return itemRepository.findAll();
    }

    public Iterable<Item> findItemsOfUser(User user) {
        return itemRepository.findItemsByUser(user);
    }
}
