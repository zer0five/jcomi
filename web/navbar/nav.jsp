<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    

    .dropdown-menu.columns {
        min-width: 600px;
    }
    .dropdown-menu li a {
        padding: 5px 15px;
        font-weight: 300;
    }
    .multi-column-dropdown {
        list-style: none;
        margin: 0px;
        padding: 0px;
    }
    .multi-column-dropdown li a {
        display: block;
        clear: both;
        line-height: 1.428571429;
        color: #333;
        white-space: normal;
    }
    .multi-column-dropdown li a:hover {
        
        color: #262626;
        background-color: #999;
    }

    @media (max-width: 767px) {
        .dropdown-menu.multi-column {
            min-width: 240px !important;
            overflow-x: hidden;
        }
    }
    li a {
        text-decoration: none;
    }

    /* ============ desktop view .end// ============ */
</style>
<nav class="navbar navbar-expand-sm navbar-light bg-light">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">
            <img src="${pageContext.request.contextPath}/assets/logo.svg" alt="logo" style="width: 120px">
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
        <div class="d-flex mt-3 mt-sm-0">
            <jsp:include page="user/auth-buttons.jsp"/>
        </div>
    </div>

</nav>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="index.jsp">HOME</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#main_nav"
                aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="main_nav">
            <ul class="navbar-nav">
                <li class="nav-item "> <a class="nav-link" href="#">Search comic </a> </li
                <li class="nav-item"><a class="nav-link" href="#"> Upload </a></li>
                <!-- drop down menu -->
                <li class="nav-item dropdown">
                    </ul>
                </li>
            </ul>

        </div> <!-- navbar-collapse.// -->
    </div> <!-- container-fluid.// -->
</nav>
