package pl.edu.wszib.springwithtests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.edu.wszib.springwithtests.dto.ProductDTO;
import pl.edu.wszib.springwithtests.model.Vat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpringMVCTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test //nic nie istnieje
    public void testShoppingBasketNotExist() throws Exception {
        int testBasketId = 34;
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(1);
        productDTO.setVat(Vat.VALUE_8);
        productDTO.setCost(57d);
        productDTO.setName("Test produkt");

        mockMvc.perform(MockMvcRequestBuilders.post("/shoppingBasket/add")
                .contentType("application/json")
                .content(objectMapper.writer().writeValueAsBytes(productDTO))
                .param("shoppingBasketId", String.valueOf(testBasketId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }



}
