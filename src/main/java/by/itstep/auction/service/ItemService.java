package by.itstep.auction.service;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.User;

public interface ItemService {
    Item findItemByName(String name);

    Item createItem(Item item, User user);

    Iterable<Item> findAllItems();

    Iterable<Item> findItemsOfUser(User user);

    Iterable<Item> getAll();

    Item findItemById(Long id);

    Item createItemWithoutUser(Item item);

    Item updateItem(Item itemToUpdate, Item updatedItem);

    void delete(Item item);
}
