package com.example.w40k.controllers;

import com.example.w40k.models.Ships;
import com.example.w40k.services.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    public HomeController(ShipService shipService) {
        this.shipService = shipService;
    }

    private final ShipService shipService;
    @GetMapping({"","/"})

    public String index(Model model) {
        List<Ships> ships = shipService.findAll();
        model.addAttribute("ships", ships);
        return "index";
    }
}
