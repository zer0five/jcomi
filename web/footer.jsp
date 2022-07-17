<%--
    Document   : footer
    Created on : Jul 15, 2022, 3:39:57 PM
    Author     : Thai Binh Quoc Viet-CE160378
--%>

<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<style type="tex/css">
    li a {
        text-decoration: none;
    }
</style>
<section class="footer">
    <!-- Footer -->
    <footer class="mt-auto bg-light pt-3 ">
        <!-- Grid container -->
        <div class="container-lg p-4">
            <!--Grid row-->
            <div class="row">
                <!--Grid column-->
                <div class="col-lg-6 col-md-12 mb-4 mb-md-0">
                    <a href="${pageContext.request.contextPath}/"> <img src="${pageContext.request.contextPath}/assets/logo.svg" alt="logo" style="width: 200px"></a>

                    <p class="mt-3 " style="text-align: justify">
                        All information and images on the website are collected from the Internet. We do not own or be responsible for any information on this website. If it affects any individual or organization, when requested, we will review and remove it immediately.
                    </p>
                </div>
                <!--Grid column-->

                <!--Grid column-->
                <div class="col-lg-3 col-md-6 mb-4 mb-md-0">
                    <h5 class="text-uppercase pb-4">Quick access</h5>

                    <ul class="list-unstyled mb-0">
                        <li>
                            <a href="${pageContext.request.contextPath}/profile">Profile</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/comic/search">Search comic</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/comic/add">Add comic</a>
                        </li>

                    </ul>
                </div>
                <!--Grid column-->

                <!--Grid column-->
                <div class="col-lg-3 col-md-6 mb-4 mb-md-0">
                    <h5 class="text-uppercase">Contact Us</h5>
                    <ul class="list-unstyled mb-0 ">
                        <li>
                            <a href="mailto:zerofive@gmail.com" class="text-decoration-none"><i class="bi bi-envelope me-2"></i> zerofive@gmail.com</a>                    
                        </li>
                        <li>
                            <a href="https://github.com/zer0five" class="text-decoration-none"><i class="bi bi-github me-2"></i> Github</a>
                        </li>
                    </ul>
                </div>
                <!--Grid column-->
            </div>
            <!--Grid row-->
        </div>
        <!-- Grid container -->

        <!-- Copyright -->
        <div class="text-center text-white p-3  bg-secondary">
            Copyright © <%=Calendar.getInstance().get(Calendar.YEAR)%>
            <a class="text-white" href="#">ZeroFive</a>
        </div>
        <!-- Copyright -->
    </footer>
    <!-- Footer -->
</section>
