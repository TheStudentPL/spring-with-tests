package pl.edu.wszib.springwithtests.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wszib.springwithtests.model.ShoppingBasketItem;

import java.util.List;

@Repository
public interface ShoppingBasketItemDao extends CrudRepository<ShoppingBasketItem, Integer> {

    ShoppingBasketItem findByProductIdAndShoppingBasketId(Integer productID, Integer shoppingBasketID);
    List<ShoppingBasketItem> findAllByShoppingBasketId(Integer id);
    List<ShoppingBasketItem> findAll();

}
