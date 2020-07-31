package com.alvin.boot.tinyid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName TinyIdApplication
 * @Description
 * @Author alvin
 * @Date 2020/7/31 18:37
 * @Version V1.0
 **/
@EnableTransactionManagement
@SpringBootApplication
public class TinyIdApplication {

    public static void main(String[] args) {
        SpringApplication.run(TinyIdApplication.class, args);
    }
}
