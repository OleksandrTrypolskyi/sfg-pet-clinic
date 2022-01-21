package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class OwnerServiceMapTest {

    private OwnerServiceMap ownerServiceMap;

    @BeforeEach
    void setUp() {
        ownerServiceMap = new OwnerServiceMap(new PetTypeServiceMap(), new PetServiceMap());

        ownerServiceMap.save(Owner.builder().firstName("first_name").build());
    }

    @Test
    void findAll() {
        assertThat(ownerServiceMap.findAll().size()).isEqualTo(1);
    }

    @Test
    void findById() {
        assertThat(ownerServiceMap.findById(1L).getId()).isEqualTo(1L);
    }

    @Test
    void save() {
        final Owner savedOwner = ownerServiceMap
                .save(Owner.builder().firstName("another_first_name").build());
        assertThat(savedOwner.getId()).isEqualTo(2L);
    }

    @Test
    void delete() {
        ownerServiceMap.delete(ownerServiceMap.findById(1L));
        assertThat(ownerServiceMap.findAll().size()).isZero();
    }

    @Test
    void deleteById() {
        ownerServiceMap.deleteById(1L);
        assertThat(ownerServiceMap.findAll().size()).isZero();
    }

    @Test
    void findByLastName() {
        final Owner ownerWithLastName = Owner.builder().lastName("last_name").build();
        ownerServiceMap.save(ownerWithLastName);
        assertThat(ownerServiceMap.findByLastName("last_name").getId())
                .isEqualTo(ownerWithLastName.getId());
    }

    @Test
    void findByLastNameNotFound() {
        assertThat(ownerServiceMap.findByLastName("foo")).isNull();
    }
}