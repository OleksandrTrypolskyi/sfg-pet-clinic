package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

@Controller
public class OwnerController {

    public static final String OWNERS_FIND_OWNERS = "owners/findOwners";
    public static final String OWNERS_OWNERS_LIST = "owners/ownersList";
    public static final String OWNERS_NEW = "/owners/new";
    public static final String OWNERS_OWNER_ID_EDIT = "/owners/{ownerId}/edit";
    public static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

//    @GetMapping("/owners")
//    public String listOwners(Model model) {
//        model.addAttribute("owners", ownerService.findAll());
//        return "owners/ownersList";
//    }

    @GetMapping("/owners")
    public String processFindForm(Owner owner, BindingResult bindingResult, Model model) {
        final List<Owner> owners;

        if(owner.getLastName() == null || owner.getLastName().equals("")) {
            owners = Collections.emptyList();
        } else {
            owners = ownerService.findByLastNameContainingIgnoreCase(owner.getLastName());
        }
//        final List<Owner> owners = ownerService.findByLastNameLike("%" + owner.getLastName() + "%");

        if(owners.isEmpty()) {
            bindingResult.rejectValue("lastName", "notFound", "not found");
            return OWNERS_FIND_OWNERS;
        } else if (owners.size() == 1) {
            return "redirect:/owners/" + owners.get(0).getId();
        } else {
            model.addAttribute("owners", owners);
            return OWNERS_OWNERS_LIST;
        }
    }



    @GetMapping("/owners/find")
    public String findOwners(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return OWNERS_FIND_OWNERS;
    }

    @GetMapping("/owners/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId) {
        final ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
        modelAndView.addObject("owner", ownerService.findById(ownerId));
        return modelAndView;
    }

    @GetMapping(OWNERS_NEW)
    public String getCreationForm(Model model) {
        return getOwnerFormWithAttribute(model, Owner.builder().build());
    }

    @PostMapping(OWNERS_NEW)
    public String processCreationForm(@ModelAttribute Owner owner, BindingResult bindingResult) {
        return createOrUpdateOwner(owner, bindingResult);
    }

    @GetMapping(OWNERS_OWNER_ID_EDIT)
    public String initUpdateForm(@PathVariable String ownerId, Model model) {
        return getOwnerFormWithAttribute(model, ownerService.findById(Long.parseLong(ownerId)));
    }

    @PostMapping(OWNERS_OWNER_ID_EDIT)
    public String processUpdateForm(@ModelAttribute Owner owner, BindingResult bindingResult) {
        return createOrUpdateOwner(owner, bindingResult);
    }

    private String createOrUpdateOwner(@ModelAttribute Owner owner, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return OWNERS_CREATE_OR_UPDATE_OWNER_FORM;
        } else {
            final Owner savedOwner = ownerService.save(owner);
            return "redirect:/owners/" + savedOwner.getId();
        }
    }


    private String getOwnerFormWithAttribute(Model model, Owner owner) {
        model.addAttribute("owner", owner);
        return OWNERS_CREATE_OR_UPDATE_OWNER_FORM;
    }
}
