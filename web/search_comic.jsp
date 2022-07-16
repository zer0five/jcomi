<%-- 
    Document   : search_comic
    Created on : Jul 15, 2022, 4:46:38 PM
    Author     : Thai Binh Quoc Viet-CE160378
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Search comic</title>
        <jsp:include page="/stylesheet.jsp"/>
    </head>
    <body>
        <jsp:include page="/navbar/nav.jsp"/>
        <section class="search-comic pt-3 pb-5">
            <div class="main-homepage container ">
                <div class="homepage-tags fw-bold inner d-flex align-items-center pb-3">
                    <h2 class="fw-bold border-danger border-start border-3 ps-3"> Advanced Search</h2>
                </div>
                <div class="border rounded shadow-lg p-5">
                    <div class="form-group row list-genre">
                        <div class="label-search fw-bold">
                            <h4>Story genre</h4> 
                        </div>
                        <div class="row">
                            <div class="genre-item col-lg-3 col-md-3 col-sm-3"
                                 title="This genre typically has content concerning fighting, violence, chaos, with fast action">
                                <input type="checkbox">
                                <span class="icon checkbox">Action</span>
                            </div>
                            <div class="genre-item col-lg-3 col-md-3 col-sm-3"
                                 title="Genre that deals with sensitive issues, 17+ only">
                                <input type="checkbox">
                                <span class="icon checkbox">Adult</span>
                            </div>
                            <div class="genre-item col-lg-3 col-md-3 col-sm-3"
                                 title="Adventure genre, adventure, usually the journey of the characters">
                                <input type="checkbox">
                                <span class="icon checkbox">Adventure</span>
                            </div>
                        </div>
                        <div class="row">
                            <div class="genre-item col-lg-3 col-md-3 col-sm-3"
                                 title="The story has been adapted into an Anime movie">
                                <input type="checkbox">
                                <span class="icon checkbox">Anime</span>
                            </div>

                            <div class="genre-item col-lg-3 col-md-3 col-sm-3"
                                 title="Genre with pure and touching content, typically with amusing details, mild conflicts">
                                <input type="checkbox">
                                <span class="icon checkbox">Comedy</span>
                            </div>
                            <div class="genre-item col-lg-3 col-md-3 col-sm-3" title="Comics Europe and America">
                                <input type="checkbox">
                                <span class="icon checkbox">Comic</span>
                            </div>
                        </div>

                        <div class="row">
                            <div class="genre-item col-lg-3 col-md-3 col-sm-3" title="Demons">
                                <input type="checkbox">
                                <span class="icon checkbox">Demons</span>
                            </div>
                            <div class="genre-item col-lg-3 col-md-3 col-sm-3" title="Genre investigation, detective.">
                                <input type="checkbox">
                                <span class="icon checkbox">Detective</span>
                            </div>
                            <div class="genre-item col-lg-3 col-md-3 col-sm-3"
                                 title="Genre of fan fiction or presumably even Mangakas that are totally different from the first author. Doujinshi creators typically draw on original characters to put in writing stories in line with their preferences.">
                                <input type="checkbox">
                                <span class="icon checkbox">Drama</span>
                            </div>
                        </div>

                        <div class="row">
                            <div class="genre-item col-lg-3 col-md-3 col-sm-3"
                                 title="There are often sensitive situations to attract viewers">
                                <input type="checkbox">
                                <span class="icon checkbox">Ecchi</span>
                            </div>
                            <div class="genre-item col-lg-3 col-md-3 col-sm-3"
                                 title="Genre comes from wealthy imagination, from magic to dream global even fairy tales">
                                <input type="checkbox">
                                <span class="icon checkbox">Fantasy</span>
                            </div>
                            <div class="genre-item col-lg-3 col-md-3 col-sm-3"
                                 title="Is a genre in which the gender of the character is confused: male becomes female, female becomes male...">
                                <input type="checkbox">
                                <span class="icon checkbox">Gender Bender</span>
                            </div>
                        </div>
                    </div>
                    <div>
                        <div class="form-group text-md-center py-3">
                            <a class="btn btn-primary btn-sm m-sm-3 px-3 ">Reset</a>
                        </div>
                    </div>

                    <div class="form-group row option pb-4">
                        <div class="label-search col-lg-3 col-sm-2 text-sm-center ">Country</div>
                        <select class="mdb-select sm-form col-lg-3 col-sm-2 border rounded">
                            <option value="1">all</option>
                            <option value="2">China</option>
                            <option value="3">Korea</option>
                            <option value="4">Viet Nam</option>
                            <option value="3">Japan</option>
                        </select>
                        <div class="label-search col-lg-3 col-sm-2 text-sm-center">Status</div>
                        <select class="mdb-select sm-form col-lg-3 col-sm-2 border rounded">
                            <option value="1">all</option>
                            <option value="2">Updating</option>
                            <option value="3">Complete</option>
                        </select>

                    </div>
                    <div class="form-group row option">
                        <div class="label-search col-lg-3 col-sm-2 text-sm-center">Number of chapters</div>
                        <select class="mdb-select sm-form col-lg-3 col-sm-2 border rounded ">
                            <option value="1"> > 0</option>
                            <option value="2"> >= 50</option>
                            <option value="3"> >= 100</option>
                            <option value="4"> >= 200</option>
                            <option value="5"> >= 300</option>
                        </select>
                        <div class="label-search col-lg-3 col-sm-2 text-sm-center">Sort</div>
                        <select class="mdb-select sm-form col-lg-3 col-sm-2 border rounded ">
                            <option value="1">Posting date descending</option>
                            <option value="2">Posting date increment</option>
                            <option value="3">Update date descending</option>
                            <option value="4">Update date increment</option>
                            <option value="5">Views are decreasing</option>
                            <option value="6">Views are increment</option>
                        </select>
                    </div>

                    <div class="form-group text-sm-center pt-3">
                        <a class="btn btn-success btn-sm m-sm-3 px-3">Search</a>
                    </div>

                </div>
            </div>
        </section>
        <jsp:include page="/footer/footer.jsp"/>
        <jsp:include page="/script.jsp"/>
    </body>
</html>
