<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form action="${pageContext.request.contextPath}/authentication/register" method="post" class="row g-3 needs-validation" novalidate>
    <div class="input-group col-12">
        <label for="display-name" class="input-group-text rounded-pill rounded-end bg-light px-3">
            <i class="bi bi-person-badge" aria-hidden="true"></i>
        </label>
        <input class="form-control rounded-pill rounded-start" type="text" name="display-name" id="display-name"
               placeholder="Display name" required>
        <div class="invalid-feedback">
            Please enter your display name.
        </div>
    </div>
    <div class="input-group col-12">
        <label for="username" class="input-group-text rounded-pill rounded-end bg-light px-3">
            <i class="bi bi-person" aria-hidden="true"></i>
        </label>
        <input class="form-control rounded-pill rounded-start" type="text" name="username" id="username"
               placeholder="Username" required>
        <div class="invalid-feedback">
            Please enter your username.
        </div>
    </div>
    <div class="input-group col-12">
        <label for="email" class="input-group-text rounded-pill rounded-end bg-light px-3">
            <i class="bi bi-mailbox" aria-hidden="true"></i>
        </label>
        <input class="form-control rounded-pill rounded-start" type="email" name="email" id="email" placeholder="Email"
               required>
        <div class="invalid-feedback">
            Please enter your email.
        </div>
    </div>
    <div class="input-group col-12">
        <label for="password" class="input-group-text rounded-pill rounded-end bg-light px-3">
            <i class="bi bi-key" aria-hidden="true"></i>
        </label>
        <input class="form-control rounded-pill rounded-start" type="password" name="password" id="password"
               placeholder="Password" required>
        <div class="invalid-feedback">
            Please enter your password.
        </div>
    </div>
    <div class="input-group col-12">
        <label for="confirm-password" class="input-group-text rounded-pill rounded-end bg-light px-3">
            <i class="bi bi-key" aria-hidden="true"></i>
        </label>
        <input class="form-control rounded-pill rounded-start" type="password" name="confirm-password"
               id="confirm-password" placeholder="Confirm password" required>
        <div class="invalid-feedback" id="confirm-password-feedback">
            Password does not match.
        </div>
    </div>
    <div class="col">
        <div class="form-check ms-3">
            <input type="checkbox" class="form-check-input" id="accept-tos" name="accept-tos" value="true" required/>
            <label class="form-check-label" for="accept-tos">I accept the </label>
            <a href="javascript:void(0)">Terms of Services</a>
        </div>
    </div>
    <div class="col-12">
        <button class="btn btn-info rounded-pill float-end me-3 text-light" type="submit">
            <i class="bi bi-signpost me-3" aria-hidden="true"></i>
            Register
        </button>
    </div>
</form>