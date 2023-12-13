package gr.aueb.cf.petcity.service;



import gr.aueb.cf.petcity.dto.RegisterUserDTO;
import gr.aueb.cf.petcity.model.User;
import gr.aueb.cf.petcity.repository.UserRepository;
import gr.aueb.cf.petcity.service.exceptions.UserNotFoundException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl provides implementations for UserService operations,
 * such as user registration and retrieval.
 */
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Registers a new user based on the provided user details.
	 *
	 * @param userToRegister The user details to register.
	 * @return The registered User.
	 */
	@Transactional
	@Override
	public User registerUser(RegisterUserDTO userToRegister) {
		return userRepository.save(convertToUser(userToRegister));
	}

	/**
	 * Retrieves a user by their username.
	 *
	 * @param username The username of the user to retrieve.
	 * @return The User object corresponding to the provided username.
	 * @throws UserNotFoundException If the user with the given username is not found.
	 */
	@Override
	public User getUserByUsername(String username) throws UserNotFoundException {
		User user = userRepository.getUserByEmail(username);
		if (user == null) {
			throw new UserNotFoundException("User " + username + " not found.");
		}
		return user;
	}

	/**
	 * Checks if the given email is already taken/exists in the database.
	 *
	 * @param email The email address to check.
	 * @return True if the email is already taken; otherwise, false.
	 */
	@Override
	public boolean isEmailTaken(String email) {
		return userRepository.emailExists(email);
	}

	/**
	 * Converts a RegisterUserDTO to a User.
	 *
	 * @param userDTO The RegisterUserDTO to convert.
	 * @return The corresponding User created from the RegisterUserDTO.
	 */
	private static User convertToUser(RegisterUserDTO userDTO) {
		return new User(userDTO.getEmail(), userDTO.getPassword());
	}

}
