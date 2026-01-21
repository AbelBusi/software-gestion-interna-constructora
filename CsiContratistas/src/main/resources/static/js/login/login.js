document.addEventListener('DOMContentLoaded', () => {
  const form           = document.getElementById('loginForm');
  const alertContainer = document.getElementById('alertContainer');

  /* -------- helper para alertas -------- */
  const showAlert = (type, msg) => {
    alertContainer.innerHTML = `
      <div class="alert alert-${type} alert-dismissible fade show" role="alert">
        <i class="fa-solid ${type === 'success' ? 'fa-circle-check' : 'fa-circle-exclamation'} me-2"></i>
        ${msg}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
      </div>`;
    setTimeout(() => {
      const al = alertContainer.querySelector('.alert');
      if (al) bootstrap.Alert.getOrCreateInstance(al).close();
    }, 4000);
  };

  /* ------------- submit --------------- */
  form.addEventListener('submit', async e => {
    e.preventDefault();

    const correo       = document.getElementById('correo').value.trim();
    const clave_acceso = document.getElementById('clave').value;

    try {
      const res = await fetch('/autenticacion/login', {
        method : 'POST',
        headers: { 'Content-Type': 'application/json' },
        body   : JSON.stringify({ correo, clave_acceso })
      });

      if (res.ok) {
        // API devuelve { token: "jwt..." } y (opcional) Set-Cookie: token=...
        const { token } = await res.json();

        /* Guarda el JWT por si lo necesitas en JS */
        localStorage.setItem('token', token);

        /* Feedback + redirección */
        showAlert('success', '¡Inicio de sesión exitoso! Redirigiendo…');
        setTimeout(() => window.location.href = '/administrador/menu', 1000);

      } else {
        showAlert('danger', 'Credenciales incorrectas. Intenta otra vez.');
      }
    } catch (err) {
      showAlert('danger', 'Error de conexión: ' + err.message);
    }
  });
});
