package pl.edu.wszib.springwithtests.service.impl;

import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.dozer.Mapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import pl.edu.wszib.springwithtests.NotFoundException;
import pl.edu.wszib.springwithtests.dao.ProductDao;


import org.mockito.*;
import pl.edu.wszib.springwithtests.dao.ShoppingBasketDao;
import pl.edu.wszib.springwithtests.dao.ShoppingBasketItemDao;
import pl.edu.wszib.springwithtests.dto.ProductDTO;
import pl.edu.wszib.springwithtests.dto.ShoppingBasketDTO;
import pl.edu.wszib.springwithtests.model.Product;
import pl.edu.wszib.springwithtests.model.ShoppingBasket;
import pl.edu.wszib.springwithtests.model.ShoppingBasketItem;
import pl.edu.wszib.springwithtests.model.Vat;
import pl.edu.wszib.springwithtests.service.ShoppingBasketService;

import java.util.Collections;
import java.util.Optional;

@RunWith(JUnit4.class)
public class ShoppingBasketServiceTest {

    @InjectMocks
    ShoppingBasketServiceImpl basketService;

    @Mock
    ProductDao productDao;

    @Mock
    ShoppingBasketDao shoppingBasketDao;

    @Mock
    ShoppingBasketItemDao shoppingBasketItemDao;

    @Spy
    Mapper mapper = new DozerBeanMapper();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this); // inicjalizujemy wszystkie mocki
    }

    @Test
    public void testShoppingBasketIdNotExist(){
        Integer testShoppingBasketID = 34;
        ProductDTO productDTO = Mockito.mock(ProductDTO.class);
        expectedException.expect(NotFoundException.class);
        basketService.addProduct(testShoppingBasketID, productDTO);

    }
    // istnieje koszyk nie istnieje produkt 2 przypadek
    @Test
    public void testShoppingBasketExistProductNotExist(){
        Integer testShoppingBasketID = 36;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setVat(Vat.VALUE_8);
        productDTO.setName("Jestem2!");
        productDTO.setCost(11d);
        productDTO.setId(testShoppingBasketID);

        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.setId(testShoppingBasketID);

        Mockito.when(shoppingBasketDao.findById(testShoppingBasketID))
                .thenReturn(Optional.of(shoppingBasket)); // optional moze byc null

        expectedException.expect(NotFoundException.class); // produkt moze byc exception
        basketService.addProduct(testShoppingBasketID, productDTO);
    }

    @Test
    public void testShoppingBasketExistProductExistShoppingBasketItemNotExist(){
        Integer testShoppingBasketID = 36;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setVat(Vat.VALUE_8);
        productDTO.setName("Jestem2!");
        productDTO.setCost(11d);
        productDTO.setId(testShoppingBasketID);

        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.setId(testShoppingBasketID);

        Mockito.when(shoppingBasketDao.findById(testShoppingBasketID))
                .thenReturn(Optional.of(shoppingBasket)); // optional moze byc null

        Mockito.when(productDao.existsById(productDTO.getId()))
                .thenReturn(true);

        ShoppingBasketItem item = new ShoppingBasketItem();
        item.setId(3243);
        item.setShoppingBasket(shoppingBasket);
        item.setAmount(1);
        item.setProduct(mapper.map(productDTO, Product.class));

        Mockito.when(shoppingBasketItemDao.
                findByProductIdAndShoppingBasketId(productDTO.getId(),testShoppingBasketID))
                .thenReturn(item);

        Mockito.when(shoppingBasketItemDao
                .findAllByShoppingBasketId(testShoppingBasketID))
                .thenReturn(Collections.singletonList(item));

        ShoppingBasketDTO result = basketService.addProduct(testShoppingBasketID, productDTO);

        Mockito.verify(shoppingBasketItemDao).save(item);

        Assert.assertEquals(testShoppingBasketID, result.getId());
        Assert.assertEquals(1, result.getItems().size());
        Assert.assertTrue(result.getItems()
                .stream()
                .anyMatch(i ->i.getProduct().getId().equals(productDTO.getId())));

        Assert.assertTrue(result.getItems()
                .stream()
                .filter(i ->i.getProduct().getId()
                        .equals(productDTO.getId()))
                        .findFirst()
                        .map(i -> i.getAmount() == 2)
                        .orElse(false));

    }
    // 4 test
    @Test
    public void testShoppingBasketExistProductExistShoppingBasketItemExist(){
        Integer testShoppingBasketID = 36;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setVat(Vat.VALUE_8);
        productDTO.setName("Jestem3!");
        productDTO.setCost(11d);
        productDTO.setId(testShoppingBasketID);

        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.setId(testShoppingBasketID);

        Mockito.when(shoppingBasketDao.findById(testShoppingBasketID))
                .thenReturn(Optional.of(shoppingBasket)); // optional moze byc null

        Mockito.when(productDao.existsById(productDTO.getId()))
                .thenReturn(true);

        ShoppingBasketItem item = new ShoppingBasketItem();
        item.setId(3243);
        item.setShoppingBasket(shoppingBasket);
        item.setAmount(1);
        item.setProduct(mapper.map(productDTO, Product.class));

        // item nie istnieje mozna usunac, wysteouje null

        Mockito.when(shoppingBasketItemDao
                .findAllByShoppingBasketId(testShoppingBasketID))
                .thenReturn(Collections.singletonList(item));

        ShoppingBasketDTO result = basketService.addProduct(testShoppingBasketID, productDTO);

        // item nie istnieje mozna usunac

        Assert.assertEquals(testShoppingBasketID, result.getId());
        Assert.assertEquals(1, result.getItems().size());
        Assert.assertTrue(result.getItems()
                .stream()
                .anyMatch(i ->i.getProduct().getId().equals(productDTO.getId())));

        Assert.assertTrue(result.getItems()
                .stream()
                .filter(i ->i.getProduct().getId()
                        .equals(productDTO.getId()))
                .findFirst()
                .map(i -> i.getAmount() == 1)
                .orElse(false));

    }


}
