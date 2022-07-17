<%-- 
    Document   : change_password
    Created on : Jul 17, 2022, 11:06:00 AM
    Author     : Thai Binh Quoc Viet-CE160378
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="/stylesheet.jsp"/>
    </head>
    <body>
        <jsp:include page="/navbar/nav.jsp"/>
        <section>
            <div class="container">
                <div class=" p-5">
                    <div class="row">
                        <div class="col-lg-8 mx-auto ">
                            <ul class="nav nav-tabs bg-transparent">
                                <li class="nav-item">
                                    <a class="nav-link text-dark" aria-current="page" href="profile_info.jsp">Profile</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link  active fw-bold" href="change_password.jsp">Change Password</a>
                                </li>
                            </ul>
                            <div class="border border-top-0 rounded-bottom shadow-lg p-3">
                                <h5 class="text-dark fw-bold text-truncate text-decoration-none">Change Password</h5>
                                <form action="${pageContext.request.contextPath}/authentication/login" method="post"
                                      class="row g-3 needs-validation p-3" novalidate>
                                    <div class="input-group col-12 mb-3">
                                        <label for="password"
                                               class="input-group-text rounded-pill rounded-end bg-light px-3">
                                            <i class="bi bi-key" aria-hidden="true"></i>
                                        </label>
                                        <input class="form-control rounded-pill rounded-start" type="password"
                                               name="password" id="password" placeholder="Current Password" required>
                                    </div>
                                    <div class="input-group col-12">
                                        <label for="display-name"
                                               class="input-group-text rounded-pill rounded-end bg-light px-3">
                                            <i class="bi bi-key-fill" aria-hidden="true"></i>
                                        </label>
                                        <input class="form-control rounded-pill rounded-start" type="password"
                                               name="new-password" id="new-password" placeholder="New Password">
                                    </div>
                                    <div class="input-group col-12">
                                        <label for="display-name"
                                               class="input-group-text rounded-pill rounded-end bg-light px-3">
                                            <i class="bi bi-key-fill" aria-hidden="true"></i>
                                        </label>
                                        <input class="form-control rounded-pill rounded-start" type="text"
                                               name="confirm-password" id="confirm-password" placeholder="Confirm Password">
                                    </div>
                                </form>
                                <form action="" method="post" class="row g-3 needs-validation p-3" novalidate>
                                    <div class="col-12">
                                        <button class="btn btn-danger rounded-pill text-center text-light float-end me-3"
                                                type="submit">
                                            <i class="bi bi-box-arrow-in-right me-1" aria-hidden="true"></i>
                                            Change
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <jsp:include page="footer.jsp"/>
    <jsp:include page="/script.jsp"/>
</body>
</html>
