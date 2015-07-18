package com.agh.studio_projektowe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.agh.studio_projektowe")
public class ServerStart {

        public static void main(String[] args) throws Exception {
            ConfigurableApplicationContext applicationContext = SpringApplication.run(TestClass.class, args);
        }
}
