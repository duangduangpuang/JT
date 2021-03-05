package com.jt.test;

import com.jt.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringBootTests {

    @Autowired
    ItemService itemService;

    @Test
    public void testFindItem(){
        itemService.findItemsByPage(1,20);
    }
}
