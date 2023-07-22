package com.example.w40k.controllers;

import com.example.w40k.models.Role;
import com.example.w40k.models.Ships;
import com.example.w40k.models.User;
import com.example.w40k.services.EmailSenderService;
import com.example.w40k.services.GenerateActivationCode;
import com.example.w40k.services.ShipService;
import com.example.w40k.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    private final ShipService shipService;
    private final UserService userService;
    private final EmailSenderService emailSenderService;

    private boolean showDifficultyOptions = true;

    @Autowired
    public HomeController(ShipService shipService, UserService userService,EmailSenderService emailSenderService) {
        this.shipService = shipService;
        this.userService = userService;
        this.emailSenderService = emailSenderService;
    }
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String registerForm() {
        return "register";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/index")
    public String index(Model model) {
        List<Ships> ships = shipService.findAll();
        model.addAttribute("ships", ships);
        model.addAttribute("showDifficultyOptions", showDifficultyOptions);
        return "index";
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           RedirectAttributes redirectAttributes) {

        User user = new User(username, email, password);

        String activationCode = GenerateActivationCode.generate(48);
        user.setActivationCode(activationCode);
        user.setIsActive(false);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userService.save(user);
/*
        // Send activation email
        emailSenderService.sendEmail(savedUser.getEmail(), activationCode);
*/
        // Set a flash attribute to indicate successful registration
        redirectAttributes.addFlashAttribute("registrationSuccess", true);

        System.out.println("Activation Code: " + activationCode);
        // Redirect to the login page
        return "redirect:/login";
    }
    @PostMapping("/activate")
    public String activateUser(@RequestParam("activationCode") String activationCode,
                               RedirectAttributes redirectAttributes, ModelMap model) {
        // Retrieve the username from the User object associated with the activation code
        Optional<User> userOptional = userService.findByActivationCode(activationCode);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String username = user.getUsername();

            // Call the activate method in the userService
            ResponseEntity<?> responseEntity = userService.activate(username, activationCode);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                // Set flash attribute indicating successful activation
                redirectAttributes.addFlashAttribute("activationSuccess", true);
                model.addAttribute("message", "User activated successfully! You can now log in.");
            } else {
                // Set flash attribute indicating activation error
                redirectAttributes.addFlashAttribute("activationError", true);
                model.addAttribute("message", "Invalid activation code.");
            }
        } else {
            // Set flash attribute indicating activation error
            redirectAttributes.addFlashAttribute("activationError", true);
            model.addAttribute("message", "Invalid activation code.");
        }

        return "redirect:/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User userForm, ModelMap model) {
        // Check if the provided username, email, and password match a record in the database
        Optional<User> foundUser = userService.findByUsernameOrEmail(userForm.getUsername(), userForm.getEmail());

        if (foundUser.isPresent() && passwordEncoder.matches(userForm.getPassword(), foundUser.get().getPassword())) {
            User user = foundUser.get();
            if (user.getIsActive()) { // Assuming you have an 'isActive()' method in your User class
                // Check if the user has the role "USER"
                if (user.getRole() == Role.USER) {
                    // Create an authentication token with the user's details
                    Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);

                    // Set the authentication object in the security context
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    return "redirect:/index";
                } else {
                    model.addAttribute("message", "Access denied. User does not have the required role.");
                    return "login";
                }
            } else {
                model.addAttribute("message", "User not activated");
                return "login";
            }
        } else {
            model.addAttribute("message", "User not found");
            return "login";
        }
    }
    @GetMapping("/FirstGame")
    public String firstGame() {
        return "FirstGame";
    }
    @GetMapping("/SecondGame")
    public String secondGame() {
        return "SecondGame";
    }
    @GetMapping("/readme")
    public String readme() {
        return "readme";
    }
}