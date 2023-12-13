package gr.aueb.cf.petcity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Displays the home page.
     *
     * @return The view name for the home page.
     */
    @GetMapping("/home")
    public String home() {
        return "home";
    }
}