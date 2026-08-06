<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2 class="page-title"><i class="bi bi-plus-circle"></i> Nouvel Emprunt</h2>
<div class="card shadow-sm">
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/emprunts/save" method="post">
            <div class="mb-3">
                <label class="form-label">Livre *</label>
                <select class="form-select" name="livreId" required>
                    <option value="">-- Choisir un livre --</option>
                    <c:forEach var="livre" items="${livres}">
                        <option value="${livre.id}">${livre.titre} (${livre.auteurNom})</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3"><label class="form-label">Nom de l'emprunteur *</label><input type="text" class="form-control" name="nomEmprunteur" required></div>
            <div class="row">
                <div class="col-md-6 mb-3"><label class="form-label">Date emprunt *</label><input type="date" class="form-control" name="dateEmprunt" required></div>
                <div class="col-md-6 mb-3"><label class="form-label">Date retour</label><input type="date" class="form-control" name="dateRetour"></div>
            </div>
            <div class="mb-3">
                <label class="form-label">Statut</label>
                <select class="form-select" name="statut">
                    <option value="EN_COURS" selected>En cours</option>
                    <option value="RETOURNE">Retourne</option>
                    <option value="EN_RETARD">En retard</option>
                </select>
            </div>
            <div class="d-flex gap-2">
                <button type="submit" class="btn btn-info text-white"><i class="bi bi-check-lg"></i> Enregistrer</button>
                <a href="${pageContext.request.contextPath}/emprunts" class="btn btn-outline-secondary">Annuler</a>
            </div>
        </form>
    </div>
</div>
