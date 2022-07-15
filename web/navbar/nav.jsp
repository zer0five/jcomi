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

    .dropdown-menu {
        min-width: 200px;
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
        text-decoration: none;
        color: #262626;
        background-color: #999;
    }

    @media (max-width: 767px) {
        .dropdown-menu.multi-column {
            min-width: 240px !important;
            overflow-x: hidden;
        }
    }

    a {
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
        <a class="navbar-brand" href="#">HOME</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#main_nav"
                aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="main_nav">
            <ul class="navbar-nav">
                
                <li class="nav-item"><a class="nav-link" href="#"> Search comic </a></li>
                <li class="nav-item"><a class="nav-link" href="#"> Upload comic </a></li>
                <li class="nav-item dropdown">
                    <a class="nav-link  dropdown-toggle" href="#" data-bs-toggle="dropdown"> Genre </a>
                    <ul class="dropdown-menu multi-column columns">
                        <div class="row">
                            <div class="col-4">
                                <ul class="multi-column-dropdown">
                                    <li><a href="#">Something</a></li>
                                    <li><a href="#">Another action</a></li>
                                    <li><a href="#">Something else here</a></li>

                                    <li><a href="#">Sonething else</a></li>

                                    <li><a href="#">Something</a></li>
                                </ul>
                            </div>
                            <div class="col-4">
                                <ul class="multi-column-dropdown">
                                    <li><a href="#">Something</a></li>
                                    <li><a href="#">Another action</a></li>
                                    <li><a href="#">Something else here</a></li>

                                    <li><a href="#">Something</a></li>

                                    <li><a href="#">Something else</a></li>
                                </ul>
                            </div>
                            <div class="col-4">
                                <ul class="multi-column-dropdown">
                                    <li><a href="#">Action</a></li>
                                    <li><a href="#">Another action</a></li>
                                    <li><a href="#">Something else here</a></li>

                                    <li><a href="#">Something</a></li>

                                    <li><a href="#">Something</a></li>
                                </ul>
                            </div>
                        </div>
                    </ul>
                </li>
            </ul>

        </div> <!-- container-fluid.// -->
</nav>
