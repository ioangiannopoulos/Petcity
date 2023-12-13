package gr.aueb.cf.petcity.service;

import gr.aueb.cf.petcity.model.Dog;
import gr.aueb.cf.petcity.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface DogService {
    Dog createDog(Long ownerId, Dog dog) throws Exception;
    Dog getDogById(Long dogId) throws EntityNotFoundException;
    List<Dog> getAllDogsByOwnerId(Long ownerId) throws EntityNotFoundException;
    Dog updateDog(Long dogId, Dog dogDetails) throws EntityNotFoundException;
    void deleteDog(Long dogId) throws EntityNotFoundException;
}
