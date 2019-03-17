package pl.edu.wszib.springwithtests.service.impl;

import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import pl.edu.wszib.springwithtests.dao.ProductDao;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductDao mockDao;

    @Spy
    Mapper mapper = new DozerBeanMapper();


    @Before
    public void setUp(){
        productService = new ProductServiceImpl();
        mockDao = Mockito.mock(ProductDao.class);
        mapper = new DozerBeanMapper();
    }

    @Test
    public void test(){
        productService.add(null);
    }


}
