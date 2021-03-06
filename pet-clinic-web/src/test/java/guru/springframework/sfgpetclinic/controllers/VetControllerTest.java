package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.services.VetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {

    private VetController vetController;
    @Mock
    private VetService vetService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        vetController = new VetController(vetService);
        mockMvc = MockMvcBuilders.standaloneSetup(vetController).build();
    }

    @Test
    void listVets() throws Exception {
        mockMvc.perform(get("/vets"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasNoErrors())
                .andExpect(model().attributeExists("vets"))
                .andExpect(view().name("vets/index"));
    }
}