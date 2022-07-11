<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="register-modal" tabindex="-1" role="dialog" aria-labelledby="modelTitleId"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable" role="document">
        <div class="modal-content">
            <div class="modal-header bg-info">
                <h5 class="modal-title text-light">
                    <i class="bi bi-signpost me-3" aria-hidden="true"></i>
                    Register
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"
                        aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <jsp:include page="register-form.jsp"/>
            </div>
            <div class="modal-footer bg-light">
                Already have an account?
                <a href="javascript:void(0)" data-bs-toggle="modal" data-bs-target="#login-modal">Login</a>
            </div>
        </div>
    </div>
</div>