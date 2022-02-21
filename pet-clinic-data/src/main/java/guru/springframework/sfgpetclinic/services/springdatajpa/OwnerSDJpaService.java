package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import guru.springframework.sfgpetclinic.services.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;

@Service
@Profile("springdatajpa")
@Slf4j
public class OwnerSDJpaService implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    private final PetTypeRepository petTypeRepository;

    public OwnerSDJpaService(OwnerRepository ownerRepository, PetRepository petRepository,
                             PetTypeRepository petTypeRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.petTypeRepository = petTypeRepository;
    }

    @Override
    public Owner findByLastName(String lastName) {
        return ownerRepository.findByLastName(lastName);
    }

    @Override
    public Set<Owner> findAll() {
        return stream(ownerRepository.findAll().spliterator(), false).collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public Owner findById(Long id) {
        final Owner owner = ownerRepository.findById(id).orElse(null);
//        final Set<Pet> pets = stream(petRepository.findAll().spliterator(), false)
//                .filter(pet -> pet.getOwner().getId().equals(owner.getId()))
//                .collect(Collectors.toSet());
//        owner.setPets(petReposiuptory.findPetsByOwner(owner));
        if(owner != null) {
            owner.setPets(petRepository.findPetsByOwner_Id(owner.getId()));
            log.info(owner.toString());
        }
        return owner;
    }

    @Override
    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public void delete(Owner owner) {
        ownerRepository.delete(owner);
    }

    @Override
    public void deleteById(Long id) {
        ownerRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Owner> findByLastNameLike(String lastName) {
        return ownerRepository.findByLastNameContainingIgnoreCase(lastName);
    }
}
