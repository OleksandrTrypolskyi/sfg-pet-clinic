package guru.springframework.sfgpetclinic.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerTestConfiguration {

    @Bean
    IndexController indexController() {
        return new IndexController();
    }

    @Bean
    OwnerController ownerController() {
        return new OwnerController();
    }

    @Bean
    VetController vetController() {
        return new VetController();
    }
}
