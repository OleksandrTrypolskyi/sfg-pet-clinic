package guru.springframework.sfgpetclinic.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(classes = ControllerTestConfiguration.class)
class OwnerControllerTest {

    @Autowired
    private OwnerController ownerController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Test
    void listOwners() throws Exception {
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasNoErrors())
                .andExpect(model().attributeExists("owners"))
                .andExpect(view().name("owners/index"));
    }
}