package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.services.*;
import guru.springframework.sfgpetclinic.services.map.*;
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
    VetService vetService(SpecialityService specialityService) {
        return new VetServiceMap(specialityService);
    }

    @Bean
    SpecialityService specialityService() {
        return new SpecialityServiceMap();
    }
}
