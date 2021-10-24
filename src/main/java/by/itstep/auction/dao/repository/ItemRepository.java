package by.itstep.auction.dao.repository;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findItemByName(String name);

    Iterable<Item> findItemsByUser(User user);

    Item findItemById(Long id);
}
