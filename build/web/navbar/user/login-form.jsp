<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form action="${pageContext.request.contextPath}/login" method="post" class="row g-3 needs-validation" novalidate>
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
        <label for="password" class="input-group-text rounded-pill rounded-end bg-light px-3">
            <i class="bi bi-key" aria-hidden="true"></i>
        </label>
        <input class="form-control rounded-pill rounded-start"
               type="password" name="password" id="password"
               placeholder="Password" required>
        <div class="invalid-feedback">
            Please enter your password.
        </div>
    </div>
    <div class="col-6">
        <input type="checkbox" class="form-check-input ms-3" id="remember" name="remember" value="true" checked>
        <label class="form-check-label" for="remember">Remember me</label>
    </div>
    <div class="col">
        <a href="#" class="float-end me-3">Forgot password?</a>
    </div>
    <div class="col-12">
        <button class="btn btn-success rounded-pill text-light float-end me-3" type="submit">
            <i class="bi bi-box-arrow-in-right me-3" aria-hidden="true"></i>
            Login
        </button>
    </div>
</form>