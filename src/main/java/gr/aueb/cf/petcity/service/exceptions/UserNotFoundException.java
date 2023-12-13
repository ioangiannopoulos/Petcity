package gr.aueb.cf.petcity.service.exceptions;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 12345L;

	public UserNotFoundException(String message) {
		super(message);
	}
	
}
