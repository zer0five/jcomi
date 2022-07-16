<%-- 
    Document   : footer
    Created on : Jul 15, 2022, 3:39:57 PM
    Author     : Thai Binh Quoc Viet-CE160378
--%>

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
                    <a href="index.jsp"> <img src="${pageContext.request.contextPath}/assets/logo.svg" alt="logo" style="width: 200px"></a>

                    <p class="mt-3">
                        Mọi thông tin và hình ảnh trên website đều được sưu tầm trên Internet. Chúng tôi không sở hữu
                        hay chịu trách nhiệm bất kỳ thông tin nào trên web này. Nếu làm ảnh hưởng đến cá nhân hay tổ
                        chức nào, khi được yêu cầu, chúng tôi sẽ xem xét và gỡ bỏ ngay lập tức.
                    </p>
                </div>
                <!--Grid column-->

                <!--Grid column-->
                <div class="col-lg-3 col-md-6 mb-4 mb-md-0">
                    <h5 class="text-uppercase pb-4">Quick access</h5>

                    <ul class="list-unstyled mb-0 ">
                        <li>
                            <a href="">Profile</a>
                        </li>
                        <li>
                            <a href="search_comic.jsp">Search comic</a>
                        </li>
                        <li>
                            <a href="#">Upload comic</a>
                        </li>

                    </ul>
                </div>
                <!--Grid column-->

                <!--Grid column-->
                <div class="col-lg-3 col-md-6 mb-4 mb-md-0">
                    <h5 class="text-uppercase">Contact Us</h5>
                    <p><i class="fa fa-envelope-o mr-3"></i> zerofive@gmail.com</p>
                </div>
                <!--Grid column-->
            </div>
            <!--Grid row-->
        </div>
        <!-- Grid container -->

        <!-- Copyright -->
        <div class="text-center text-white p-3  bg-secondary">
            © 2022 Copyright:
            <a class="text-white" href="#">Group 5 zerofive</a>
        </div>
        <!-- Copyright -->
    </footer>
    <!-- Footer -->
</section>
