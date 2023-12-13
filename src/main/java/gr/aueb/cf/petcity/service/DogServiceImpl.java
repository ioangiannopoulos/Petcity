package gr.aueb.cf.petcity.service;

import gr.aueb.cf.petcity.model.Dog;
import gr.aueb.cf.petcity.model.PetOwner;
import gr.aueb.cf.petcity.repository.DogRepository;
import gr.aueb.cf.petcity.service.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DogServiceImpl implements DogService {

    private final DogRepository dogRepository;
    private final PetOwnerService petOwnerService;

    @Autowired
    public DogServiceImpl(DogRepository dogRepository, PetOwnerService petOwnerService) {
        this.dogRepository = dogRepository;
        this.petOwnerService = petOwnerService;
    }

    /**
     * Creates a new Dog for a specific Owner.
     *
     * @param ownerId The ID of the Owner who owns the dog.
     * @param dog     The Dog to be created.
     * @return The created Dog.
     * @throws Exception If an error occurs while creating a new dog.
     */
    @Override
    public Dog createDog(Long ownerId, Dog dog) throws Exception {
        try {
            if (dog == null) {
                throw new IllegalArgumentException("Dog cannot be null");
            }
            if (ownerId == null) {
                throw new IllegalArgumentException("Owner ID cannot be null");
            }
            PetOwner owner = petOwnerService.getPetOwnerById(ownerId);
            dog.setOwner(owner);
            Dog savedDog = dogRepository.save(dog);

            if (savedDog.getId() == null) {
                throw new Exception("Failed to create Dog");
            }
            return savedDog;
        } catch (IllegalArgumentException e) {
            log.info("Invalid input provided!", e);
            throw e;
        } catch (EntityNotFoundException e) {
            log.info("PetOwner not found with ID: " + ownerId, e);
            throw e;
        } catch (Exception e) {
            log.info("Exception error in creating a Dog", e);
            throw new Exception("Exception error in creating a Dog", e);
        }
    }

    /**
     * Retrieves a Dog by its ID.
     *
     * @param dogId The ID of the Dog to retrieve.
     * @return The Dog matching the provided ID.
     * @throws EntityNotFoundException If the Dog with the given ID is not found.
     */
    @Override
    public Dog getDogById(Long dogId) throws EntityNotFoundException {
        try {
            Optional<Dog> optionalDog = dogRepository.findById(dogId);

            if (optionalDog.isPresent()) {
                return optionalDog.get();
            } else {
                throw new EntityNotFoundException(Dog.class, dogId);
            }
        } catch (EntityNotFoundException e) {
            log.info("Dog not found with ID: " + dogId);
            throw e;
        }
    }

    /**
     * Retrieves all Dogs owned by a specific Owner.
     *
     * @param ownerId The ID of the Owner whose Dogs to retrieve.
     * @return A list of Dogs owned by the specified Owner.
     * @throws EntityNotFoundException If no Dogs are found for the given Owner ID.
     */
    @Override
    public List<Dog> getAllDogsByOwnerId(Long ownerId) throws EntityNotFoundException {
        try {
            PetOwner owner = petOwnerService.getPetOwnerById(ownerId);
            if (owner.getDogs().isEmpty()) {
                throw new EntityNotFoundException(Dog.class, 0L);
            }
            return owner.getDogs();
        } catch (EntityNotFoundException e) {
            log.info("PetOwner not found with ID: " + ownerId);
            throw e;
        }
    }

    /**
     * Updates details of an existing Dog.
     *
     * @param dogId       The ID of the Dog to be updated.
     * @param dogDetails  The Dog with updated details.
     * @return The updated Dog.
     * @throws EntityNotFoundException If the Dog with the given ID is not found.
     */
    @Override
    public Dog updateDog(Long dogId, Dog dogDetails) throws EntityNotFoundException {
        Dog dog;
        Dog updatedDog;
        try {
            dog = dogRepository.getById(dogId);
            if (dog == null) throw new EntityNotFoundException(Dog.class, dogId);
            dog.setName(dogDetails.getName());
            dog.setAge(dogDetails.getAge());
            updatedDog = dogRepository.save(dog);
        } catch (EntityNotFoundException e) {
            log.info("Update exception error");
            throw e;
        }
        return updatedDog;
    }

/**
 * Deletes a Dog by its ID.
 *
 * @param dogId The ID of the Dog to be deleted.
 * @throws EntityNotFoundException If the Dog with the given ID is not found.
 */
    @Override
    public void deleteDog(Long dogId) throws EntityNotFoundException {
        Optional<Dog> dog;
        try {
            dog = dogRepository.findById(dogId);
            if (dog.isEmpty()) throw new EntityNotFoundException(Dog.class, dogId);
            dogRepository.deleteById(dogId);
        } catch (EntityNotFoundException e) {
            log.info("Delete exception error");
            throw e;
        }
    }
}
