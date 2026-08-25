<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty successMessage}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <i class="bi bi-check-circle-fill"></i> ${successMessage}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
</c:if>
<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i> ${errorMessage}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
</c:if>

<h2 class="page-title"><i class="bi bi-house"></i> Tableau de bord</h2>
<div class="row g-4 mb-4">
    <div class="col-md-4">
        <div class="card stat-card shadow-sm bg-primary text-white">
            <div class="card-body d-flex align-items-center">
                <i class="bi bi-book fs-1 me-3"></i>
                <div><div class="fs-2 fw-bold">${totalLivres}</div><div>Livres</div></div>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card stat-card shadow-sm bg-success text-white">
            <div class="card-body d-flex align-items-center">
                <i class="bi bi-people fs-1 me-3"></i>
                <div><div class="fs-2 fw-bold">${totalAuteurs}</div><div>Auteurs</div></div>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card stat-card shadow-sm bg-info text-white">
            <div class="card-body d-flex align-items-center">
                <i class="bi bi-arrow-left-right fs-1 me-3"></i>
                <div><div class="fs-2 fw-bold">${totalEmprunts}</div><div>Emprunts</div></div>
            </div>
        </div>
    </div>
</div>
<div class="card shadow-sm">
    <div class="card-header bg-white"><h5 class="mb-0"><i class="bi bi-book-half"></i> Derniers livres ajoutes</h5></div>
    <div class="card-body">
        <c:choose>
            <c:when test="${empty livres}">
                <p class="text-muted text-center py-3">Aucun livre pour le moment</p>
            </c:when>
            <c:otherwise>
                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead class="table-light"><tr><th>Titre</th><th>Auteur</th><th>Categorie</th><th>Quantite</th></tr></thead>
                        <tbody>
                            <c:forEach var="livre" items="${livres}" begin="0" end="4">
                                <tr><td class="fw-semibold">${livre.titre}</td><td>${livre.auteurNom}</td><td>${livre.categorie}</td><td>${livre.quantite}</td></tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
