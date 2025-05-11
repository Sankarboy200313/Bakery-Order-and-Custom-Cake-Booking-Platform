package com.example.oopcrud.views;

import com.example.oopcrud.services.PickupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PickupViews {

    @Autowired
    private PickupService pickupService;

    @GetMapping
    public  String index(){
        return "index";
    }

    @GetMapping("/create")
    public  String create(){
        return "create";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model){

        model.addAttribute("pickupModel",pickupService.getPickupById(id));
        return "edit";
    }
}
