package com.biblio.app.controller;

import com.biblio.app.dto.AuteurDto;
import com.biblio.app.service.AuteurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuteurController {

    private final AuteurService auteurService = new AuteurService();

    @GetMapping("/auteurs")
    public String list(Model model) {
        model.addAttribute("auteurs", auteurService.findAll());
        return "auteurs";
    }

    @GetMapping("/auteurs/add")
    public String showAddForm(Model model) {
        model.addAttribute("auteur", new AuteurDto());
        return "add-auteur";
    }

    @PostMapping("/auteurs/save")
    public String save(@RequestParam("nom") String nom,
                       @RequestParam(value = "prenom", required = false) String prenom,
                       @RequestParam(value = "biographie", required = false) String biographie) {
        AuteurDto dto = new AuteurDto();
        dto.setNom(nom); dto.setPrenom(prenom); dto.setBiographie(biographie);
        auteurService.save(dto);
        return "redirect:/auteurs";
    }

    @GetMapping("/auteurs/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("auteur", auteurService.findById(id));
        return "add-auteur";
    }

    @PostMapping("/auteurs/update")
    public String update(@RequestParam("id") Long id, @RequestParam("nom") String nom,
                         @RequestParam(value = "prenom", required = false) String prenom,
                         @RequestParam(value = "biographie", required = false) String biographie) {
        AuteurDto dto = new AuteurDto();
        dto.setId(id); dto.setNom(nom); dto.setPrenom(prenom); dto.setBiographie(biographie);
        auteurService.update(dto);
        return "redirect:/auteurs";
    }

    @GetMapping("/auteurs/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        auteurService.delete(id);
        return "redirect:/auteurs";
    }
}
