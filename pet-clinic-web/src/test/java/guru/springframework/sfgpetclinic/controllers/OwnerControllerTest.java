package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @InjectMocks
    private OwnerController ownerController;

    @Mock
    private OwnerService ownerService;

    private MockMvc mockMvc;

    private Set<Owner> owners;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
        owners = new HashSet<>();
        owners.add(Owner.builder().id(1L).lastName("firstLastName").build());
        owners.add(Owner.builder().id(2L).lastName("secondLastName").build());
    }

//    @Test
//    void listOwners() throws Exception {
//        when(ownerService.findAll()).thenReturn(owners);
//
//        mockMvc.perform(get("/owners"))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("owners"))
//                .andExpect(model().attribute("owners", owners))
//                .andExpect(model().attribute("owners", hasSize(2)))
//                .andExpect(view().name("owners/ownersList"));
//
//        verify(ownerService).findAll();
//    }

    @Test
    void testFindOwnersForm() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void processFindFormReturnMany() throws Exception {
        when(ownerService.findByLastNameContainingIgnoreCase(anyString()))
                .thenReturn(new ArrayList<>(owners));

        mockMvc.perform(get("/owners")
                        .param("lastName", "lastName "))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("owners", hasSize(2)));
    }

    @Test
    void processFindFormEmptyReturnMany() throws Exception {
        mockMvc.perform(get("/owners")
                .param("lastName", ""))
                .andExpect(model().attributeHasErrors())
                .andExpect(model().attributeDoesNotExist("owners"))
                .andExpect(view().name("owners/findOwners"));
        verifyNoInteractions(ownerService);
    }

    @Test
    void processFindFormReturnOne() throws Exception {
        when(ownerService.findByLastNameContainingIgnoreCase(anyString()))
                .thenReturn(List.of(Owner.builder().id(1L).lastName("lastName").build()));

        mockMvc.perform(get("/owners")
                        .param("lastName", "first"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));
    }

    @Test
    void showOwner() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owners.iterator().next());

        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
//                .andExpect(model().attribute("owner", hasProperty("id", is(1L))))
                .andExpect(model().attribute("owner", notNullValue()))
                .andExpect(view().name("owners/ownerDetails"));

        verify(ownerService, times(1)).findById(anyLong());
    }


}