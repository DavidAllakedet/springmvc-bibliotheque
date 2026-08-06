package com.biblio.app.controller;

import com.biblio.app.dto.LivreDto;
import com.biblio.app.service.AuteurService;
import com.biblio.app.service.LivreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LivreController {

    private final LivreService livreService = new LivreService();
    private final AuteurService auteurService = new AuteurService();

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("livres", livreService.findAll());
        model.addAttribute("totalLivres", livreService.findAll().size());
        model.addAttribute("totalAuteurs", auteurService.findAll().size());
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
                       @RequestParam("auteurId") Long auteurId) {
        LivreDto dto = new LivreDto();
        dto.setTitre(titre); dto.setIsbn(isbn); dto.setCategorie(categorie);
        dto.setQuantite(quantite); dto.setAuteurId(auteurId);
        livreService.save(dto);
        return "redirect:/livres";
    }

    @GetMapping("/livres/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("livre", livreService.findById(id));
        model.addAttribute("auteurs", auteurService.findAll());
        return "add-livre";
    }

    @PostMapping("/livres/update")
    public String update(@RequestParam("id") Long id, @RequestParam("titre") String titre,
                         @RequestParam(value = "isbn", required = false) String isbn,
                         @RequestParam(value = "categorie", required = false) String categorie,
                         @RequestParam(value = "quantite", defaultValue = "1") Integer quantite,
                         @RequestParam("auteurId") Long auteurId) {
        LivreDto dto = new LivreDto();
        dto.setId(id); dto.setTitre(titre); dto.setIsbn(isbn); dto.setCategorie(categorie);
        dto.setQuantite(quantite); dto.setAuteurId(auteurId);
        livreService.update(dto);
        return "redirect:/livres";
    }

    @GetMapping("/livres/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        livreService.delete(id);
        return "redirect:/livres";
    }
}
