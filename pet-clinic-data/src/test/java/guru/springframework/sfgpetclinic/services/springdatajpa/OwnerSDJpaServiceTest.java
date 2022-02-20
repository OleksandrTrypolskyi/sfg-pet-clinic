package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {
    private static final String LAST_NAME = "Smith";
    public static final long ID = 1L;

    @InjectMocks
    private OwnerSDJpaService service;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private PetRepository petRepository;

    private Owner owner;



    @BeforeEach
    void setUp() {
        owner = Owner.builder().id(ID)
                .firstName("Alex").lastName(LAST_NAME).address("Baker Str.").telephone("123").build();
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(anyString())).thenReturn(owner);
        Owner result = service.findByLastName(LAST_NAME);
        assertThat(result).isNotNull();
        assertThat(result.getLastName()).isEqualTo(LAST_NAME);
    }

    @Test
    void findAll() {
        final HashSet<Owner> owners = new HashSet<>();
        owners.add(owner);
        owners.add(Owner.builder().firstName("some_name").build());
        when(ownerRepository.findAll()).thenReturn(owners);
        assertThat(service.findAll().size()).isEqualTo(2);
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));
        when(petRepository.findPetsByOwner_Id(anyLong())).thenReturn(new HashSet<>());
        assertThat(service.findById(ID)).isNotNull();
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThat(service.findById(ID)).isNull();
    }

    @Test
    void save() {
        when(ownerRepository.save(any())).thenReturn(owner);
        assertThat(service.save(Owner.builder().build())).isNotNull();
    }

    @Test
    void delete() {
        service.delete(owner);
        verify(ownerRepository).delete(any());
    }

    @Test
    void deleteById() {
        service.deleteById(ID);
        verify(ownerRepository).deleteById(anyLong());
    }
}