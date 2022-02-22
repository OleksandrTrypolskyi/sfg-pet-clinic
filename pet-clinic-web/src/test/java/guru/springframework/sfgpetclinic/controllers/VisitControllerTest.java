package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    public static final String PETS_CREATE_OR_UPDATE_VISIT_FORM = "pets/createOrUpdateVisitForm";
    public static final String OWNERS_1_PETS_1_VISITS_NEW = "/owners/1/pets/1/visits/new";
    public static final String OWNERS_1_PETS_1_VISITS_1_EDIT = "/owners/1/pets/1/visits/1/edit";
    public static final String REDIRECT_OWNERS_1 = "redirect:/owners/1";
    @InjectMocks
    private VisitController visitController;

    @Mock
    private PetService petService;

    @Mock
    private VisitService visitService;

    private MockMvc mockMvc;

    private Pet pet;
    private Visit visit;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
        pet = Pet.builder().id(1L).build();
        visit = Visit.builder().id(1L).build();

        when(petService.findById(anyLong())).thenReturn(pet);
    }

    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get(OWNERS_1_PETS_1_VISITS_NEW))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("visit"))
                .andExpect(view().name(PETS_CREATE_OR_UPDATE_VISIT_FORM));

        verifyNoInteractions(visitService);
    }

    @Test
    void processCreationForm() throws Exception {
        when(visitService.save(any())).thenReturn(visit);

        mockMvc.perform(post(OWNERS_1_PETS_1_VISITS_NEW))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_OWNERS_1));

        verify(visitService, times(1)).save(any());
    }

    @Test
    void initUpdateForm() throws Exception {
        when(visitService.findById(anyLong())).thenReturn(visit);

        mockMvc.perform(get(OWNERS_1_PETS_1_VISITS_1_EDIT))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("visit"))
                .andExpect(view().name(PETS_CREATE_OR_UPDATE_VISIT_FORM));

        verify(visitService).findById(anyLong());
    }

    @Test
    void processUpdateForm() throws Exception {
        when(visitService.save(any())).thenReturn(visit);

        mockMvc.perform(post(OWNERS_1_PETS_1_VISITS_1_EDIT))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_OWNERS_1));

        verify(visitService, times(1)).save(any());
    }
}