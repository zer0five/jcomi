<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Home</title>
        <jsp:include page="/stylesheet.jsp"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    </head>
    <body>
        <jsp:include page="/navbar/nav.jsp"/>
        <jsp:include page="back_to_top_btn.jsp"/>

        <!-- body section -->
        <section class="container py-3">

            <!-- body section -->
            <div class="container ">
                <!-- slide show-->

                <div class="carousel slide" data-bs-ride="carousel" data-bs-interval="1500">
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img src="https://dummyimage.com/550x250/e077e0/f7fafa" class="d-block w-100 img-caeousel"
                                 alt="...">
                        </div>
                        <div class="carousel-item">
                            <img src="https://dummyimage.com/550x250/5faed9/f7fafa" class="d-block w-100 img-caeousel"
                                 alt="...">
                        </div>
                        <div class="carousel-item">
                            <img src="https://dummyimage.com/550x250/d4e88e/f7fafa" class="d-block w-100 img-caeousel"
                                 alt="...">
                        </div>
                    </div>
                </div>
            </div>                
            <!-- end slide show-->
            <!-- card  comic-->

            <div class="container pt-3">
                <!-- updated  text-->

                <div class="section-heading mb-4 font-12x font-bold pl-1 grid-heading py-1">
                    <div class=" d-flex align-items-center">
                        <h4 class="fw-bold border-danger border-start border-3 ps-3 text-danger"> Updated Comic</h4>
                    </div>
                </div>
                <!-- end updated  text-->

                <div>
                    <div class="row text-center">
                        <div class="col-md-3 col-sm-6 comic_frame">
                            <jsp:include page="comic_card.jsp"/>

                        </div>
                        <div class="col-md-3 col-sm-6 comic_frame">
                            <jsp:include page="comic_card.jsp"/>

                        </div>
                        <div class="col-md-3 col-sm-6 comic_frame">
                            <jsp:include page="comic_card.jsp"/>

                        </div>
                        <div class="col-md-3 col-sm-6 comic_frame">
                            <jsp:include page="comic_card.jsp"/>

                        </div>
                    </div>
                </div>

                <div>
                    <div class="row text-center">
                        <div class="col-md-3 col-sm-6 comic_frame">
                            <jsp:include page="comic_card.jsp"/>

                        </div>
                        <div class="col-md-3 col-sm-6 comic_frame">
                            <jsp:include page="comic_card.jsp"/>

                        </div>
                        <div class="col-md-3 col-sm-6 comic_frame">
                            <jsp:include page="comic_card.jsp"/>

                        </div>
                        <div class="col-md-3 col-sm-6 comic_frame">
                            <jsp:include page="comic_card.jsp"/>

                        </div>
                    </div>
                </div>
            </div>


        </section>
        <!-- end body section -->

        <jsp:include page="footer.jsp"/>
        <jsp:include page="/script.jsp"/>

    </body>
</html>