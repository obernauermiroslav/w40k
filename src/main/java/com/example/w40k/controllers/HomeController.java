package com.example.w40k.controllers;

import com.example.w40k.models.Ships;
import com.example.w40k.models.User;
import com.example.w40k.services.ShipService;
import com.example.w40k.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final ShipService shipService;
    private final UserService userService;

    @Autowired
    public HomeController(ShipService shipService, UserService userService) {
        this.shipService = shipService;
        this.userService = userService;
    }

    @GetMapping("/")
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
        return "redirect:/index";
    }
}