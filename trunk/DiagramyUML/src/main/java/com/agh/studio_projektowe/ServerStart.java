package com.agh.studio_projektowe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.concurrent.TimeUnit;


//@Configuration
//@ComponentScan("com.agh.studio_projektowe")
//@EnableAutoConfiguration
@SpringBootApplication
public class ServerStart {

//    @Bean
//    public WebMvcConfigurerAdapter forwardToIndex() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addViewControllers(ViewControllerRegistry registry) {
//                // forward requests to /admin and /user to their index.html
//                registry.addViewController("/").setViewName("forward:/index.html");
//            }
//        };
//    }
//    @Bean
//    public EmbeddedServletContainerFactory servletContainer() {
//        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
//        factory.setPort(8080);
//        factory.setSessionTimeout(1, TimeUnit.MINUTES);
//        //factory.addErrorPages(new ErrorPage(HttpStatus.404, "/notfound.html"));
//        return factory;
//    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ServerStart.class, args);
    }
}
