package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.services.map.OwnerServiceMap;
import guru.springframework.sfgpetclinic.services.map.VetServiceMap;
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
    VetController vetController(VetService vetService) {
        return new VetController(vetService);
    }

    @Bean
    OwnerService ownerService() {
        return new OwnerServiceMap();
    }

    @Bean
    VetService vetService() {
        return new VetServiceMap();
    }
}
