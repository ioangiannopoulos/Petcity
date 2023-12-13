package gr.aueb.cf.petcity.controller;

import gr.aueb.cf.petcity.dto.RegisterUserDTO;
import gr.aueb.cf.petcity.model.User;
import gr.aueb.cf.petcity.service.SecurityService;
import gr.aueb.cf.petcity.service.UserService;
import gr.aueb.cf.petcity.validator.UserValidator;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegisterController {

	private final UserValidator userValidator;

	private final UserService userService;

	private final SecurityService securityService;

	@Autowired
	public RegisterController(UserValidator userValidator, UserService userService,
							  SecurityService securityService) {
		this.userValidator = userValidator;
		this.userService = userService;
		this.securityService = securityService;
	}

	/**
	 * Displays the registration form.
	 *
	 * @param model The user form to be used for the view.
	 * @return The view name for the registration form.
	 */
	@GetMapping("/register")
	public String login(Model model) {
		model.addAttribute("userForm", new RegisterUserDTO());
		return "register";
	}

	/**
	 * Handles user registration.
	 *
	 * @param userDTO        The RegisterUserDTO containing user registration data.
	 * @param bindingResult  BindingResult object for validation errors.
	 * @return The view or redirect depending on the registration result.
	 */
	@PostMapping("/register")
	public String registration(@Valid @ModelAttribute("userForm") RegisterUserDTO userDTO,
			BindingResult bindingResult) {
		userValidator.validate(userDTO, bindingResult);

		if (bindingResult.hasErrors()) {
			return "register";
		}
		User createdUser = userService.registerUser(userDTO);

		// login with the newly created account and redirect to search page
		securityService.autoLogin(createdUser);

		return "redirect:/home";
	}
}