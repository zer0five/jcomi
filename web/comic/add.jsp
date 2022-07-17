<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">

<head>
    <title>Title</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS v5.2.0-beta1 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
          integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

</head>

<body class="bg-white">
<div class="container">
    <div class="row mt-3">
        <div class="col-10 mx-auto border-start border-4 border-danger ps-3 text-danger">
            <h2>Create Comic</h2>
        </div>
    </div>
    <div class="row mt-4">
        <form id="create-comic-form" method="post" class="col-10 rounded rounded-3 shadow p-5 bg-light mx-auto">
            <div class="mb-3">
                <img id="preview-cover-image" src="https://via.placeholder.com/190x247"
                     class="shadow d-block mx-auto img-thumbnail" alt="Cover image" width="190" height="247"/>
            </div>
            <input type="text" class="form-control mb-3" id="name" name="name" placeholder="Name" required>
            <label for="cover-image" class="form-label mb-1">Cover image</label>
            <input type="file" class="form-control mb-3" name="cover-image" id="cover-image"
                   placeholder="Cover image" aria-describedby="fileHelpId" accept="image/*"/>
            <input type="text" class="form-control mb-3" id="alt-name" name="alt-name" placeholder="Alternative name">
            <input type="text" class="form-control mb-3" id="author" name="author" placeholder="Author">
            <textarea class="form-control mb-3" id="description" name="description" placeholder="Description"></textarea>
            <jsp:useBean id="genres" class="org.jcomi.entity.genre.GenreDataAccess" scope="request"/>
            <label for="cover-image" class="form-label mb-1">Genres</label>
            <div class="row mb-3">
                <c:set var="i" value="0"/>
                <c:forEach items="${genres.all}" var="genre">
                    <div class="col-12 col-sm-6 col-md-4 col-lg-3">
                        <div class="form-check" data-bs-toggle="tooltip" title="${genre.description}">
                            <input class="form-check-input" type="checkbox" value="${genre.id}" id="genre-${genre.id}"
                                   name="genres[${i}]">
                            <label class="form-check-label" for="genre-${genre.id}">
                                    ${genre.genre}
                            </label>
                        </div>
                    </div>
                    <c:set var="i" value="${i + 1}"/>
                </c:forEach>
            </div>
            <button type="submit" class="btn btn-primary mt-3">Create</button>
        </form>
    </div>
</div>
<script>
    let input = document.getElementById('cover-image');
    let previewCoverImage = document.getElementById('preview-cover-image');
    input.onchange = function () {
        if (!this.files || !this.files[0]) {
            return;
        }
        input = this;
        let reader = new FileReader();
        reader.onload = function (e) {
            let image = new Image();
            image.src = e.target.result;
            image.onload = function () {
                let width = image.width;
                let height = image.height;
                let x = 0;
                let y = 0;
                if (width < 190 || height < 247) {
                    alert("Image size must be at least 192x247");
                    return;
                }
                if (width % 190 !== 0 || height % 247 !== 0) {
                    x = (width % 190) / 2;
                    y = (height % 247) / 2;
                }
                // convert to webp and resize if necessary
                let canvas = document.createElement("canvas");
                canvas.width = 190;
                canvas.height = 247;
                let ctx = canvas.getContext("2d");
                ctx.drawImage(image, -x, -y, width, height);
                previewCoverImage.src = canvas.toDataURL("image/webp");
            };
        };
        reader.readAsDataURL(this.files[0]);
    };
    document.getElementById("create-comic-form").onsubmit = function (e) {
        e.preventDefault();
        let formData = new FormData(this);
        let blob = dataURItoBlob(previewCoverImage.src);
        formData.set("cover-image", blob);
        let xhr = new XMLHttpRequest();
        xhr.onload = function () {
            if (xhr.status === 200) {
                alert("Comic created successfully");
            } else {
                alert("Error: " + xhr.status);
            }
        };
        xhr.open('POST', '${pageContext.request.contextPath}/comic/add');
        xhr.send(formData);
    };

    function dataURItoBlob(dataURI) {
        // convert base64 to raw binary data held in a string
        var byteString = atob(dataURI.split(',')[1]);

        // separate out the mime component
        var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];

        // write the bytes of the string to an ArrayBuffer
        var arrayBuffer = new ArrayBuffer(byteString.length);
        var _ia = new Uint8Array(arrayBuffer);
        for (var i = 0; i < byteString.length; i++) {
            _ia[i] = byteString.charCodeAt(i);
        }

        var dataView = new DataView(arrayBuffer);
        return new Blob([dataView], { type: mimeString });
    }
</script>
<!-- sha384-kjU+l4N0Yf4ZOJErLsIcvOU2qSb74wXpOhqTvwVx3OElZRweTnQ6d31fXEoRD1Jy -->
<!-- Bootstrap JavaScript Libraries -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.5/dist/umd/popper.min.js"
        integrity="sha384-Xe+8cL9oJa6tN/veChSP7q+mnSPaj5Bcu9mPX5F5xIGE0DVittaqT5lorf0EI7Vk"
        crossorigin="anonymous"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.min.js"
        integrity="sha384-kjU+l4N0Yf4ZOJErLsIcvOU2qSb74wXpOhqTvwVx3OElZRweTnQ6d31fXEoRD1Jy"
        crossorigin="anonymous"></script>
</body>

</html>
