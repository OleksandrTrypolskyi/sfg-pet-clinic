package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {
    public static final String PETS_CREATE_OR_UPDATE_PET_FORM = "pets/createOrUpdatePetForm";
    public static final String PETS_NEW = "/pets/new";
    private final OwnerService ownerService;
    private final PetService petService;
    private final PetTypeService petTypeService;

    public PetController(OwnerService ownerService, PetService petService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.petService = petService;
        this.petTypeService = petTypeService;
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner populateOwner(@PathVariable String ownerId) {
        return ownerService.findById(Long.parseLong(ownerId));
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
    }

    @GetMapping(PETS_NEW)
    public String initCreationForm(Model model, Owner owner) {
        final Pet pet = Pet.builder().build();
        pet.setOwner(owner);
        model.addAttribute("pet", pet);
        return PETS_CREATE_OR_UPDATE_PET_FORM;
    }

    @PostMapping(PETS_NEW)
    public String processCreationForm(Owner owner, Pet pet, BindingResult bindingResult,
                                      Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pet", pet);
            return PETS_CREATE_OR_UPDATE_PET_FORM;
        } else {
            pet.setOwner(owner);
            petService.save(pet);
            return "redirect:/owners/" + owner.getId();
        }
    }

    @GetMapping("/pets/{petId}/edit")
    public String initUpdateForm(@PathVariable String petId, Model model, Owner owner) {
        final Pet pet = petService.findById(Long.parseLong(petId));
        pet.setOwner(owner);
        model.addAttribute("pet", pet);
        return PETS_CREATE_OR_UPDATE_PET_FORM;
    }

    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(Pet pet, BindingResult bindingResult, Model model,
                                    Owner owner) {
        log.info(pet.toString());
        if (bindingResult.hasErrors()) {
            model.addAttribute("pet", pet);
            return PETS_CREATE_OR_UPDATE_PET_FORM;
        } else {
            pet.setOwner(owner);
            final Pet savedPet = petService.save(pet);
            return "redirect:/owners/" + owner.getId();
        }
    }


}
