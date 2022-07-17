<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>Add chapter</title>
    <jsp:include page="/stylesheet.jsp"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
</head>
<body class="bg-white">
<jsp:include page="/navbar/nav.jsp"/>
<div class="container">
    <div class="row mt-3">
        <div class="col-10 mx-auto border-start border-4 border-danger ps-3 text-danger">
            <h2>Add chapter</h2>
        </div>
    </div>
    <div class="row mt-4">
        <form id="create-comic-form" method="post" class="col-10 rounded rounded-3 shadow p-5 bg-light mx-auto">
            <div id="preview">
            </div>
            <jsp:useBean id="comicData" scope="request" class="org.jcomi.entity.comic.ComicDataAccess"/>
            <c:if test="${param.id == null}">
                <div class="alert alert-danger">
                    <strong>Error!</strong> Comic not found.
                </div>
                <c:set var="disabled" value="disabled"/>
                <c:set var="name" value=""/>
            </c:if>
            <c:if test="${param.id != null}">
                <c:set var="disabled" value=""/>
                <c:set var="comic" value="${comicData.getOne(param.id)}"/>
                <c:set var="name" value="${comic.get().getName()}"/>
            </c:if>
            <label for="name">Comic name</label>
            <input type="text" id="name" class="form-control mb-3" disabled value="${name}"/>
            <input type="hidden" value="${param.id}" name="id"/>
            <input type="text" class="form-control mb-3" id="name" name="title" placeholder="Title" ${disabled}>
            <input type="number" class="form-control mb-3" name="ordinal" id="ordinal" aria-describedby="helpId"
                   placeholder="Chapter number" required ${disabled}/>

            <label for="images">Chapter pages</label>
            <input type="file" multiple class="form-control mt-1 mb-3" name="images" id="images"
                   placeholder="images" aria-describedby="fileHelpId" accept="image/*" ${disabled}/>

            <button type="submit" class="btn btn-primary mt-3" ${disabled}>Create</button>
        </form>
    </div>
</div>
<script>
    let input = document.getElementById('images');
    let preview = document.getElementById("preview");
    input.onchange = function () {
        preview.innerHTML = '';
        input = this;
        if (!this.files || !this.files[0]) {
            return;
        }
        for (let file of this.files) {
            let previewCoverImage = document.createElement("img");
            previewCoverImage.className = "d-block mx-auto";
            let reader = new FileReader();
            reader.onload = function (e) {
                let image = new Image();
                image.src = e.target.result;
                image.onload = function () {
                    // convert to webp and resize if necessary
                    let canvas = document.createElement("canvas");
                    canvas.width = image.width;
                    canvas.height = image.height;
                    let ctx = canvas.getContext("2d");
                    ctx.drawImage(image, 0, 0);
                    let dataURL = canvas.toDataURL("image/webp");
                    previewCoverImage.src = dataURL;
                };
                preview.appendChild(previewCoverImage);
            };
            reader.readAsDataURL(file);
        }
    };
    document.getElementById("create-comic-form").onsubmit = function (e) {
        e.preventDefault();
        let preview = document.getElementById("preview");
        let formData = new FormData(this);
        let xhr = new XMLHttpRequest();
        formData.delete('images');
        let i = 0;
        for (let previewCoverImage of preview.children) {
            console.log("Hình" + i++ + previewCoverImage + "\n");
            let blob = dataURItoBlob(previewCoverImage.src);
            formData.append('images[' + i + ']', blob);
        }
        xhr.onload = function () {
            if (xhr.status === 200) {
                alert("Comic created successfully");
            } else {
                alert("Error: " + xhr.status);
            }
        };
        xhr.open('POST', '${pageContext.request.contextPath}/comic/chapter/add');


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
</div>
<jsp:include page="/script.jsp"/>
</body>
</html>
