<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="mx-2 mt-lg-0 mt-sm-2">
    <button type="button" class="btn btn-outline-success rounded-pill shadow-sm" data-bs-toggle="modal"
            data-bs-target="#login-modal">
        Login
    </button>
    <jsp:include page="login-modal.jsp"/>
</div>
<div class="mt-lg-0 mt-sm-2">
    <button type="button" class="btn btn-outline-info rounded-pill shadow-sm" data-bs-toggle="modal"
            data-bs-target="#register-modal">
        Register
    </button>
    <jsp:include page="register-modal.jsp"/>
</div>

<script type="text/javascript">
    (function () {
        'use strict';

        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        var forms = document.querySelectorAll('.needs-validation');

        // Loop over them and prevent submission
        Array.prototype.slice.call(forms)
            .forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }

                    form.classList.add('was-validated')
                }, false)
            })
    })();
</script>
