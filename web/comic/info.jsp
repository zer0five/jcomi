<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="comics" class="org.jcomi.entity.comic.ComicDataAccess"/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Home</title>
        <jsp:include page="/stylesheet.jsp"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    </head>
    <body>
        <jsp:include page="/navbar/nav.jsp"/>
        <jsp:include page="/back_to_top_btn.jsp"/>

        <div class="container">
            <div class="border rounded shadow-lg p-5">
                <%--        <div class="d-flex flex-row bd-highlight mb-3">--%>
                <%--            <div class="p-2 bd-highlight"> <a href="${pageContext.request.contextPath}"--%>
                <%--                                              class="text-dark fw-bold d-block text-truncate text-decoration-none d-inline">Home </a>--%>
                <%--            </div>--%>
                <%--            <div class="p-2 bd-highlight">/</div>--%>
                <%--            <div class="p-2 bd-highlight">--%>
                <%--                <a href="searchComic.html"--%>
                <%--                   class="text-dark fw-bold d-block text-truncate text-decoration-none d-inline">Search--%>
                <%--                    Comic</a>--%>

                <%--            </div>--%>
                <%--        </div>--%>
                <div class="row">
                    <div class="col-lg-4 align-self-center">
                        <img class="mx-auto d-block" src="${pageContext.request.contextPath}/${requestScope.comic.cover}"
                             with="190px" heigh="247px" alt="${requestScope.comic.name}">
                    </div>
                    <div class="col">
                        <h5 class="text-dark fw-bold text-truncate text-decoration-none">${requestScope.comic.name}</h5>
                        <table class="table table-borderless">
                            <tbody>
                                <tr>
                            <div class="row">
                                <td scope="row" class="col-lg-3"><span class="text-dark "><i class="bi bi-file-earmark-diff" aria-hidden="true"></i><span class="ms-1">Alternative name</span></span></td>
                                <td><span class="fw-bold">${requestScope.comic.name}</span></td>
                            </div>
                            </tr>
                            <tr>
                            <div class="row">
                                <td scope="row" class="col-lg-2"><span class="text-dark "><i class="bi bi-person" aria-hidden="true"></i> Author</span></td>
                                <td><span>${requestScope.comic.author}</span></td>
                            </div>
                            </tr>
                            <tr>
                            <div class="row">
                                <td scope="row" class="col-lg-2"><span class="text-dark ">
                                        <i class="bi bi-eye" aria-hidden="true"></i>
                                        <span class="ms-1">View</span></span></td>
                                <td><span>${requestScope.comic.views}</span></td>
                            </div>
                            </tr>
                            </tbody>
                        </table>
                        <jsp:useBean id="comicData" class="org.jcomi.entity.comic.ComicDataAccess" scope="request"/>
                        <c:forEach var="genre" items="${comicData.getGenre(requestScope.comic)}">
                            <span class="badge rounded-pill" style="background-color: #54BAB9;">
                                <a href="${pageContext.request.contextPath}/comic/search?genre=${genre.genre}" class="text-decoration-none text-light">${genre.genre}</a>
                            </span>
                        </c:forEach>
                        <div class="d-flex justify-content-start">
                            <div class="p-2 bd-highlight ps-0">
                                <a class="btn mt-3 btn-sm btn-outline" href="${pageContext.request.contextPath}/comic/view?id=${requestScope.comic.id}&chapter=1"
                                   style="background-color: #CEE5D0;">
                                    <i class="bi bi-book" aria-hidden="true"></i>
                                    <span class="ms-1 mb-1">Read</span>
                                </a>
                            </div>
                            <c:if test="${sessionScope.user != null && sessionScope.user.admin}">
                                <div class="p-2 bd-highlight ps-0">
                                    <a class="btn mt-3 btn-sm btn-outline bg-danger" href="${pageContext.request.contextPath}/comic/delete?id=${requestScope.comic.id}">
                                        <i class="bi bi-x-lg" aria-hidden="true"></i>
                                        <span class="ms-1 mb-1">Delete</span>
                                    </a>
                                </div>
                                <div class="p-2 bd-highlight ps-0">
                                    <a class="btn mt-3 btn-sm btn-outline bg-danger" href="${pageContext.request.contextPath}/comic/chapter/add?id=${requestScope.comic.id}">
                                        <i class="bi bi-x-lg" aria-hidden="true"></i>
                                        <span class="ms-1 mb-1">Add chapter</span>
                                    </a>
                                </div>
                            </c:if>
                            <%--                    <div class="p-2 bd-highlight">--%>
                            <%--                        <button type="button" class="btn mt-3 btn-sm " style="background-color: #FCF8E8;">--%>
                            <%--                            <i class="bi bi-bookmark mb-1" aria-hidden="true"></i>--%>
                            <%--                            <span class="ms-1 mb-1">Bookmark</span>--%>
                            <%--                        </button>--%>
                            <%--                    </div>--%>
                        </div>
                    </div>
                </div>
                <h5 class="mt-5" style="color: #FC4F4F">
                    <i class="bi bi-info-circle" aria-hidden="true"></i>
                    Description
                </h5>
                <div class="container">
                    <c:if test="${requestScope.comic.description.isEmpty()}">
                        No description provided.
                    </c:if>
                    <c:if test="${!requestScope.comic.description.isEmpty()}">
                        <p>${requestScope.comic.description}</p>
                    </c:if>
                </div>
                <h5 class="mt-5" style="color: #FC4F4F">
                    <i class="bi bi-inboxes" aria-hidden="true"></i>
                    Chapter
                </h5>
                <div class="container" style="height:500px; overflow-y: scroll;">

                    <jsp:useBean id="chapterData" class="org.jcomi.entity.comic.chapter.ChapterDataAccess" scope="request"/>
                    <div class="border p-3 h-100">
                        <ul class="list-group">
                            <c:forEach var="chapter" items="${chapterData.getChapters(requestScope.comic.id)}">
                                <li class="list-group-item">
                                    <a href="${pageContext.request.contextPath}/comic/view?id=${requestScope.comic.id}&chapter=${chapter.ordinal}" class="row text-decoration-none text-dark w-100">
                                        <span class="col">
                                            Chapter ${chapter.ordinal} <c:if test="${!chapter.title.isEmpty()}">- ${chapter.title}</c:if>
                                            </span>
                                            <span class="col-auto">
                                            ${chapter.uploadDate}
                                        </span>
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <%--            <table class="table border">--%>
                    <%--                <thead>--%>
                    <%--                <tr>--%>
                    <%--                    <th scope="col">#</th>--%>
                    <%--                    <th scope="col">Date</th>--%>
                    <%--                </tr>--%>
                    <%--                <tbody>--%>
                    <%--                <c:forEach var="chapter" items="${chapterData.getChapters(requestScope.comic.id)}">--%>
                    <%--                    <a href="" class="text-decoration-none text-dark">--%>
                    <%--                        <td>Chapter ${chapter.ordinal} - ${chapter.title}</td>--%>
                    <%--                        <td>${chapter.uploadDate}</td>--%>
                    <%--                    </a>--%>
                    <%--                </c:forEach>--%>
                    <%--                </tbody>--%>
                    <%--            </table>--%>
                </div>
            </div>
        </div>

        <jsp:include page="/script.jsp"/>
    </body>
</html>
