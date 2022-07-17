<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="card bg-light text-dark border-0 bg-transparent mb-3" data-bs-toggle="popover" title="${param.title}"
     data-bs-trigger="hover focus" data-bs-content="${param.description}">
    <a href="${param.url}">
        <img class="card-img-top rounded-3 shadow" src="${param.cover}" alt="${param.title}">
    </a>
    <div class="card-body text-center p-1">
        <a href="${param.url}"
           class="text-dark text-capitalize fw-bold d-block text-truncate text-decoration-none">${param.title}</a>
    </div>
</div>
