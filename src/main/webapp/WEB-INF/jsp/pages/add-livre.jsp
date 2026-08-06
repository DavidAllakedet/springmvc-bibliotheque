<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2 class="page-title"><i class="bi bi-plus-circle"></i> Ajouter / Modifier un Livre</h2>
<div class="card shadow-sm">
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/livres/${livre.id != null ? 'update' : 'save'}" method="post">
            <c:if test="${livre.id != null}"><input type="hidden" name="id" value="${livre.id}"/></c:if>
            <div class="mb-3"><label class="form-label">Titre *</label><input type="text" class="form-control" name="titre" value="${livre.titre}" required></div>
            <div class="row">
                <div class="col-md-6 mb-3"><label class="form-label">ISBN</label><input type="text" class="form-control" name="isbn" value="${livre.isbn}"></div>
                <div class="col-md-6 mb-3"><label class="form-label">Categorie</label><input type="text" class="form-control" name="categorie" value="${livre.categorie}"></div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3"><label class="form-label">Quantite</label><input type="number" class="form-control" name="quantite" value="${livre.quantite != null ? livre.quantite : 1}"></div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">Auteur *</label>
                    <select class="form-select" name="auteurId" required>
                        <option value="">-- Choisir --</option>
                        <c:forEach var="auteur" items="${auteurs}">
                            <option value="${auteur.id}" ${livre.auteurId == auteur.id ? 'selected' : ''}>${auteur.prenom} ${auteur.nom}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="d-flex gap-2">
                <button type="submit" class="btn btn-primary"><i class="bi bi-check-lg"></i> Enregistrer</button>
                <a href="${pageContext.request.contextPath}/livres" class="btn btn-outline-secondary">Annuler</a>
            </div>
        </form>
    </div>
</div>
