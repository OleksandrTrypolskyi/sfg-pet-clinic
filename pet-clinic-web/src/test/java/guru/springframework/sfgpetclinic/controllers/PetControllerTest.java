package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    public static final String PETS_CREATE_OR_UPDATE_PET_FORM = "pets/createOrUpdatePetForm";
    public static final String REDIRECT_OWNERS_1 = "redirect:/owners/1";
    public static final String OWNERS_1_PETS_NEW = "/owners/1/pets/new";
    public static final String OWNERS_1_PETS_1_EDIT = "/owners/1/pets/1/edit";
    @InjectMocks
    private PetController petController;

    @Mock
    private OwnerService ownerService;

    @Mock
    private PetService petService;

    @Mock
    private PetTypeService petTypeService;

    private MockMvc mockMvc;
    private Owner owner;
    private Set<PetType> petTypes;
    private Pet pet;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();

        owner = Owner.builder().id(1L).pets(new HashSet<>()).build();
        petTypes = new HashSet<>();
        petTypes.add(PetType.builder().id(1L).name("Cat").build());
        petTypes.add(PetType.builder().id(2L).name("Dog").build());

        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);
    }

    @AfterEach
    void tearDown() {
        verify(ownerService, times(1)).findById(anyLong());
        verify(petTypeService, times(1)).findAll();
    }

    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get(OWNERS_1_PETS_NEW))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name(PETS_CREATE_OR_UPDATE_PET_FORM));
    }

    @Test
    void processCreationForm() throws Exception {
        initPet();

        when(petService.save(any())).thenReturn(pet);

        mockMvc.perform(post(OWNERS_1_PETS_NEW))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_OWNERS_1));

        verify(petService, times(1)).save(any());
    }

    private void initPet() {
        pet = new Pet();
        pet.setId(1L);
        pet.setOwner(owner);
        owner.getPets().add(pet);
    }

    @Test
    void initUpdateForm() throws Exception {
        initPet();
        when(petService.findById(anyLong())).thenReturn(pet);

        mockMvc.perform(get(OWNERS_1_PETS_1_EDIT))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name(PETS_CREATE_OR_UPDATE_PET_FORM));

        verify(petService, times(1)).findById(anyLong());
    }

    @Test
    void processUpdateForm() throws Exception {
        initPet();
        when(petService.save(any())).thenReturn(pet);

        mockMvc.perform(post(OWNERS_1_PETS_1_EDIT))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_OWNERS_1));

        verify(petService, times(1)).save(any());
    }
}