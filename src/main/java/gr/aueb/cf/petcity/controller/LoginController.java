package gr.aueb.cf.petcity.controller;


import gr.aueb.cf.petcity.authentication.CustomAuthenticationSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LoginController {

    /**
     * Displays the login page.
     *
     * @param principal The Principal representing the currently logged-in user.
     * @param request   The HttpServletRequest for the HTTP request.
     * @return The view name for the login page or redirect to the home page if already logged in.
     */
    @GetMapping(path = "/login")
    public String login(Principal principal, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        request.getSession().setAttribute(CustomAuthenticationSuccessHandler.REDIRECT_URL, referer);

        return principal == null ? "login" : "redirect:/home";
    }

    /**
     * Handles the root path ("/").
     *
     * @param principal The Principal representing the currently logged-in user.
     * @return The view name for the login page or redirect to the home page if already logged in.
     */
    @GetMapping(path = "/")
    public String root(Principal principal) {
        return principal == null ? "login" : "redirect:/home";
    }
}
