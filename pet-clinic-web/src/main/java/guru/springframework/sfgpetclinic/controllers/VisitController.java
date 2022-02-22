package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}")
public class VisitController {
    public static final String PETS_CREATE_OR_UPDATE_VISIT_FORM = "pets/createOrUpdateVisitForm";
    public static final String VISITS_NEW = "/visits/new";
    public static final String VISITS_VISIT_ID_EDIT = "visits/{visitId}/edit";
    private final PetService petService;
    private final VisitService visitService;

    public VisitController(PetService petService, VisitService visitService) {
        this.petService = petService;
        this.visitService = visitService;
    }

    @ModelAttribute("pet")
    public Pet populatePet(@PathVariable String petId) {
        return petService.findById(Long.parseLong(petId));
    }

    @GetMapping("visits/new")
    public String initCreationForm(Model model) {
        return addVisitAttributeAndReturnForm(model, Visit.builder().build());
    }

    private String addVisitAttributeAndReturnForm(Model model, Visit visit) {
        model.addAttribute("visit", visit);
        return PETS_CREATE_OR_UPDATE_VISIT_FORM;
    }

    @PostMapping({VISITS_NEW, VISITS_VISIT_ID_EDIT})
    public String processCreateUpdateForm(@PathVariable String ownerId, Visit visit, BindingResult bindingResult,
                                          Model model, Pet pet) {
        if(bindingResult.hasErrors()) {
            return addVisitAttributeAndReturnForm(model, visit);
        } else {
            visit.setPet(pet);
            visitService.save(visit);
            return "redirect:/owners/" + ownerId;
        }
    }

    @GetMapping(VISITS_VISIT_ID_EDIT)
    public String initUpdateForm(@PathVariable String visitId, Model model) {
        final Visit byId = visitService.findById(Long.parseLong(visitId));
        log.info(String.valueOf(byId.isNew()));
        return addVisitAttributeAndReturnForm(model, byId);
    }


}
