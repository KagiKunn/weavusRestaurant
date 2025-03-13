function logout() {
    const logoutForm = document.createElement("form");
    logoutForm.method = "POST";
    logoutForm.action = "/logout";

// CSRF 토큰을 추가
    csrfTokenSubmit(logoutForm)

// 폼을 제출
    document.body.appendChild(logoutForm);
    logoutForm.submit();
}

function csrfTokenSubmit(f){
    const csrfToken = document.querySelector('input[name="_csrf"]').value;
    const csrfTokenInput = document.createElement("input");
    csrfTokenInput.type = "hidden";
    csrfTokenInput.name = "_csrf";
    csrfTokenInput.value = csrfToken;
    f.appendChild(csrfTokenInput);
}