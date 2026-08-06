package com.biblio.app.controller;

import com.biblio.app.dto.EmpruntDto;
import com.biblio.app.service.EmpruntService;
import com.biblio.app.service.LivreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
                       @RequestParam(value = "statut", defaultValue = "EN_COURS") String statut) {
        EmpruntDto dto = new EmpruntDto();
        dto.setLivreId(livreId); dto.setNomEmprunteur(nomEmprunteur);
        dto.setDateEmprunt(java.time.LocalDate.parse(dateEmprunt));
        if (dateRetour != null && !dateRetour.isEmpty()) dto.setDateRetour(java.time.LocalDate.parse(dateRetour));
        dto.setStatut(statut);
        empruntService.save(dto);
        return "redirect:/emprunts";
    }

    @GetMapping("/emprunts/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        empruntService.delete(id);
        return "redirect:/emprunts";
    }
}
