package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.services.map.OwnerServiceMap;
import guru.springframework.sfgpetclinic.services.map.PetServiceMap;
import guru.springframework.sfgpetclinic.services.map.PetTypeServiceMap;
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
    OwnerService ownerService(PetTypeService petTypeService, PetService petService) {
        return new OwnerServiceMap(petTypeService, petService);
    }

    @Bean
    PetService petService() {
        return new PetServiceMap();
    }

    @Bean
    PetTypeService petTypeService() {
        return new PetTypeServiceMap();
    }

    @Bean
    VetService vetService() {
        return new VetServiceMap();
    }
}
