// Protege el acceso al menú
(function asegurarSesion() {
  const token = localStorage.getItem('token');

  if (!token) {
    window.location.href = "/administrador/ingresar";
    return;
  }

})();

// Mostrar/ocultar sidebar
function toggleSidebar() {
  const sidebar = document.getElementById('sidebar');
  if (sidebar) sidebar.classList.toggle('show');
}

// Cerrar sesión
function logout() {
  localStorage.removeItem('token');
  document.cookie = "token=; Max-Age=0; path=/";
  window.location.href = "/administrador/ingresar";
}
