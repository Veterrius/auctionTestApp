package by.itstep.auction.service.impl;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.User;
import by.itstep.auction.dao.repository.ItemRepository;
import by.itstep.auction.service.ItemService;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item findItemByName(String name) {
        return itemRepository.findItemByName(name);
    }

    @Override
    public Item createItem(Item item, User user) {
        item.setUser(user);
        return itemRepository.save(item);
    }

    @Override
    public Iterable<Item> findAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Iterable<Item> findItemsOfUser(User user) {
        return itemRepository.findItemsByUser(user);
    }

    @Override
    public Iterable<Item> getAll() {
        return itemRepository.findAll();
    }

    @Override
    public Item findItemById(Long id) {
       return itemRepository.findItemById(id);
    }

    @Override
    public Item createItemWithoutUser(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(Item itemToUpdate, Item updatedItem) {
        itemToUpdate.setDescription(updatedItem.getDescription());
        itemToUpdate.setPrice(updatedItem.getPrice());
        itemToUpdate.setName(updatedItem.getName());
        return itemRepository.save(itemToUpdate);
    }

    @Override
    public void delete(Item item) {
        itemRepository.delete(item);
    }
}
