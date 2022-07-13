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
        <div class="modal-content border-0 bg-transparent">
            <div class="modal-header bg-success bg-gradient rounded-pill shadow mx-3" style="z-index: 100; transform: translateY(50%)">
                <h5 class="modal-title text-light">
                    <i class="bi bi-box-arrow-in-right me-3" aria-hidden="true"></i>
                    Login
                </h5>
                <button type="button" class="btn-close btn-close-white"
                        data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body bg-white" style="border-radius: 12px 12px 0 0">
                <div class="my-5"></div>
                <jsp:include page="login-form.jsp"/>
            </div>
            <div class="modal-footer bg-light">
                Didn't have an account?
                <a href="javascript:void(0)" data-bs-toggle="modal" data-bs-target="#register-modal">Register</a>
            </div>
        </div>
    </div>
</div>
