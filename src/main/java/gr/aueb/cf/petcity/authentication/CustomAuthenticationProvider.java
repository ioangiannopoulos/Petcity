package gr.aueb.cf.petcity.authentication;


import gr.aueb.cf.petcity.repository.UserRepository;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Locale;

/**
 * Custom authentication provider responsible for authenticating users.
 * Implements Spring Security's AuthenticationProvider interface.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Autowired
    public CustomAuthenticationProvider(UserRepository userRepository, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    private MessageSourceAccessor accessor;

    /**
     * Initializes the MessageSourceAccessor to retrieve messages.
     */
    @PostConstruct
    private void init() {
        accessor = new MessageSourceAccessor(messageSource, Locale.getDefault());
    }

    /**
     * Authenticates a user based on provided credentials.
     *
     * @param authentication Authentication representing the user's authentication request.
     * @return Authentication if authentication is successful.
     * @throws AuthenticationException If authentication fails.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (!userRepository.isUserValid(username, password)) {
            throw new BadCredentialsException(accessor.getMessage("badCredentials"));
        }
        return new UsernamePasswordAuthenticationToken
                (username, password, Collections.<GrantedAuthority>emptyList());
    }

    /**
     * Indicates if the authentication provider supports the provided Authentication class.
     *
     * @param authentication The class to be checked for support.
     * @return True if the provider supports the class, false otherwise.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
