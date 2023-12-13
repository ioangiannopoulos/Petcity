package gr.aueb.cf.petcity.controller;

import gr.aueb.cf.petcity.model.PetOwner;
import gr.aueb.cf.petcity.service.PetOwnerService;
import gr.aueb.cf.petcity.service.exceptions.EntityNotFoundException;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/owners")
public class PetOwnerController {
    private final PetOwnerService petOwnerService;

    @Autowired
    public PetOwnerController(PetOwnerService petOwnerService) {
        this.petOwnerService = petOwnerService;
    }

    /**
     * Displays the form to create a new PetOwner.
     *
     * @param model The data of the object that will be sent to the view.
     * @return The view name for the insert-owner form.
     */
    @GetMapping("/insert")
    public String showCreateForm(Model model) {
        model.addAttribute("petOwner", new PetOwner());
        return "insert-owner";
    }

    /**
     * Handles the insertion of a new PetOwner.
     *
     * @param petOwner       The PetOwner to be inserted.
     * @param bindingResult  BindingResult object for validation errors.
     * @return The view name or redirect URL based on the insertion result.
     */
    @PostMapping("/insert")
    public String insertPetOwner(@Valid @ModelAttribute("petOwner")
                                     PetOwner petOwner, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "insert-owner";
        }
        try {
            petOwnerService.createPetOwner(petOwner);
            return "redirect:/owners/list";
        } catch (Exception e) {
            return "error";
        }
    }

    /**
     * Retrieves details of a PetOwner by ID.
     *
     * @param ownerId The ID of the PetOwner to retrieve.
     * @param model   The data of the object that will be sent to the view.
     * @return The view name for the owner-details page or error page if not found.
     */
    @GetMapping("/{ownerId}")
    public String getPetOwnerById(@PathVariable("ownerId") Long ownerId, Model model) {
        try {
            PetOwner petOwner = petOwnerService.getPetOwnerById(ownerId);
            model.addAttribute("petOwner", petOwner);
            return "owner-details";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", "Owner not found");
            return "error";
        }
    }

    /**
     * Retrieves all PetOwners.
     *
     * @param model The data of the object that will be sent to the view.
     * @return The view name for the owners-list page or error page if not found.
     */
    @GetMapping("/list")
    public String getAllPetOwners(Model model) {
        try {
            List<PetOwner> petOwners = petOwnerService.getAllPetOwners();
            model.addAttribute("petOwners", petOwners);
        } catch (EntityNotFoundException e) {
            log.info("No pet owners found", e);
            model.addAttribute("errorMessage", "No pet owners found");
        }
        return "owners-list";
    }

    /**
     * Displays the form to update PetOwner details.
     *
     * @param ownerId The ID of the PetOwner to be updated.
     * @param model   The data of the object that will be sent to the view.
     * @return The view name for the update-owner form or error page if not found.
     * @throws EntityNotFoundException If the PetOwner with the given ID is not found.
     */
    @GetMapping("/update/{ownerId}")
    public String showUpdatePetOwnerForm(@PathVariable("ownerId") Long ownerId, Model model)
            throws EntityNotFoundException {
        PetOwner petOwnerToUpdate = petOwnerService.getPetOwnerById(ownerId);
        if (petOwnerToUpdate != null) {
            model.addAttribute("petOwnerToUpdate", petOwnerToUpdate);
            return "update-owner";
        } else {
            throw new EntityNotFoundException(PetOwner.class, ownerId);
        }
    }


    /**
     * Handles the update of PetOwner details.
     *
     * @param ownerId       The ID of the PetOwner to be updated.
     * @param petOwnerDetails The updated PetOwner details.
     * @param bindingResult  BindingResult object for validation errors.
     * @param model         The data of the object that will be sent to the view.
     * @return The view name or redirect URL based on the update result.
     * @throws EntityNotFoundException If the PetOwner with the given ID is not found.
     */
    @PostMapping("/update/{ownerId}")
    public String updatePetOwner(@PathVariable("ownerId") Long ownerId,
                                 @Valid PetOwner petOwnerDetails,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("petOwnerToUpdate", petOwnerDetails);
            return "update-owner";
        }

        try {
            PetOwner existingPetOwner = petOwnerService.getPetOwnerById(ownerId);
            if (existingPetOwner != null) {
                existingPetOwner.setFirstname(petOwnerDetails.getFirstname());
                existingPetOwner.setLastname(petOwnerDetails.getLastname());
                existingPetOwner.setPhoneNumber(petOwnerDetails.getPhoneNumber());
                existingPetOwner.setEmail(petOwnerDetails.getEmail());
                existingPetOwner.setDogs(petOwnerDetails.getDogs());

                PetOwner updatedPetOwner = petOwnerService.updatePetOwner(ownerId, existingPetOwner);
                model.addAttribute("updatedPetOwner", updatedPetOwner);
                return "redirect:/owners/list";
            } else {
                return "error";
            }
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "Pet owner not found");
            return "error";
        }
    }


    /**
     * Deletes a PetOwner by ID.
     *
     * @param ownerId The ID of the PetOwner to be deleted.
     * @param model   The data of the object that will be sent to the view.
     * @return The view name or redirect URL based on the deletion result.
     */
    @GetMapping("/delete/{ownerId}")
    public String deletePetOwner(@PathVariable("ownerId") Long ownerId, Model model) {
        if (ownerId == null || ownerId <= 0) {
            model.addAttribute("errorMessage", "Invalid ID provided");
            return "error";
        }
        try {
            petOwnerService.deletePetOwner(ownerId);
            return "redirect:/owners/list";
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "Pet owner not found");
            return "error";
        }
    }
}