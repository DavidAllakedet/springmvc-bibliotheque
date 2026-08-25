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

<h2 class="page-title"><i class="bi bi-book-half"></i> Liste des Livres</h2>
<div class="d-flex justify-content-between mb-3">
    <div></div>
    <a href="${pageContext.request.contextPath}/livres/add" class="btn btn-primary"><i class="bi bi-plus-lg"></i> Nouveau Livre</a>
</div>
<div class="card shadow-sm">
    <div class="card-body">
        <c:choose>
            <c:when test="${empty livres}"><p class="text-muted text-center py-3">Aucun livre</p></c:when>
            <c:otherwise>
                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead class="table-light"><tr><th>#</th><th>Titre</th><th>ISBN</th><th>Auteur</th><th>Categorie</th><th>Qte</th><th>Actions</th></tr></thead>
                        <tbody>
                            <c:forEach var="livre" items="${livres}">
                                <tr>
                                    <td>${livre.id}</td><td class="fw-semibold">${livre.titre}</td><td>${livre.isbn}</td><td>${livre.auteurNom}</td><td><span class="badge bg-secondary">${livre.categorie}</span></td><td>${livre.quantite}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/livres/edit/${livre.id}" class="btn btn-sm btn-outline-warning"><i class="bi bi-pencil"></i></a>
                                        <form action="${pageContext.request.contextPath}/livres/delete/${livre.id}" method="post" style="display:inline" onsubmit="return confirm('Supprimer ce livre?')">
                                            <button type="submit" class="btn btn-sm btn-outline-danger"><i class="bi bi-trash"></i></button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
