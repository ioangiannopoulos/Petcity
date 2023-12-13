package gr.aueb.cf.petcity.service;

import gr.aueb.cf.petcity.model.PetOwner;
import gr.aueb.cf.petcity.service.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface PetOwnerService {
    PetOwner createPetOwner(PetOwner petOwner) throws Exception;
    PetOwner getPetOwnerById(Long ownerId) throws EntityNotFoundException;
    List<PetOwner> getAllPetOwners() throws EntityNotFoundException;
    PetOwner updatePetOwner(Long ownerId, PetOwner petOwnerDetails) throws EntityNotFoundException;
    void deletePetOwner(Long ownerId) throws EntityNotFoundException;
}
