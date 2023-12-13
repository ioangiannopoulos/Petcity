package gr.aueb.cf.petcity.controller;

import gr.aueb.cf.petcity.model.Dog;
import gr.aueb.cf.petcity.service.DogService;
import gr.aueb.cf.petcity.service.PetOwnerService;
import gr.aueb.cf.petcity.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping
public class DogController {

    private final DogService dogService;

    @Autowired
    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    /**
     * Displays the form to create a new Dog for a specific PetOwner.
     *
     * @param model    The data of the object that will be sent to the view.
     * @param ownerId  The ID of the PetOwner for whom the dog is created.
     * @return The view name for the create-dog form.
     */
    @GetMapping("/owners/{ownerId}/dogs/create")
    public String showCreateDogForm(Model model, @PathVariable Long ownerId) {
        model.addAttribute("dog", new Dog());
        model.addAttribute("ownerId", ownerId);
        return "create-dog";
    }

    /**
     * Handles the creation of a new Dog for a specific PetOwner.
     *
     * @param dog            The Dog to be created.
     * @param ownerId        The ID of the PetOwner for whom the dog is created.
     * @param model          The data of the object that will be sent to the view.
     * @param bindingResult  BindingResult object for validation errors.
     * @return The view name based on the creation result.
     */
    @PostMapping("/owners/{ownerId}/dogs/create")
    public String createDog(@Valid @ModelAttribute("dog")
    Dog dog, @PathVariable Long ownerId,Model model, BindingResult bindingResult ) {
        try {
            dogService.createDog(ownerId, dog);
            List<Dog> dogs = dogService.getAllDogsByOwnerId(ownerId);
            model.addAttribute("dogs", dogs);
            return "dog-details";
        } catch (Exception e) {
            return "error";
        }
    }

    /**
     * Retrieves details of a Dog by ID.
     *
     * @param dogId  The ID of the Dog to retrieve.
     * @param model  The data of the object that will be sent to the view.
     * @return The view name for the dog-details page or error page if not found.
     */
    @GetMapping("/dogs/{dogId}")
    public String getDogById(@PathVariable("dogId") Long dogId, Model model) {
        try {
            Dog dog = dogService.getDogById(dogId);
            model.addAttribute("dog", dog);
            return "dog-details";
        } catch (EntityNotFoundException e) {
            return "error";
        }
    }

    /**
     * Retrieves all Dogs belonging to a specific PetOwner.
     *
     * @param ownerId The ID of the PetOwner.
     * @param model   The data of the object that will be sent to the view.
     * @return The view name for the owner-dogs page or error page if not found.
     */
    @GetMapping("/owners/{ownerId}/dogs")
    public String getDogsByOwnerId(@PathVariable Long ownerId, Model model) {
        try {
            List<Dog> dogs = dogService.getAllDogsByOwnerId(ownerId);
            model.addAttribute("dogs", dogs);
            return "owner-dogs";
        } catch (EntityNotFoundException e) {
            return "error";
        }
    }

    /**
     * Deletes a Dog by its ID belonging to a specific PetOwner.
     *
     * @param dogId   The ID of the Dog to be deleted.
     * @param ownerId The ID of the PetOwner who owns the dog.
     * @param model   The data of the object that will be sent to the view.
     * @return The view name for the owner-dogs page or error page if not found.
     */
    @GetMapping("/owners/{ownerId}/dogs/delete/{dogId}")
    public String deleteByDogId(@PathVariable Long dogId,
                                @PathVariable Long ownerId, Model model) {
        if (dogId == null) {
            model.addAttribute("errorMessage", "Invalid ID provided");
            return "error";
        }
        try {
            dogService.deleteDog(dogId);
            List<Dog> dogs = dogService.getAllDogsByOwnerId(ownerId);
            model.addAttribute("dogs", dogs);
            return "owner-dogs";
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "Dog not found");
            return "error";
        }
    }

}

