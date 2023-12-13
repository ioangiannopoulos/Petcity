package gr.aueb.cf.petcity.service;



import gr.aueb.cf.petcity.dto.RegisterUserDTO;
import gr.aueb.cf.petcity.model.User;
import gr.aueb.cf.petcity.service.exceptions.UserNotFoundException;


public interface UserService {
	public User registerUser(RegisterUserDTO userToRegister);
	public User getUserByUsername(String username) throws UserNotFoundException;
	public boolean isEmailTaken(String email);
}
