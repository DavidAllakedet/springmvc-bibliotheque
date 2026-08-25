<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

<h2 class="page-title"><i class="bi bi-arrow-left-right"></i> Liste des Emprunts</h2>
<div class="d-flex justify-content-between mb-3">
    <div></div>
    <a href="${pageContext.request.contextPath}/emprunts/add" class="btn btn-info text-white"><i class="bi bi-plus-lg"></i> Nouvel Emprunt</a>
</div>
<div class="card shadow-sm">
    <div class="card-body">
        <c:choose>
            <c:when test="${empty emprunts}"><p class="text-muted text-center py-3">Aucun emprunt</p></c:when>
            <c:otherwise>
                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead class="table-light"><tr><th>#</th><th>Livre</th><th>Emprunteur</th><th>Date Emprunt</th><th>Date Retour</th><th>Statut</th><th>Actions</th></tr></thead>
                        <tbody>
                            <c:forEach var="emprunt" items="${emprunts}">
                                <tr>
                                    <td>${emprunt.id}</td><td class="fw-semibold">${emprunt.livreTitre}</td><td>${emprunt.nomEmprunteur}</td>
                                    <td><fmt:parseDate value="${emprunt.dateEmprunt}" pattern="yyyy-MM-dd" var="d1" type="date"/><fmt:formatDate value="${d1}" pattern="dd/MM/yyyy"/></td>
                                    <td><c:if test="${emprunt.dateRetour != null}"><fmt:parseDate value="${emprunt.dateRetour}" pattern="yyyy-MM-dd" var="d2" type="date"/><fmt:formatDate value="${d2}" pattern="dd/MM/yyyy"/></c:if></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${emprunt.statut == 'EN_COURS'}"><span class="badge bg-primary">En cours</span></c:when>
                                            <c:when test="${emprunt.statut == 'RETOURNE'}"><span class="badge bg-success">Retourne</span></c:when>
                                            <c:otherwise><span class="badge bg-danger">En retard</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:if test="${emprunt.statut == 'EN_COURS'}">
                                            <form action="${pageContext.request.contextPath}/emprunts/return/${emprunt.id}" method="post" style="display:inline">
                                                <button type="submit" class="btn btn-sm btn-outline-success" title="Retourner le livre"><i class="bi bi-arrow-return-left"></i></button>
                                            </form>
                                        </c:if>
                                        <form action="${pageContext.request.contextPath}/emprunts/delete/${emprunt.id}" method="post" style="display:inline" onsubmit="return confirm('Supprimer cet emprunt?')">
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
