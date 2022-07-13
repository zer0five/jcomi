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

    /* ============ desktop view .end// ============ */
</style>
<nav class="navbar navbar-expand-sm navbar-light bg-light">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">
            <img src="${pageContext.request.contextPath}/assets/logo.svg" alt="logo" style="width: 160px">
        </a>

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

</nav>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="#">Brand</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#main_nav"
                aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="main_nav">
            <ul class="navbar-nav">
                <li class="nav-item active"> <a class="nav-link" href="#">Home </a> </li>
                <li class="nav-item"><a class="nav-link" href="#"> About </a></li>
                <li class="nav-item"><a class="nav-link" href="#"> Services </a></li>
                <li class="nav-item dropdown">
                    <a class="nav-link  dropdown-toggle" href="#" data-bs-toggle="dropdown"> Hover me </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="#"> Submenu item 1</a></li>
                        <li><a class="dropdown-item" href="#"> Submenu item 2 </a></li>
                        <li><a class="dropdown-item" href="#"> Submenu item 3 </a></li>
                    </ul>
                </li>
            </ul>

        </div> <!-- navbar-collapse.// -->
    </div> <!-- container-fluid.// -->
</nav>          