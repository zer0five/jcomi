<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
    <meta charset="utf-8">
    <title>${requestScope.comic.name} - Chapter ${requestScope.chapter.ordinal} ${requestScope.chapter.title}</title>
    <jsp:include page="/stylesheet.jsp"/>
    <style type="text/css">
        html, body {
            overflow: auto;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            margin: 0;
            padding: 0;
        }

        /*.center {*/
        /*    display: block;*/
        /*    position: absolute;*/
        /*    margin: 0;*/
        /*    top: 50%;*/
        /*    left: 50%;*/
        /*    transform: translate(-50%, -50%);*/
        /*}*/

        .img-container {
            position: relative;
            background: #dfdfdf;
            margin: 0 auto;
            max-width: 800px;
        }

        .img-container > img {
            max-width: 800px;
        }

        /*#main-scrollbar {*/
        /*    overflow: hidden;*/
        /*    width: 100%;*/
        /*    height: 100%;*/
        /*}*/

        /*.scrollbar-track {*/
        /*    -webkit-transition-duration: 225ms !important;*/
        /*    -o-transition-duration: 225ms !important;*/
        /*    transition-duration: 225ms !important;*/
        /*    -webkit-transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1) !important;*/
        /*    -o-transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1) !important;*/
        /*    transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1) !important;*/
        /*    background-color: transparent !important;*/
        /*    z-index: 9999 !important;*/
        /*}*/

        /*.scrollbar-track .scrollbar-thumb {*/
        /*    background-color: rgba(255, 177, 62, 0.80) !important;*/
        /*}*/
    </style>
</head>
<body>
<jsp:include page="/navbar/nav.jsp"/>
<main id="main-scrollbar" data-scrollbar="true" class="my-3">
    <div class="scroll-content">
        <jsp:useBean id="chapterData" class="org.jcomi.entity.comic.chapter.ChapterDataAccess"/>

        <div id="comic" class="d-block">
            <c:set var="list" value="${chapterData.getPages(requestScope.chapter.id)}"/>
            <c:if test="${list.isEmpty()}">
                <div class="center">
                    <h1>No pages found</h1>
                </div>
            </c:if>
            <c:if test="${!list.isEmpty()}">
                <c:forEach var="page" items="${list}">
                    <div class="img-container" style="width: 800px; height: 1000px">
                        <img id="pic-${page.ordinal}" class="d-block mx-auto my-auto" alt="Picture ${page.ordinal}"
                             src="${pageContext.request.contextPath}/assets/loading.svg"
                             data-image="${pageContext.request.contextPath}/${page.imageUrl}"/>
                    </div>
                </c:forEach>
            </c:if>
        </div>
    </div>
    <%--    <div class="scrollbar-track scrollbar-track-x">--%>
    <%--        <div class="scrollbar-thumb scrollbar-thumb-x"></div>--%>
    <%--    </div>--%>
    <%--    <div class="scrollbar-track scrollbar-track-y">--%>
    <%--        <div class="scrollbar-thumb scrollbar-thumb-y"></div>--%>
    <%--    </div>--%>
</main>
<%--<script src="${pageContext.request.contextPath}/js/smooth-scrollbar.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/js/plugins/overscroll.js"></script>--%>
<%--<script type="text/javascript">--%>
<%--    var Scrollbar = window.Scrollbar;--%>
<%--    Scrollbar.use(window.OverscrollPlugin);--%>
<%--    Scrollbar.initAll({damping:.08,thumbMinSize:20,renderByPixels:true,continuousScrolling:true,alwaysShowTracks:false,delegateTo:null,plugins:{overscroll:true}});--%>
<%--</script>--%>
<jsp:include page="/script.jsp"/>
<script src="${pageContext.request.contextPath}/js/images-loaded.min.js"></script>
<script type="text/javascript">
    var counter = 1;
    <%--function addNewPic(container, url) {--%>
    <%--    let img = $(`<img id="pic-\${counter}" class="center" src="${pageContext.request.contextPath}/assets/loading.svg" data-image="\${url}" alt="\${counter}"/>`);--%>
    <%--    let imgContainer = $('<div class="img-container" style="width: 800px; height: 800px;"></div>');--%>
    <%--    imgContainer.append(img);--%>
    <%--    container.append(imgContainer);--%>
    <%--    counter++;--%>
    <%--    return img;--%>
    <%--}--%>
    $(document).ready(function () {
        $('.img-container img').each(function() {
            let img = $(this);
            execute(img);
        });
    });


    function execute(element) {
        let imageUrl = element.attr("data-image");
        $.ajax({
            type: "HEAD",
            url: imageUrl,
        })
            .done((message, text, request) => {
                let parent = element.parent();
                let width = request.getResponseHeader("width") ?? "800px";
                let height = request.getResponseHeader("height") ?? null;
                parent.width(width);
                if (height) {
                    parent.height(height);
                }
                loadImage(element, imageUrl);
            })
            .fail(() => imageError(element));
    }

    function loadImage(element, imageUrl) {
        $.ajax({
            cache: true,
            url: imageUrl,
            xhr: function () {
                var xhr = new XMLHttpRequest();
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 2) {
                        if (xhr.status === 200) {
                            xhr.responseType = "blob";
                        } else {
                            xhr.responseType = "text";
                        }
                    }
                };
                return xhr;
            },
        })
            .done(data => {
                console.log("Image loaded");
                let blob = new Blob([data], {
                    type: "image/webp",
                });
                let src = window.URL.createObjectURL(blob);
                element.html(data)
                    .imagesLoaded()
                    .then(() => element.attr("src", src));
            })
            .fail(() => element.attr("src", "assets/not-found.svg"));
    }
</script>
</body>
</html>

