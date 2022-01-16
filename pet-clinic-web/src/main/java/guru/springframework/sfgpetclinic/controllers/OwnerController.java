package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/owners")
    public String listOwners(Model model) {
        model.addAttribute("owners", ownerService.findAll());
        return "owners/ownersList";
    }

    @GetMapping("/owners/find")
    public String findOwners() {
        return "notimplemented";
    }
}
