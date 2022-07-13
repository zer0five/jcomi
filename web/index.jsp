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
        <div class="container py-3">
            <a href="${pageContext.request.contextPath}/database-connection-test">Test Database Connection</a>
        </div>
        <jsp:include page="/script.jsp"/>
    </body>
</html>