package gr.aueb.cf.petcity.service;

import gr.aueb.cf.petcity.model.PetOwner;
import gr.aueb.cf.petcity.repository.PetOwnerRepository;
import gr.aueb.cf.petcity.service.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PetOwnerServiceImpl implements PetOwnerService {

    private final PetOwnerRepository petOwnerRepository;

    @Autowired
    public PetOwnerServiceImpl(PetOwnerRepository petOwnerRepository) {
        this.petOwnerRepository = petOwnerRepository;
    }

    /**
     * Creates a new PetOwner.
     *
     * @param petOwner The PetOwner to be created.
     * @return The created PetOwner.
     * @throws Exception If an error occurs while creating the PetOwner.
     */
    @Override
    public PetOwner createPetOwner(PetOwner petOwner) throws Exception {
        try {
            if (petOwner == null) {
                throw new IllegalArgumentException("PetOwner cannot be null");
            }
            PetOwner savedPetOwner = petOwnerRepository.save(petOwner);

            if (savedPetOwner.getId() == null) {
                throw new Exception("Failed to create PetOwner");
            }
            return savedPetOwner;
        } catch (IllegalArgumentException e) {
            log.info("Invalid PetOwner provided");
            throw new Exception("Invalid PetOwner provided", e);
        } catch (Exception e) {
            log.info("Exception error in creating a PetOwner");
            throw new Exception("Exception error in creating a PetOwner", e);
        }
    }

    /**
     * Retrieves a PetOwner by its ID.
     *
     * @param ownerId The ID of the PetOwner to retrieve.
     * @return The PetOwner matching the provided ID.
     * @throws EntityNotFoundException If the PetOwner with the given ID is not found.
     */
    @Override
    public PetOwner getPetOwnerById(Long ownerId) throws EntityNotFoundException {
        try {
            PetOwner petOwner = petOwnerRepository.getById(ownerId);
            if (petOwner == null) {
                throw new EntityNotFoundException(PetOwner.class, ownerId);
            }
            return petOwner;
        } catch (EntityNotFoundException e) {
            log.info("PetOwner not found with ID: " + ownerId);
            throw e;
        } catch (Exception e) {
            log.info("Unexpected error in getting petOwner by ID: " + ownerId);
            throw new EntityNotFoundException(PetOwner.class, ownerId);
        }
    }

    /**
     * Retrieves all PetOwners.
     *
     * @return A list of all existing PetOwners.
     * @throws EntityNotFoundException If no PetOwners are found.
     */
    @Override
    public List<PetOwner> getAllPetOwners() throws EntityNotFoundException {
        List<PetOwner> petOwners;
        try {
             petOwners = petOwnerRepository.findAll();
            if (petOwners.isEmpty()) {
                throw new EntityNotFoundException(PetOwner.class, 0L);
            }
        } catch (EntityNotFoundException e) {
            log.info("No PetOwners found");
            throw e;
        }
        return petOwners;
    }

    /**
     * Updates an existing PetOwner.
     *
     * @param ownerId        The ID of the PetOwner to be updated.
     * @param petOwnerDetails The PetOwner with updated details.
     * @return The updated PetOwner.
     * @throws EntityNotFoundException If the PetOwner with the given ID is not found.
     */
    @Override
    public PetOwner updatePetOwner(Long ownerId, PetOwner petOwnerDetails)
            throws EntityNotFoundException {
        PetOwner petOwner;
        PetOwner updatedPetOwner;
        try {
            petOwner = petOwnerRepository.getById(ownerId);
            if (petOwner == null) throw new EntityNotFoundException(PetOwner.class, ownerId);
            petOwner.setFirstname(petOwnerDetails.getFirstname());
            petOwner.setLastname(petOwnerDetails.getLastname());
            petOwner.setPhoneNumber(petOwnerDetails.getPhoneNumber());
            petOwner.setEmail(petOwnerDetails.getEmail());
            petOwner.setDogs(petOwnerDetails.getDogs());
            updatedPetOwner = petOwnerRepository.save(petOwner);
        } catch (EntityNotFoundException e) {
            log.info("Update exception error");
            throw e;
        }
        return updatedPetOwner;
    }

    /**
     * Deletes a PetOwner by its ID.
     *
     * @param ownerId The ID of the PetOwner to be deleted.
     * @throws EntityNotFoundException If the PetOwner with the given ID is not found.
     */
    @Override
    public void deletePetOwner(Long ownerId) throws EntityNotFoundException {
        try {
            Optional<PetOwner> optionalPetOwner = petOwnerRepository.findById(ownerId);
            if (optionalPetOwner.isPresent()) {
                petOwnerRepository.deleteById(ownerId);
            } else {
                throw new EntityNotFoundException(PetOwner.class, ownerId);
            }
        } catch (EntityNotFoundException e) {
            log.info("Delete exception error");
            throw e;
        }
    }
}
