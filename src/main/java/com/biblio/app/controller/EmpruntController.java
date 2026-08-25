package com.biblio.app.controller;

import com.biblio.app.dto.EmpruntDto;
import com.biblio.app.service.EmpruntService;
import com.biblio.app.service.LivreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmpruntController {

    private final EmpruntService empruntService = new EmpruntService();
    private final LivreService livreService = new LivreService();

    @GetMapping("/emprunts")
    public String list(Model model) {
        model.addAttribute("emprunts", empruntService.findAll());
        return "emprunts";
    }

    @GetMapping("/emprunts/add")
    public String showAddForm(Model model) {
        model.addAttribute("emprunt", new EmpruntDto());
        model.addAttribute("livres", livreService.findAll());
        return "add-emprunt";
    }

    @PostMapping("/emprunts/save")
    public String save(@RequestParam("livreId") Long livreId,
                       @RequestParam("nomEmprunteur") String nomEmprunteur,
                       @RequestParam("dateEmprunt") String dateEmprunt,
                       @RequestParam(value = "dateRetour", required = false) String dateRetour,
                       @RequestParam(value = "statut", defaultValue = "EN_COURS") String statut,
                       RedirectAttributes redirectAttributes) {
        if (nomEmprunteur == null || nomEmprunteur.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("emprunteurError", "Le nom de l'emprunteur est obligatoire.");
            return "redirect:/emprunts/add";
        }
        if (livreId == null) {
            redirectAttributes.addFlashAttribute("livreError", "Veuillez selectionner un livre.");
            return "redirect:/emprunts/add";
        }
        EmpruntDto dto = new EmpruntDto();
        dto.setLivreId(livreId); dto.setNomEmprunteur(nomEmprunteur.trim());
        dto.setDateEmprunt(java.time.LocalDate.parse(dateEmprunt));
        if (dateRetour != null && !dateRetour.isEmpty()) dto.setDateRetour(java.time.LocalDate.parse(dateRetour));
        dto.setStatut(statut);
        empruntService.save(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Emprunt enregistre avec succes !");
        return "redirect:/emprunts";
    }

    @PostMapping("/emprunts/return/{id}")
    public String returnBook(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        EmpruntDto emprunt = empruntService.findById(id);
        if (emprunt == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Emprunt introuvable.");
            return "redirect:/emprunts";
        }
        emprunt.setStatut("RETOURNE");
        emprunt.setDateRetour(java.time.LocalDate.now());
        empruntService.update(emprunt);
        redirectAttributes.addFlashAttribute("successMessage", "Livre retourne avec succes !");
        return "redirect:/emprunts";
    }

    @PostMapping("/emprunts/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        EmpruntDto emprunt = empruntService.findById(id);
        if (emprunt == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Emprunt introuvable.");
            return "redirect:/emprunts";
        }
        empruntService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Emprunt supprime avec succes !");
        return "redirect:/emprunts";
    }
}
