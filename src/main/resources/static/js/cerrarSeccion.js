function cerrarSesion() {
    localStorage.removeItem("usuario");
    window.location.href = "../../html/paginas/login.html";

}
