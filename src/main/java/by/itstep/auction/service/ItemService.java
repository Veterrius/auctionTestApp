package by.itstep.auction.service;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.User;

import java.util.Optional;

public interface ItemService {

    Item createItem(Item item, User user);

    Iterable<Item> getAll();

    Optional<Item> findItemById(Long id);

    Item updateItem(Item itemToUpdate, Item updatedItem);

    void delete(Item item);
}
