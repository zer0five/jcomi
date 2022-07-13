<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="navbar navbar-expand-sm navbar-light bg-light">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">
            <img src="${pageContext.request.contextPath}/assets/logo.svg" alt="logo" style="width: 160px">
        </a>
        <button class="navbar-toggler d-lg-none" type="button" data-bs-toggle="collapse"
                data-bs-target="#collapsibleNavId" aria-controls="collapsibleNavId" aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="collapsibleNavId">
            <div class="navbar-nav me-auto mt-sm-2 mt-lg-0">
                <div class="input-group">
                    <input type="text" class="form-control rounded-pill rounded-end ps-4" id="search-box"
                           placeholder="Search">
                    <button class="btn btn-outline-primary px-3 rounded-pill rounded-start" type="button">
                        <i class="bi bi-search" aria-hidden="true"></i>
                    </button>
                </div>
            </div>
            <div class="d-flex">
                <jsp:include page="user/auth-buttons.jsp"/>
            </div>
        </div>
    </div>
</nav>