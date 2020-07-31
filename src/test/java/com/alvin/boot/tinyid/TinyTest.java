package com.alvin.boot.tinyid;

import com.alvin.boot.tinyid.util.TinyId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName TinyTest
 * @Description
 * @Author alvin
 * @Date 2020/7/31 15:22
 * @Version V1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class TinyTest {

    @Test
    public void testNextId() {
        for (int i = 0; i < 100; i++) {
            Long id = TinyId.nextId("test");
            System.out.println("current id is: " + id);
        }
        for (int i = 0; i < 100; i++) {
            Long id = TinyId.nextId("test_odd");
            System.out.println("==> current id is: " + id);
        }
    }
}
