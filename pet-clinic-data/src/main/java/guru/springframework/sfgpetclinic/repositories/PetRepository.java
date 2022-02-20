package guru.springframework.sfgpetclinic.repositories;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface PetRepository extends CrudRepository<Pet, Long> {
    Set<Pet> findPetsByOwner(Owner owner);
    Set<Pet> findPetsByOwner_Id(Long ownerId);
}
