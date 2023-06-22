package com.example.w40k.controllers;

import com.example.w40k.models.*;
import com.example.w40k.services.ShipService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
public class ShipController {

    private final ShipService shipService;

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

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(redirectUrl1);

        return redirectView;
    }

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

    @GetMapping("/delete/{id}")
    public String deleteShip(Model model, @PathVariable Long id) {
        Optional<Ships> optionalShip = shipService.findById(id);
        if (optionalShip.isPresent()) {
            Ships ship = optionalShip.get();
            model.addAttribute("ship", ship);
            model.addAttribute("errorMessage", null);
            return "delete";
        } else {
            return getShipNotFoundResponse(model);
        }
    }

    @DeleteMapping ("/delete/{id}")
    public String removeShip(RedirectAttributes redirectAttributes, @PathVariable Long id) {
        Optional<Ships> optionalShip = shipService.findById(id);
        if (optionalShip.isPresent()) {
            Ships ship = optionalShip.get();
            shipService.delete(ship);
            redirectAttributes.addFlashAttribute("successMessage", "Success: Ship was deleted!");
        }
        return "redirect:/";
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    private String handleInvalidShipId(Model model, RuntimeException e) {
        return getShipNotFoundResponse(model);
    }

    private String getShipNotFoundResponse(Model model) {
        model.addAttribute("ship", null);
        model.addAttribute("errorMessage", "Error: No such ship was found!");
        return "delete";
    }
}

