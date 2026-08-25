package com.biblio.app.controller;

import com.biblio.app.dto.AuteurDto;
import com.biblio.app.service.AuteurService;
import com.biblio.app.service.LivreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuteurController {

    private final AuteurService auteurService = new AuteurService();
    private final LivreService livreService = new LivreService();

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
                       @RequestParam(value = "biographie", required = false) String biographie,
                       RedirectAttributes redirectAttributes) {
        if (nom == null || nom.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("nomError", "Le nom est obligatoire.");
            return "redirect:/auteurs/add";
        }
        AuteurDto dto = new AuteurDto();
        dto.setNom(nom.trim()); dto.setPrenom(prenom); dto.setBiographie(biographie);
        auteurService.save(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Auteur ajoute avec succes !");
        return "redirect:/auteurs";
    }

    @GetMapping("/auteurs/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        AuteurDto auteur = auteurService.findById(id);
        if (auteur == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Auteur introuvable.");
            return "redirect:/auteurs";
        }
        model.addAttribute("auteur", auteur);
        return "add-auteur";
    }

    @PostMapping("/auteurs/update")
    public String update(@RequestParam("id") Long id, @RequestParam("nom") String nom,
                         @RequestParam(value = "prenom", required = false) String prenom,
                         @RequestParam(value = "biographie", required = false) String biographie,
                         RedirectAttributes redirectAttributes) {
        if (nom == null || nom.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("nomError", "Le nom est obligatoire.");
            return "redirect:/auteurs/edit/" + id;
        }
        AuteurDto dto = new AuteurDto();
        dto.setId(id); dto.setNom(nom.trim()); dto.setPrenom(prenom); dto.setBiographie(biographie);
        auteurService.update(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Auteur mis a jour avec succes !");
        return "redirect:/auteurs";
    }

    @PostMapping("/auteurs/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        AuteurDto auteur = auteurService.findById(id);
        if (auteur == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Auteur introuvable.");
            return "redirect:/auteurs";
        }
        auteurService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Auteur supprime avec succes !");
        return "redirect:/auteurs";
    }
}
