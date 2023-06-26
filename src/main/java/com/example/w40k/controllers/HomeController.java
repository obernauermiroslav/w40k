package com.example.w40k.controllers;

import com.example.w40k.models.Ships;
import com.example.w40k.models.User;
import com.example.w40k.repositories.UserRepository;
import com.example.w40k.services.ShipService;
import com.example.w40k.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    private final ShipService shipService;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public HomeController(ShipService shipService, UserService userService, UserRepository userRepository) {
        this.shipService = shipService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

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
        return "index";
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password) {
        // Create a new user object
        User user = new User(username, email, password);
        // Save the user to the database
        userService.save(user);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, ModelMap model) {
        // Check if the provided username, email, and password match a record in the database
        User foundUser = userRepository.findByUsernameAndEmailAndPassword(
                user.getUsername(), user.getEmail(), user.getPassword());

        if (foundUser != null) {
            return "redirect:/index";
        } else {
            model.addAttribute("message", "User not found");
            return "/login";
        }
    }

}
