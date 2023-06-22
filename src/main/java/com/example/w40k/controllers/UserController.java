package com.example.w40k.controllers;

import com.example.w40k.models.*;
import com.example.w40k.services.ShipService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserController {



    private final ShipService shipService;

    public UserController( ShipService shipService) {

        this.shipService = shipService;
    }


    @GetMapping("/user/{shipClass}")
    public RedirectView getShipPage(@PathVariable String shipClass) {
        String redirectUrl = "http://localhost:3000/";

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

    @GetMapping("/ships/{userRole}")
    public RedirectView getUserPage(@PathVariable String userRole) {
        String redirectUrl1 = "http://localhost:3000/";

        if (userRole.equalsIgnoreCase("imperial_navy")) {
            redirectUrl1 = "https://warhammer40k.fandom.com/wiki/Imperial_Navy";
        } else if (userRole.equalsIgnoreCase("space_marines")) {
            redirectUrl1 = "https://warhammer40k.fandom.com/wiki/Space_Marines";
        }

        // Create a RedirectView with the appropriate URL
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(redirectUrl1);

        return redirectView;
    }

    @Controller
    public class ShipController {

        private final ShipService shipService;

        public ShipController(ShipService shipService) {
            this.shipService = shipService;
        }

        @GetMapping("/create")
        public String create(Model model) {
            if (!model.containsAttribute("ship")) {
                model.addAttribute("ship", new Ships());
            }
            if (!model.containsAttribute("errorMessage")) {
                model.addAttribute("errorMessage", null);
            }
            return "create";
        }

        @PostMapping("/create")
        public String store(RedirectAttributes redirectAttributes,
                            @RequestParam("title") String title,
                            @RequestParam("type") String type,
                            @RequestParam("user") String user) {
            Ships ship = new Ships();
            ship.setTitle(title);
            ship.setType(ShipClass.fromLabel(type));
            ship.setUser(UserRole.fromLabel(user));

            shipService.save(ship);
            redirectAttributes.addFlashAttribute("successMessage", "Success: Ship was created!");

           return "redirect:/";
        }
    }

}

