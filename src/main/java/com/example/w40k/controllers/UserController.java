package com.example.w40k.controllers;

import com.example.w40k.models.User;
import com.example.w40k.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{userId}")
    public RedirectView getUserPage(@PathVariable Long userId) {
        String redirectUrl = "https://warhammer40k.fandom.com/wiki/Warhammer_40k_Wiki";

        if (userId.equals(1L)) {
            redirectUrl = "https://warhammer40k.fandom.com/wiki/Imperial_Navy";
        } else if (userId.equals(2L)) {
            redirectUrl = "https://warhammer40k.fandom.com/wiki/Space_Marines";
        }

        // Create a RedirectView with the appropriate URL
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(redirectUrl);

        return redirectView;
    }


    @GetMapping("/ships/{shipClass}")
    public RedirectView getShipPage(@PathVariable String shipClass) {
        String redirectUrl = "https://warhammer40k.fandom.com/wiki/Warhammer_40k_Wiki";

        if (shipClass.equalsIgnoreCase("escorts")) {
            redirectUrl = "https://warhammer40k.fandom.com/wiki/Escorts";
        } else if (shipClass.equalsIgnoreCase("cruisers")) {
            redirectUrl = "https://warhammer40k.fandom.com/wiki/Cruisers";
        } else if (shipClass.equalsIgnoreCase("battleships")) {
            redirectUrl = "https://warhammer40k.fandom.com/wiki/Battleships";
        }

        // Create a RedirectView with the appropriate URL
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(redirectUrl);

        return redirectView;
    }

}

