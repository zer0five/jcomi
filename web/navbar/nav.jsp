<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="genres" class="org.jcomi.entity.genre.GenreDataAccess" scope="request"/>
<style type="text/css">
    /* ============ desktop view ============ */
    @media all and (min-width: 992px) {
        .navbar .nav-item .dropdown-menu {
            display: none;
        }


        .navbar .nav-item:hover .dropdown-menu {
            display: block;
        }

        .navbar .nav-item .dropdown-menu {
            margin-top: 0;
        }
    }

    /* ============ desktop view .end// ============ */
</style>
<nav class="navbar navbar-expand-sm navbar-light bg-light">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">
            <img src="${pageContext.request.contextPath}/assets/logo.svg" alt="logo" style="width: 160px"/>
        </a>

        <div class="navbar-nav me-auto mt-sm-2 mt-lg-0">
            <div class="input-group rounded-pill shadow-sm">
                <input type="text" class="form-control rounded-pill rounded-end ps-4" id="search-box"
                       placeholder="Search">
                <button class="btn btn-outline-primary px-3 rounded-pill rounded-start" type="button">
                    <i class="bi bi-search" aria-hidden="true"></i>
                </button>
            </div>
        </div>
        <div class="d-flex">
            <c:if test="${sessionScope.user == null}">
                <jsp:include page="user/auth-buttons.jsp"/>
            </c:if>
            <c:if test="${sessionScope.user != null}">
                <jsp:include page="user/profile_mini.jsp"/>
            </c:if>
        </div>
    </div>

</nav>
<style>
    .navbar .megamenu{ padding: 1rem; }

    /* ============ desktop view ============ */
    @media all and (min-width: 992px) {

        .navbar .has-megamenu{position:static!important;}
        .navbar .megamenu{left:0; right:0; width:100%; margin-top:0;  }

    }	
    /* ============ desktop view .end// ============ */

    /* ============ mobile view ============ */
    @media(max-width: 991px){
        .navbar.fixed-top .navbar-collapse, .navbar.sticky-top .navbar-collapse{
            overflow-y: auto;
            max-height: 90vh;
            margin-top:10px;
        }
    }
    /* ============ mobile view .end// ============ */
</style>                   
<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
    <div class="container">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#main_nav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="main_nav">
            <ul class="navbar-nav">
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/comic/search"> Search comic </a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/comic/add">Add comic </a></li>
                <li class="nav-item dropdown has-megamenu">
                    <a class="nav-link dropdown-toggle" href="javascript:void(0)" aria-expanded="false">Genres</a>
                    <div class="dropdown-menu megamenu" role="menu">
                        <div class="container">
                            <div class="row px-5">
                                <c:set var="i" value="0"/>
                                <c:forEach items="${genres.all}" var="genre">
                                    <div class="col-2 py-1">
                                        <a class="text-decoration-none text-dark" href="${pageContext.request.contextPath}/comic/search?genre=${genre.genre}" id="genre-${genre.id}" data-bs-toggle="tooltip" title="${genre.description}">${genre.genre}</a>
                                    </div>
                                    <c:set var="i" value="${i + 1}"/>
                                </c:forEach>
                            </div>
                        </div>
                    </div> <!-- dropdown-mega-menu.// -->
                </li>
            </ul>
        </div> <!-- navbar-collapse.// -->
    </div> <!-- container-fluid.// -->
</nav>
