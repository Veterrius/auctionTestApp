package by.itstep.auction.dao.repository;

import by.itstep.auction.dao.model.Item;
import by.itstep.auction.dao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findItemByName(String name);

    Iterable<Item> findItemsByUser(User user);

    Optional<Item> findItemById(Long id);
}
