<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2 class="page-title"><i class="bi bi-plus-circle"></i> Ajouter / Modifier un Auteur</h2>
<div class="card shadow-sm">
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/auteurs/${auteur.id != null ? 'update' : 'save'}" method="post">
            <c:if test="${auteur.id != null}"><input type="hidden" name="id" value="${auteur.id}"/></c:if>
            <div class="row">
                <div class="col-md-6 mb-3"><label class="form-label">Nom *</label><input type="text" class="form-control" name="nom" value="${auteur.nom}" required></div>
                <div class="col-md-6 mb-3"><label class="form-label">Prenom</label><input type="text" class="form-control" name="prenom" value="${auteur.prenom}"></div>
            </div>
            <div class="mb-3"><label class="form-label">Biographie</label><textarea class="form-control" name="biographie" rows="3">${auteur.biographie}</textarea></div>
            <div class="d-flex gap-2">
                <button type="submit" class="btn btn-success"><i class="bi bi-check-lg"></i> Enregistrer</button>
                <a href="${pageContext.request.contextPath}/auteurs" class="btn btn-outline-secondary">Annuler</a>
            </div>
        </form>
    </div>
</div>
