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

<h2 class="page-title"><i class="bi bi-people"></i> Liste des Auteurs</h2>
<div class="d-flex justify-content-between mb-3">
    <div></div>
    <a href="${pageContext.request.contextPath}/auteurs/add" class="btn btn-success"><i class="bi bi-plus-lg"></i> Nouvel Auteur</a>
</div>
<div class="card shadow-sm">
    <div class="card-body">
        <c:choose>
            <c:when test="${empty auteurs}"><p class="text-muted text-center py-3">Aucun auteur</p></c:when>
            <c:otherwise>
                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead class="table-light"><tr><th>#</th><th>Nom</th><th>Prenom</th><th>Biographie</th><th>Actions</th></tr></thead>
                        <tbody>
                            <c:forEach var="auteur" items="${auteurs}">
                                <tr>
                                    <td>${auteur.id}</td><td class="fw-semibold">${auteur.nom}</td><td>${auteur.prenom}</td><td class="text-muted">${auteur.biographie}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/auteurs/edit/${auteur.id}" class="btn btn-sm btn-outline-warning"><i class="bi bi-pencil"></i></a>
                                        <form action="${pageContext.request.contextPath}/auteurs/delete/${auteur.id}" method="post" style="display:inline" onsubmit="return confirm('Supprimer cet auteur?')">
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
