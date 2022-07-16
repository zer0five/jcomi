<%-- 
    Document   : profile_info
    Created on : Jul 16, 2022, 5:09:55 PM
    Author     : Thai Binh Quoc Viet-CE160378
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/stylesheet.jsp"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Profile</title>
    </head>
    <body>
        <jsp:include page="/navbar/nav.jsp"/>
        <section>
            <div class="container">
                <div class=" p-5">
                    <div class="row">
                        <div class="col-lg-8 mx-auto ">
                            <ul class="nav nav-tabs bg-light">
                                <li class="nav-item">
                                    <a class="nav-link active fw-bold" aria-current="page" href="profile.html">Profile</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link text-dark" href="changePassword.html">Change Password</a>
                                </li>
                            </ul>
                            <div class="border rounded-bottom shadow-lg p-3">
                                <h5 class="text-dark fw-bold text-truncate text-decoration-none">Account Information</h5>
                                <img class="rounded-circle img-thumbnail d-block text-center mx-auto" width="128px"
                                     src="https://via.placeholder.com/128" alt="">
                                <div class="d-flex justify-content-center m-3">
                                    <a href="" class="btn btn-dark rounded-pill "> Change Avatar </a>
                                </div>
                                <form action="${pageContext.request.contextPath}/authentication/login" method="post"
                                      class="row g-3 needs-validation p-3" novalidate>
                                    <div class="input-group col-12 mb-2">
                                        <label for="email" class="input-group-text rounded-pill rounded-end bg-light px-3">
                                            <i class="bi bi-envelope"></i>
                                        </label>
                                        <input class="form-control rounded-pill rounded-start" type="email" name="email"
                                               id="email" value="abc@xyz.com" placeholder="Your Email">

                                    </div>
                                    <div class="input-group col-12">
                                        <label for="display-name"
                                               class="input-group-text rounded-pill rounded-end bg-light px-3">
                                            <i class="bi bi-person-badge" aria-hidden="true"></i>
                                        </label>
                                        <input class="form-control rounded-pill rounded-start" type="text"
                                               name="display-name" id="display-name" value="Name" placeholder="Display Name">
                                    </div>
                                    <div class="col-12">
                                        <button class="btn btn-success rounded-pill text-center text-light float-end me-3"
                                                type="submit">
                                            <i class="bi bi-box-arrow-in-right me-1" aria-hidden="true"></i>
                                            Save
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
   
</body>
</html>
