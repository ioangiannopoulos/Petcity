package gr.aueb.cf.petcity.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Custom authentication success handler responsible for handling successful user authentication.
 * Extends Spring Security's SimpleUrlAuthenticationSuccessHandler.
 */
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    public static String REDIRECT_URL = "REDIRECT_URL";


    /**
     * Overrides the default behavior on successful authentication.
     *
     * @param request        The HttpServletRequest request.
     * @param response       The HttpServletResponse response.
     * @param authentication The authenticated user's authentication .
     * @throws IOException      If an input or output exception occurs during handling.
     * @throws ServletException If a servlet-related exception occurs during handling.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        Object redirectURLObject = request.getSession().getAttribute("REDIRECT_URL");

        if (redirectURLObject != null) {
            setDefaultTargetUrl(redirectURLObject.toString());
        } else {
            setDefaultTargetUrl("/home");
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
