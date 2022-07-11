<%--
  Created by IntelliJ IDEA.
  User: kazoku
  Date: 7/10/22
  Time: 5:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="modelTitleId" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable" role="document">
        <div class="modal-content">
            <div class="modal-header bg-success">
                <h5 class="modal-title text-light">
                    <i class="bi bi-box-arrow-in-right me-3" aria-hidden="true"></i>
                    Login
                </h5>
                <button type="button" class="btn-close btn-close-white"
                        data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <jsp:include page="login-form.jsp"/>
            </div>
            <div class="modal-footer bg-light">
                Didn't have an account?
                <a href="javascript:void(0)" data-bs-toggle="modal" data-bs-target="#register-modal">Register</a>
            </div>
        </div>
    </div>
</div>
