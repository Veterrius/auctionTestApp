package by.itstep.auction.service.impl;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.User;
import by.itstep.auction.dao.repository.ItemRepository;
import by.itstep.auction.service.ItemService;
import by.itstep.auction.service.exceptions.InvalidItemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private final Logger l = LoggerFactory.getLogger(LobbyServiceImpl.class);

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item createItem(Item item, User user) {
        validateItem(item);
        item.setUser(user);
        l.info("Item "+item.getName()+"was created by "+item.getUser().getEmail()+"#"+item.getUser().getId());
        return itemRepository.save(item);
    }

    private void validateItem(Item item) {
        if (item.getName() == null || item.getPrice() == null || item.getPrice() <= 0) {
            throw new InvalidItemException("Item is invalid");
        }
    }

    @Override
    public Iterable<Item> getAll() {
        return itemRepository.findAll();
    }

    @Override
    public Optional<Item> findItemById(Long id) {
       return itemRepository.findItemById(id);
    }

    @Override
    public Item updateItem(Item itemToUpdate, Item updatedItem) {
        itemToUpdate.setDescription(updatedItem.getDescription());
        itemToUpdate.setPrice(updatedItem.getPrice());
        itemToUpdate.setName(updatedItem.getName());
        itemRepository.save(itemToUpdate);
        l.info("Item#"+itemToUpdate.getId()+"was updated");
        return itemToUpdate;
    }

    @Override
    public void delete(Item item) {
        itemRepository.delete(item);
    }
}
