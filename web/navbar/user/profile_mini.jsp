<%-- 
    Document   : profile_mini
    Created on : Jul 16, 2022, 4:19:56 PM
    Author     : Thai Binh Quoc Viet-CE160378
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="d-flex mt-3 mt-sm-0">
    <div class="dropdown nav-item">
        <div class="user-select-none" style="cursor: pointer" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
            <img class="rounded-circle img-thumbnail me-2" width="56px"
                 src="${sessionScope.user.getAvatar()}" alt="profile-img" />
            Hello, ${sessionScope.user.displayName}
        </div>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile_info.jsp"><i class="bi bi-person-circle me-2"></i>Profile</a>
            </li>
            <li>
                <hr class="dropdown-divider">
            </li>
            <li><a class="dropdown-item" href="#"><i class="bi bi-bookmark me-2"></i>Bookmark</a></li>
            <li><a class="dropdown-item" href="#"><i class="bi bi-clock-history me-2"></i>History</a>
            </li>
            <li>
                <hr class="dropdown-divider">
            </li>
            <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/authentication/logout"><i
                        class="bi bi-box-arrow-left me-2"></i>Logout</a></li>
        </ul>
    </div>
</div>
