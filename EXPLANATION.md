# EXPLICATION DETAILLEE - Spring MVC - JSP - JSTL - Tiles

## Table des matieres

1. [Introduction](#introduction)
2. [Technologies utilisees](#technologies-utilisees)
3. [Architecture du projet](#architecture-du-projet)
4. [Explication de JSP et JSTL](#explication-de-jsp-et-jstl)
5. [Explication de Apache Tiles](#explication-de-apache-tiles)
6. [Configuration du Dual View Resolver](#configuration-du-dual-view-resolver)
7. [Layout Tiles en detail](#layout-tiles-en-detail)
8. [Couche Controller (Multi-entites)](#couche-controller-multi-entites)
9. [Couche Service et DAO](#couche-service-et-dao)
10. [Base de donnees (Relations)](#base-de-donnees-relations)
11. [Vues JSP avec Tiles](#vues-jsp-avec-tiles)
12. [Comparaison avec le Projet 1](#comparaison-avec-le-projet-1)
13. [Conclusion](#conclusion)

---

## Introduction

Ce projet est une application de **gestion de bibliotheque** developpee avec **Spring MVC**, **JSP**, **JSTL** et **Apache Tiles**. Il demontre comment creer un **layout reutilisable** pour eviter la duplication de code HTML.

### Objectifs pedagogiques

- Comprendre le concept de **layout/template**
- Apprendre a utiliser **Apache Tiles** pour les mises en page
- Gerer **plusieurs entites** (Livres, Auteurs, Emprunts)
- Implementer un **dual view resolver** (Tiles + JSP)
- Comprendre les **relations entre entites** (Foreign Keys)

---

## Technologies utilisees

| Technologie | Version | Role |
|-------------|---------|------|
| **Java** | 11 | Langage de programmation |
| **Spring MVC** | 5.2.22.RELEASE | Framework web |
| **JSP** | 2.3 | Pages dynamiques |
| **JSTL** | 1.2 | Tags standard |
| **Apache Tiles** | 3.0.8 | Framework de mise en page |
| **Hibernate** | 5.4.10.Final | ORM |
| **MySQL** | 8.x | Base de donnees |
| **Bootstrap** | 5.2.0 | Framework CSS |
| **Maven** | 3.9.x | Outil de build |

---

## Architecture du projet

### Architecture avec Tiles

```
┌─────────────────────────────────────────────────────────────┐
│                    BASELAYOUT.JSP                            │
│  ┌─────────────────────────────────────────────────────────┐│
│  │                    HEADER.JSP                           ││
│  │              (Commun a toutes les pages)                ││
│  └─────────────────────────────────────────────────────────┘│
│  ┌──────────────┐  ┌──────────────────────────────────────┐│
│  │   MENU.JSP   │  │           CONTENT (variable)         ││
│  │   (Sidebar)  │  │                                      ││
│  │  - Accueil   │  │  livres.jsp | auteurs.jsp | etc.     ││
│  │  - Livres    │  │                                      ││
│  │  - Auteurs   │  │  (Contenu specifique a chaque page)  ││
│  │  - Emprunts  │  │                                      ││
│  └──────────────┘  └──────────────────────────────────────┘│
│  ┌─────────────────────────────────────────────────────────┐│
│  │                   FOOTER.JSP                            ││
│  │              (Commun a toutes les pages)                ││
│  └─────────────────────────────────────────────────────────┘│
└─────────────────────────────────────────────────────────────┘
```

### Structure des dossiers

```
springmvc-bibliotheque/
├── pom.xml
├── src/main/
│   ├── java/com/biblio/app/
│   │   ├── config/
│   │   │   └── SpringWebConfig.java           # Config Spring + Tiles
│   │   ├── controller/
│   │   │   ├── LivreController.java           # Gestion des livres
│   │   │   ├── AuteurController.java          # Gestion des auteurs
│   │   │   └── EmpruntController.java         # Gestion des emprunts
│   │   ├── entities/
│   │   │   ├── LivreEntity.java
│   │   │   ├── AuteurEntity.java
│   │   │   └── EmpruntEntity.java
│   │   ├── dto/
│   │   │   ├── LivreDto.java
│   │   │   ├── AuteurDto.java
│   │   │   └── EmpruntDto.java
│   │   ├── dao/
│   │   │   ├── ILivreDao.java / LivreDao.java
│   │   │   ├── IAuteurDao.java / AuteurDao.java
│   │   │   └── IEmpruntDao.java / EmpruntDao.java
│   │   ├── mapper/
│   │   │   └── BiblioMapper.java
│   │   └── service/
│   │       ├── LivreService.java
│   │       ├── AuteurService.java
│   │       └── EmpruntService.java
│   └── webapp/WEB-INF/
│       ├── defs/
│       │   └── tiles.xml                      # Configuration Tiles
│       └── jsp/
│           ├── baseLayout.jsp                  # Layout de base
│           ├── header.jsp                      # En-tete
│           ├── menu.jsp                        # Menu lateral
│           ├── footer.jsp                      # Pied de page
│           └── pages/
│               ├── index.jsp                   # Contenu: Accueil
│               ├── livres.jsp                  # Contenu: Livres
│               ├── add-livre.jsp               # Contenu: Ajouter livre
│               ├── auteurs.jsp                 # Contenu: Auteurs
│               ├── add-auteur.jsp              # Contenu: Ajouter auteur
│               ├── emprunts.jsp                # Contenu: Emprunts
│               └── add-emprunt.jsp             # Contenu: Ajouter emprunt
```

---

## Explication de JSP et JSTL

### JSP (JavaServer Pages)

JSP est une technologie qui permet de creer des pages web dynamiques en melangeant HTML et Java.

```jsp
<%-- Exemple JSP --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>${titre}</title></head>
<body>
    <h1>${titre}</h1>
    <p>Nombre de livres : ${totalLivres}</p>
</body>
</html>
```

### JSTL (JSP Standard Tag Library)

JSTL fournit des tags pour manipuler les donnees sans code Java brut.

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Boucle sur les livres --%>
<c:forEach var="livre" items="${livres}">
    <tr>
        <td>${livre.titre}</td>
        <td>${livre.auteurNom}</td>
        <td>
            <c:if test="${livre.quantite > 0}">
                <span class="badge bg-success">Disponible</span>
            </c:if>
        </td>
    </tr>
</c:forEach>
```

---

## Explication de Apache Tiles

### Qu'est-ce qu'Apache Tiles ?

**Apache Tiles** est un framework de **mise en page** (layout) qui permet de creer des **templates reutilisables**. Il evite la duplication de code HTML entre les pages.

### Le probleme sans Tiles

**Sans Tiles (mauvais) :**
```jsp
<%-- index.jsp --%>
<html>
<head>
    <link href="bootstrap.css">
    <title>Accueil</title>
</head>
<body>
    <nav>Menu commun...</nav>
    <h1>Accueil</h1>
    <footer>Footer commun...</footer>
</body>
</html>

<%-- livres.jsp --%>
<html>
<head>
    <link href="bootstrap.css">   <%-- DUPLICATION ! --%>
    <title>Livres</title>
</head>
<body>
    <nav>Menu commun...</nav>     <%-- DUPLICATION ! --%>
    <h1>Livres</h1>
    <footer>Footer commun...</footer>  <%-- DUPLICATION ! --%>
</body>
</html>
```

### La solution avec Tiles

**Avec Tiles (bon) :**
```jsp
<%-- baseLayout.jsp (template unique) --%>
<html>
<head>
    <title><tiles:getAsString name="title"/></title>
    <link href="bootstrap.css">
</head>
<body>
    <tiles:insertAttribute name="header"/>
    <tiles:insertAttribute name="menu"/>
    <tiles:insertAttribute name="content"/>
    <tiles:insertAttribute name="footer"/>
</body>
</html>

<%-- livres.jsp (contenu uniquement) --%>
<h1>Liste des Livres</h1>
<table>...</table>
```

### Configuration tiles.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE tiles-definitions PUBLIC
        "-//Apache Software Foundation//DTD Tiles Configuration 3.0//EN"
        "http://tiles.apache.org/dtds/tiles-config_3_0.dtd">
<tiles-definitions>

    <!-- Layout de base -->
    <definition name="baseLayout" template="/WEB-INF/jsp/baseLayout.jsp">
        <put-attribute name="title" value=""/>
        <put-attribute name="header" value="/WEB-INF/jsp/header.jsp"/>
        <put-attribute name="menu" value="/WEB-INF/jsp/menu.jsp"/>
        <put-attribute name="content" value=""/>
        <put-attribute name="footer" value="/WEB-INF/jsp/footer.jsp"/>
    </definition>

    <!-- Pages heritant du layout -->
    <definition name="index" extends="baseLayout">
        <put-attribute name="title" value="Accueil - Bibliotheque"/>
        <put-attribute name="content" value="/WEB-INF/jsp/pages/index.jsp"/>
    </definition>

    <definition name="livres" extends="baseLayout">
        <put-attribute name="title" value="Liste des Livres"/>
        <put-attribute name="content" value="/WEB-INF/jsp/pages/livres.jsp"/>
    </definition>

    <definition name="auteurs" extends="baseLayout">
        <put-attribute name="title" value="Liste des Auteurs"/>
        <put-attribute name="content" value="/WEB-INF/jsp/pages/auteurs.jsp"/>
    </definition>

</tiles-definitions>
```

**Explication :**
- `baseLayout` : Template de base avec tous les attributs
- `extends="baseLayout"` : Chaque page herite du layout
- `template` : Fichier JSP contenant la structure
- `put-attribute` : Remplit les zones du template

---

## Configuration du Dual View Resolver

### SpringWebConfig.java

```java
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.biblio.app")
public class SpringWebConfig implements WebMvcConfigurer {

    // 1. Configuration de Tiles
    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer configurer = new TilesConfigurer();
        configurer.setDefinitions("WEB-INF/defs/tiles.xml");
        configurer.setCheckRefresh(true);
        return configurer;
    }

    // 2. View Resolver pour Tiles (Prioritaire)
    @Bean
    public TilesViewResolver tilesViewResolver() {
        TilesViewResolver resolver = new TilesViewResolver();
        resolver.setViewClass(TilesView.class);
        resolver.setOrder(0);  // <-- PRIORITAIRE
        return resolver;
    }

    // 3. View Resolver pour JSP (Fallback)
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setOrder(1);  // <-- SECONDAIRE
        return viewResolver;
    }
}
```

### Comment fonctionne le Dual View Resolver ?

```
Controller retourne "livres"
         │
         ▼
┌─────────────────────────────────────────┐
│         TilesViewResolver (order=0)     │
│  Cherche une definition "livres"        │
│  dans tiles.xml                         │
├─────────────────────────────────────────┤
│  Si trouvee :                           │
│    Utilise baseLayout.jsp + livres.jsp  │
│    --> RENDU FINAL                      │
├─────────────────────────────────────────┤
│  Si pas trouvee :                       │
│    Passe au resolver suivant            │
└─────────────────────────────────────────┘
         │
         ▼
┌─────────────────────────────────────────┐
│  InternalResourceViewResolver (order=1) │
│  Cherche /WEB-INF/jsp/livres.jsp        │
│  --> RENDU FINAL                        │
└─────────────────────────────────────────┘
```

---

## Layout Tiles en detail

### baseLayout.jsp

```jsp
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title><tiles:getAsString name="title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .sidebar { min-height: 100vh; background: linear-gradient(180deg, #1a1a2e 0%, #16213e 100%); }
        .sidebar .nav-link { color: rgba(255,255,255,0.7); padding: 0.8rem 1.2rem; }
        .sidebar .nav-link:hover { color: #fff; background: rgba(255,255,255,0.1); }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <!-- Menu lateral -->
            <nav class="col-md-2 sidebar">
                <div class="sidebar-brand">
                    <i class="bi bi-book"></i> Bibliotheque
                </div>
                <ul class="nav flex-column mt-3">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/">
                            <i class="bi bi-house"></i> Accueil
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/livres">
                            <i class="bi bi-book-half"></i> Livres
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/auteurs">
                            <i class="bi bi-people"></i> Auteurs
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/emprunts">
                            <i class="bi bi-arrow-left-right"></i> Emprunts
                        </a>
                    </li>
                </ul>
            </nav>
            
            <!-- Contenu principal -->
            <main class="col-md-10 main-content">
                <tiles:insertAttribute name="content"/>
            </main>
        </div>
    </div>
</body>
</html>
```

### Exemple de page contenu (livres.jsp)

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2 class="page-title"><i class="bi bi-book-half"></i> Liste des Livres</h2>

<a href="${pageContext.request.contextPath}/livres/add" class="btn btn-primary">
    <i class="bi bi-plus-lg"></i> Nouveau Livre
</a>

<div class="card shadow-sm">
    <div class="card-body">
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>Titre</th>
                    <th>Auteur</th>
                    <th>Categorie</th>
                    <th>Quantite</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="livre" items="${livres}">
                    <tr>
                        <td class="fw-semibold">${livre.titre}</td>
                        <td>${livre.auteurNom}</td>
                        <td><span class="badge bg-secondary">${livre.categorie}</span></td>
                        <td>${livre.quantite}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/livres/edit/${livre.id}" 
                               class="btn btn-sm btn-outline-warning">
                                <i class="bi bi-pencil"></i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
```

---

## Couche Controller (Multi-entites)

### LivreController.java

```java
@Controller
public class LivreController {

    private final LivreService livreService = new LivreService();
    private final AuteurService auteurService = new AuteurService();

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("livres", livreService.findAll());
        model.addAttribute("totalLivres", livreService.findAll().size());
        model.addAttribute("totalAuteurs", auteurService.findAll().size());
        return "index";  // Resolu par Tiles vers baseLayout + index.jsp
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
                       @RequestParam("auteurId") Long auteurId) {
        LivreDto dto = new LivreDto();
        dto.setTitre(titre);
        dto.setAuteurId(auteurId);
        livreService.save(dto);
        return "redirect:/livres";
    }
}
```

---

## Base de donnees (Relations)

### Script SQL

```sql
-- Table des auteurs
CREATE TABLE auteurs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100),
    biographie TEXT
);

-- Table des livres (avec Foreign Key vers auteurs)
CREATE TABLE livres (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    categorie VARCHAR(100),
    quantite INT DEFAULT 1,
    auteur_id BIGINT,
    FOREIGN KEY (auteur_id) REFERENCES auteurs(id)
);

-- Table des emprunts (avec Foreign Key vers livres)
CREATE TABLE emprunts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    livre_id BIGINT,
    nom_emprunteur VARCHAR(200),
    date_emprunt DATE,
    date_retour DATE,
    statut ENUM('EN_COURS', 'RETOURNE', 'EN_RETARD') DEFAULT 'EN_COURS',
    FOREIGN KEY (livre_id) REFERENCES livres(id)
);
```

### Diagramme de relations

```
┌─────────────────┐       ┌─────────────────┐
│    AUTEURS      │       │     LIVRES      │
├─────────────────┤       ├─────────────────┤
│ id (PK)         │◄──────│ auteur_id (FK)  │
│ nom             │   1,N │ id (PK)         │
│ prenom          │       │ titre           │
│ biographie      │       │ isbn            │
└─────────────────┘       │ categorie       │
                          │ quantite        │
                          └─────────────────┘
                                  │
                                  │ 1,N
                                  ▼
                          ┌─────────────────┐
                          │    EMPRUNTS     │
                          ├─────────────────┤
                          │ id (PK)         │
                          │ livre_id (FK)   │
                          │ nom_emprunteur  │
                          │ date_emprunt    │
                          │ date_retour     │
                          │ statut          │
                          └─────────────────┘
```

### Entity avec relation ManyToOne

```java
@Entity
@Table(name = "livres")
public class LivreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "auteur_id")
    private AuteurEntity auteur;

    // Getters et Setters
}
```

**Explication :**
- `@ManyToOne` : Un livre a un seul auteur, un auteur a plusieurs livres
- `@JoinColumn(name = "auteur_id")` : Cle etrangere dans la table livres
- `FetchType.EAGER` : Charge l'auteur automatiquement avec le livre

---

## Comparaison avec le Projet 1

| Critere | Projet 1 (Todo) | Projet 2 (Bibliotheque) |
|---------|-----------------|-------------------------|
| **Technologie** | JSP + JSTL | JSP + JSTL + **Tiles** |
| **Layout** | Chaque JSP independante | **Layout reutilisable** |
| **Entites** | 1 (Todo) | 3 (Livre, Auteur, Emprunt) |
| **Controllers** | 1 | 3 |
| **Relations** | Aucune | **ManyToOne** |
| **Duplication** | Oui (menu, header, footer) | **Non (Tiles)** |
| **Maintenance** | Difficile | **Facile** |

---

## Conclusion

Ce projet demontre l'utilisation d'**Apache Tiles** pour creer un layout reutilisable :

1. **Tiles** : Evite la duplication de code HTML
2. **Dual View Resolver** : Tiles (prioritaire) + JSP (fallback)
3. **Multi-entites** : Gestion de Livres, Auteurs et Emprunts
4. **Relations** : Foreign Keys entre les tables

### Avantages de Tiles
- Layout centralise et reutilisable
- Code HTML non duplique
- Maintenance facilitee
- Separation claire structure/contenu

### Vers quoi aller ?
- **Thymeleaf** : Templates HTML naturels sans JSP (Projet 3)
