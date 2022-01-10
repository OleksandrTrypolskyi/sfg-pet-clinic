package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.map.OwnerServiceMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerTestConfiguration {

    @Bean
    IndexController indexController() {
        return new IndexController();
    }

    @Bean
    OwnerController ownerController(OwnerService ownerService) {
        return new OwnerController(ownerService);
    }

    @Bean
    VetController vetController() {
        return new VetController();
    }

    @Bean
    OwnerService ownerService() {
        return new OwnerServiceMap();
    }
}
