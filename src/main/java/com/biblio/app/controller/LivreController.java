package com.biblio.app.controller;

import com.biblio.app.dto.LivreDto;
import com.biblio.app.service.AuteurService;
import com.biblio.app.service.EmpruntService;
import com.biblio.app.service.LivreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LivreController {

    private final LivreService livreService = new LivreService();
    private final AuteurService auteurService = new AuteurService();
    private final EmpruntService empruntService = new EmpruntService();

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("livres", livreService.findAll());
        model.addAttribute("totalLivres", livreService.findAll().size());
        model.addAttribute("totalAuteurs", auteurService.findAll().size());
        model.addAttribute("totalEmprunts", empruntService.findAll().size());
        return "index";
    }

    @GetMapping("/livres")
    public String list(Model model) {
        model.addAttribute("livres", livreService.findAll());
        return "livres";
    }

    @GetMapping("/livres/add")
    public String showAddForm(Model model) {
        model.addAttribute("livre", new LivreDto());
        model.addAttribute("auteurs", auteurService.findAll());
        return "add-livre";
    }

    @PostMapping("/livres/save")
    public String save(@RequestParam("titre") String titre,
                       @RequestParam(value = "isbn", required = false) String isbn,
                       @RequestParam(value = "categorie", required = false) String categorie,
                       @RequestParam(value = "quantite", defaultValue = "1") Integer quantite,
                       @RequestParam("auteurId") Long auteurId,
                       RedirectAttributes redirectAttributes) {
        if (titre == null || titre.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("titreError", "Le titre est obligatoire.");
            return "redirect:/livres/add";
        }
        if (auteurId == null) {
            redirectAttributes.addFlashAttribute("auteurError", "Veuillez selectionner un auteur.");
            return "redirect:/livres/add";
        }
        LivreDto dto = new LivreDto();
        dto.setTitre(titre.trim()); dto.setIsbn(isbn); dto.setCategorie(categorie);
        dto.setQuantite(quantite); dto.setAuteurId(auteurId);
        livreService.save(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Livre ajoute avec succes !");
        return "redirect:/livres";
    }

    @GetMapping("/livres/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        LivreDto livre = livreService.findById(id);
        if (livre == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Livre introuvable.");
            return "redirect:/livres";
        }
        model.addAttribute("livre", livre);
        model.addAttribute("auteurs", auteurService.findAll());
        return "add-livre";
    }

    @PostMapping("/livres/update")
    public String update(@RequestParam("id") Long id, @RequestParam("titre") String titre,
                         @RequestParam(value = "isbn", required = false) String isbn,
                         @RequestParam(value = "categorie", required = false) String categorie,
                         @RequestParam(value = "quantite", defaultValue = "1") Integer quantite,
                         @RequestParam("auteurId") Long auteurId,
                         RedirectAttributes redirectAttributes) {
        if (titre == null || titre.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("titreError", "Le titre est obligatoire.");
            return "redirect:/livres/edit/" + id;
        }
        LivreDto dto = new LivreDto();
        dto.setId(id); dto.setTitre(titre.trim()); dto.setIsbn(isbn); dto.setCategorie(categorie);
        dto.setQuantite(quantite); dto.setAuteurId(auteurId);
        livreService.update(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Livre mis a jour avec succes !");
        return "redirect:/livres";
    }

    @PostMapping("/livres/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        LivreDto livre = livreService.findById(id);
        if (livre == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Livre introuvable.");
            return "redirect:/livres";
        }
        livreService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Livre supprime avec succes !");
        return "redirect:/livres";
    }
}
