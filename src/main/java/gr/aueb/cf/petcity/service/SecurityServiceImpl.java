package gr.aueb.cf.petcity.service;

import gr.aueb.cf.petcity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * SecurityServiceImpl provides implementations for SecurityService operations,
 * such as automatic user login.
 */
@Service
public class SecurityServiceImpl implements SecurityService {

	private final AuthenticationManager authenticationManager;

	@Autowired
	public SecurityServiceImpl(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/**
	 * Automatically logs in the user by authenticating the provided User.
	 *
	 * @param user The User to be automatically logged in.
	 */
	@Override
	public void autoLogin(User user) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				user.getEmail(), user.getPassword(), Collections.<GrantedAuthority>emptyList());

		authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		if (usernamePasswordAuthenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}
	}
}