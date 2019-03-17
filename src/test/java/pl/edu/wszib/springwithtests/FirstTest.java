package pl.edu.wszib.springwithtests;


import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class FirstTest {

    @BeforeClass
    public static void przedWszystkimi(){

    }

    @Before // przed kazdym testem
    public void przed(){

    }

    // do testow
    @Test
    public void test(){
        List list = Mockito.mock(ArrayList.class);
        Mockito.when(list.size()).thenReturn(1);
        Assert.assertEquals("nie ma ani jednego elementu!", 1, list.size());
    }



    @After
    public void po(){

    }

    @AfterClass
    public static void  poWszystkim(){

    }



}
