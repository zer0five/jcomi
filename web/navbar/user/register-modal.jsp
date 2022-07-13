<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="register-modal" tabindex="-1" role="dialog" aria-labelledby="modelTitleId"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable" role="document">
        <div class="modal-content border-0 bg-transparent">
            <div class="modal-header bg-info bg-gradient rounded-pill shadow mx-3" style="z-index: 100; transform: translateY(50%)">
                <h5 class="modal-title text-light">
                    <i class="bi bi-signpost me-3" aria-hidden="true"></i>
                    Register
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"
                        aria-label="Close"></button>
            </div>
            <div class="modal-body bg-white" style="border-radius: 12px 12px 0 0">
                <div class="my-5"></div>
                <jsp:include page="register-form.jsp"/>
            </div>
            <div class="modal-footer bg-light">
                Already have an account?
                <a href="javascript:void(0)" data-bs-toggle="modal" data-bs-target="#login-modal">Login</a>
            </div>
        </div>
    </div>
</div>