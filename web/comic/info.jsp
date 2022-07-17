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
                            <td scope="row" class="col-lg-3"><span class="text-dark "><svg
                                xmlns="http://www.w3.org/2000/svg" width="16" height="16"
                                fill="currentColor" class="bi bi-file-diff mb-1"
                                viewBox="0 0 16 16">
                                            <path
                                                d="M8 4a.5.5 0 0 1 .5.5V6H10a.5.5 0 0 1 0 1H8.5v1.5a.5.5 0 0 1-1 0V7H6a.5.5 0 0 1 0-1h1.5V4.5A.5.5 0 0 1 8 4zm-2.5 6.5A.5.5 0 0 1 6 10h4a.5.5 0 0 1 0 1H6a.5.5 0 0 1-.5-.5z"/>
                                            <path
                                                d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2zm10-1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1z"/>
                                        </svg><span class="ms-1">Alternative name</span></span></td>
                            <td><span class="fw-bold">${requestScope.comic.name}</span></td>
                        </div>
                    </tr>
                    <tr>
                        <div class="row">
                            <td scope="row" class="col-lg-2"><span class="text-dark "><svg
                                xmlns="http://www.w3.org/2000/svg" width="16" height="16"
                                fill="currentColor" class="bi bi-person mb-1" viewBox="0 0 16 16">
                                            <path
                                                d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z"/>
                                        </svg><span class="ms-1">Author</span> </span></td>
                            <td><span>${requestScope.comic.author}</span></td>
                        </div>
                    </tr>
                    <tr>
                        <div class="row">
                            <td scope="row" class="col-lg-2"><span class="text-dark "><svg
                                xmlns="http://www.w3.org/2000/svg" width="16" height="16"
                                fill="currentColor" class="bi bi-eye mb-1" viewBox="0 0 16 16">
                                            <path
                                                d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
                                            <path
                                                d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
                                        </svg><span class="ms-1">View</span></span></td>
                            <td><span>${requestScope.comic.views}</span></td>
                        </div>
                    </tr>
                    </tbody>
                </table>
                <span class="badge text-dark" style="background-color: #54BAB9;"><a href=""
                                                                                    class="text-decoration-none text-light">Action</a></span>
                <span class="badge ms-1" style="background-color: #54BAB9;"><a href=""
                                                                               class="text-decoration-none text-light">Aldult</a> </span>
                <span class="badge ms-1" style="background-color: #54BAB9;"><a href=""
                                                                               class="text-decoration-none text-light">Adventure</a></span>
                <div class="d-flex justify-content-start">
                    <div class="p-2 bd-highlight ps-0">
                        <button type="button" class="btn mt-3 btn-sm btn-outline"
                                style="background-color: #CEE5D0;">
                            <svg xmlns="http://www.w3.org/2000/svg"
                                 width="16" height="16" fill="currentColor" class="bi bi-book-fill mb-1 me-1"
                                 viewBox="0 0 16 16">
                                <path
                                    d="M8 1.783C7.015.936 5.587.81 4.287.94c-1.514.153-3.042.672-3.994 1.105A.5.5 0 0 0 0 2.5v11a.5.5 0 0 0 .707.455c.882-.4 2.303-.881 3.68-1.02 1.409-.142 2.59.087 3.223.877a.5.5 0 0 0 .78 0c.633-.79 1.814-1.019 3.222-.877 1.378.139 2.8.62 3.681 1.02A.5.5 0 0 0 16 13.5v-11a.5.5 0 0 0-.293-.455c-.952-.433-2.48-.952-3.994-1.105C10.413.809 8.985.936 8 1.783z"/>
                            </svg>
                            <span class="ms-1 mb-1">Read</span>
                        </button>
                    </div>
                    <%--                    <div class="p-2 bd-highlight">--%>
                    <%--                        <button type="button" class="btn mt-3 btn-sm " style="background-color: #FCF8E8;"><svg--%>
                    <%--                            xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"--%>
                    <%--                            class="bi bi-bookmark mb-1" viewBox="0 0 16 16">--%>
                    <%--                            <path--%>
                    <%--                                d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z" />--%>
                    <%--                        </svg><span class="ms-1 mb-1">Bookmark</span>--%>
                    <%--                        </button>--%>
                    <%--                    </div>--%>
                </div>
            </div>
        </div>
        <h5 class="mt-5" style="color: #FC4F4F">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
                 fill="currentColor" class="bi bi-info-circle me-2 mb-1" viewBox="0 0 16 16">
                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                <path
                    d="m8.93 6.588-2.29.287-.082.38.45.083c.294.07.352.176.288.469l-.738 3.468c-.194.897.105 1.319.808 1.319.545 0 1.178-.252 1.465-.598l.088-.416c-.2.176-.492.246-.686.246-.275 0-.375-.193-.304-.533L8.93 6.588zM9 4.5a1 1 0 1 1-2 0 1 1 0 0 1 2 0z"/>
            </svg>
            Description
        </h5>
        <div class="container">
            ${requestScope.comic.description}
        </div>
        <h5 class="mt-5" style="color: #FC4F4F">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
                 fill="currentColor" class="bi bi-inboxes me-2 mb-1" viewBox="0 0 16 16">
                <path
                    d="M4.98 1a.5.5 0 0 0-.39.188L1.54 5H6a.5.5 0 0 1 .5.5 1.5 1.5 0 0 0 3 0A.5.5 0 0 1 10 5h4.46l-3.05-3.812A.5.5 0 0 0 11.02 1H4.98zm9.954 5H10.45a2.5 2.5 0 0 1-4.9 0H1.066l.32 2.562A.5.5 0 0 0 1.884 9h12.234a.5.5 0 0 0 .496-.438L14.933 6zM3.809.563A1.5 1.5 0 0 1 4.981 0h6.038a1.5 1.5 0 0 1 1.172.563l3.7 4.625a.5.5 0 0 1 .105.374l-.39 3.124A1.5 1.5 0 0 1 14.117 10H1.883A1.5 1.5 0 0 1 .394 8.686l-.39-3.124a.5.5 0 0 1 .106-.374L3.81.563zM.125 11.17A.5.5 0 0 1 .5 11H6a.5.5 0 0 1 .5.5 1.5 1.5 0 0 0 3 0 .5.5 0 0 1 .5-.5h5.5a.5.5 0 0 1 .496.562l-.39 3.124A1.5 1.5 0 0 1 14.117 16H1.883a1.5 1.5 0 0 1-1.489-1.314l-.39-3.124a.5.5 0 0 1 .121-.393zm.941.83.32 2.562a.5.5 0 0 0 .497.438h12.234a.5.5 0 0 0 .496-.438l.32-2.562H10.45a2.5 2.5 0 0 1-4.9 0H1.066z"/>
            </svg>
            Chapter
        </h5>
        <div class="container" style="height:500px; overflow:auto;">

            <jsp:useBean id="chapterData" class="org.jcomi.entity.comic.chapter.ChapterDataAccess" scope="request"/>


            <table class="table border">
                <tbody>
                <c:forEach var="chapter" items="${chapterData.getChapters(requestScope.comic.id)}">
                    <a href="" class="text-decoration-none text-dark">
                        <td>Chapter ${chapter.ordinal} - ${chapter.title}</td>
                        <td>dd/mm/yyy</td>
                    </a>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="/script.jsp"/>
</body>
</html>
