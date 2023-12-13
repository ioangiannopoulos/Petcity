package gr.aueb.cf.petcity.validator;

import gr.aueb.cf.petcity.dto.RegisterUserDTO;
import gr.aueb.cf.petcity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Objects;

/**
 * UserValidator is responsible for validating user input during registration.
 * It implements the Spring Validator interface and performs validation on
 * RegisterUserDTO objects.
 */
@Component
public class UserValidator implements Validator {

	private final UserService userService;

	@Autowired
	public UserValidator(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Determines if this validator supports the given class for validation.
	 *
	 * @param aClass The Class to be checked for validation support.
	 * @return True if this validator supports the class; otherwise, false.
	 */
	@Override
	public boolean supports(Class<?> aClass) {
		return RegisterUserDTO.class.equals(aClass);
	}

	/**
	 * Validates the RegisterUserDTO for registration.
	 *
	 * @param target The object to be validated.
	 * @param errors Errors object to store validation errors (if any).
	 */
	@Override
	public void validate(Object target, Errors errors) {
		RegisterUserDTO userToRegister = (RegisterUserDTO) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "empty");
		if (userToRegister.getEmail().length() < 3 || userToRegister.getEmail().length() > 32) {
			errors.rejectValue("email", "size");
		}
		if (userService.isEmailTaken(userToRegister.getEmail())) {
			errors.rejectValue("email", "duplicate");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "empty");
		if (userToRegister.getPassword().length() < 3 || userToRegister.getPassword().length() > 32) {
			errors.rejectValue("password", "size");
		}

		if (!Objects.equals(userToRegister.getPassword(), userToRegister.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "confirmation");
		}
	}
}
