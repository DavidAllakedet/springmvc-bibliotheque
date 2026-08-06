# Spring MVC - Bibliotheque (JSP + JSTL + Tiles)

[![Java](https://img.shields.io/badge/Java-11-green)](https://www.oracle.com/java/)
[![Spring MVC](https://img.shields.io/badge/Spring%20MVC-5.2.22-yellowgreen)](https://spring.io/projects/spring-framework)
[![JSP](https://img.shields.io/badge/JSP-JSTL-Tiles-blue)](https://tiles.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-blue)](https://www.mysql.com/)
[![Bootstrap](https://img.shields.io/badge/Bootstrap-5.2-purple)](https://getbootstrap.com/)

## Description

Application de gestion de bibliotheque developpee avec Spring MVC, JSP, JSTL et Apache Tiles pour la gestion des mises en page. Cette application permet de gerer des livres, des auteurs et des emprunts avec une architecture en couches et un layout Tiles reutilisable.

### Capture d'ecran de l'interface

![Dashboard](screenshots/dashboard.png)
*Tableau de bord avec statistiques de la bibliotheque*

![Liste des livres](screenshots/livres.png)
*Liste des livres avec informations de l'auteur*

![Layout Tiles](screenshots/layout.png)
*Layout Tiles reutilisable avec sidebar*

---

## Environnement

| Outil | Version |
|-------|---------|
| JDK | 11 |
| Tomcat | 9.x |
| Maven | 3.9.x |
| Spring MVC | 5.2.22.RELEASE |
| Hibernate | 5.4.10.Final |
| Apache Tiles | 3.0.8 |
| MySQL | 8.x |
| Bootstrap | 5.2.0 |

---

## Architecture du projet

```
springmvc-bibliotheque/
├── pom.xml
├── src/main/
│   ├── java/com/biblio/app/
│   │   ├── MyServletInitializer.java
│   │   ├── config/
│   │   │   ├── SpringWebConfig.java          # Config Spring + Tiles
│   │   │   ├── HibernateUtil.java
│   │   │   └── PropertiesReader.java
│   │   ├── controller/
│   │   │   ├── LivreController.java
│   │   │   ├── AuteurController.java
│   │   │   └── EmpruntController.java
│   │   ├── entities/
│   │   │   ├── LivreEntity.java
│   │   │   ├── AuteurEntity.java
│   │   │   └── EmpruntEntity.java
│   │   ├── dto/
│   │   │   ├── LivreDto.java
│   │   │   ├── AuteurDto.java
│   │   │   └── EmpruntDto.java
│   │   ├── dao/
│   │   │   ├── Repository.java
│   │   │   ├── RepositoryImpl.java
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
│       │   └── tiles.xml                     # Configuration Tiles
│       └── jsp/
│           ├── baseLayout.jsp                # Layout de base Tiles
│           ├── header.jsp
│           ├── menu.jsp
│           ├── footer.jsp
│           └── pages/
│               ├── index.jsp
│               ├── livres.jsp
│               ├── add-livre.jsp
│               ├── auteurs.jsp
│               ├── add-auteur.jsp
│               ├── emprunts.jsp
│               └── add-emprunt.jsp
```

---

## Configuration Apache Tiles

### tiles.xml

```xml
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

### baseLayout.jsp (Layout principal)

```jsp
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
<head>
    <title><tiles:getAsString name="title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <nav class="col-md-2 sidebar">
                <!-- Menu lateral -->
            </nav>
            <main class="col-md-10 main-content">
                <tiles:insertAttribute name="content"/>
            </main>
        </div>
    </div>
</body>
</html>
```

### Configuration Spring pour Tiles

```java
@Bean
public TilesConfigurer tilesConfigurer() {
    TilesConfigurer configurer = new TilesConfigurer();
    configurer.setDefinitions("WEB-INF/defs/tiles.xml");
    return configurer;
}

@Bean
public TilesViewResolver tilesViewResolver() {
    TilesViewResolver resolver = new TilesViewResolver();
    resolver.setViewClass(TilesView.class);
    resolver.setOrder(0);  // Priorite sur JSP
    return resolver;
}
```

---

## Base de donnees

### Script SQL

```sql
CREATE DATABASE IF NOT EXISTS bibliotheque_db;

CREATE TABLE auteurs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100),
    biographie TEXT
);

CREATE TABLE livres (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    categorie VARCHAR(100),
    quantite INT DEFAULT 1,
    auteur_id BIGINT,
    FOREIGN KEY (auteur_id) REFERENCES auteurs(id)
);

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

---

## Installation

```bash
git clone https://github.com/votre-username/springmvc-bibliotheque.git
cd springmvc-bibliotheque

# Configurer database.properties
# Compiler
mvn clean install

# Deployer
cp target/springmvc-bibliotheque.war $TOMCAT_HOME/webapps/
```

---

## Routes disponibles

| Methode | URL | Description |
|---------|-----|-------------|
| GET | `/` | Tableau de bord |
| GET | `/livres` | Liste des livres |
| GET | `/livres/add` | Ajouter un livre |
| GET | `/auteurs` | Liste des auteurs |
| GET | `/auteurs/add` | Ajouter un auteur |
| GET | `/emprunts` | Liste des emprunts |
| GET | `/emprunts/add` | Nouvel emprunt |

---

## Points cles de l'implementation

### Dual View Resolver

L'application utilise deux view resolvers :
1. **TilesViewResolver** (order=0) : Prioritaire, resout les definitions Tiles
2. **InternalResourceViewResolver** (order=1) : Fallback pour les JSP directes

### Architecture en couches

```
Controller -> Service -> DAO -> Hibernate
    |            |
    v            v
  DTO          Entity
    |            |
    +-- Mapper --+
```

### Repository generique

```java
public class RepositoryImpl<T> implements Repository<T> {
    private final Class<T> entityClass;
    
    public T save(T entity) { /* Hibernate save */ }
    public T findById(Long id) { /* Hibernate get */ }
    public List<T> findAll() { /* Hibernate query */ }
}
```

---

## Technologies

- **Spring MVC 5.2.22** : Framework web
- **Apache Tiles 3.0.8** : Framework de mise en page
- **JSP + JSTL** : Pages dynamiques
- **Hibernate 5.4.10** : ORM
- **MySQL 8** : Base de donnees
- **Bootstrap 5.2** : Design responsive

---

## Auteur

Developpe avec Spring MVC - JSP - JSTL - Tiles
