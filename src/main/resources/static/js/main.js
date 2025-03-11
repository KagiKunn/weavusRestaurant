function logout() {
    const f =document.createElement("form");
    f.setAttribute("method", "POST");
    f.setAttribute("action", "/logout");
    document.body.append(f);
    f.submit();
}
