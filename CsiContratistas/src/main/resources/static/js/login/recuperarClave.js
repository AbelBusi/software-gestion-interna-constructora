let currentStep = 1;
const alertBox = document.getElementById('alertContainer');
let emailUsuario = '';  // <- el correo del paso 1, lo guardaremos aquí

/* ---------- Helpers ---------- */
function show(msg, type = 'danger') {
  alertBox.innerHTML = `
    <div class="alert alert-${type} alert-dismissible fade show" role="alert">
      <i class="fa-solid fa-circle-exclamation me-2"></i>${msg}
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>`;
}

const fieldValid = id => document.getElementById(id).checkValidity();

async function postJSON(url, data, method = 'POST') {
  const res = await fetch(url, {
    method,
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
  });

  if (!res.ok) {
    const body = await res.json().catch(() => ({}));
    throw new Error(body.mensaje || 'Error en la operación');
  }
  return res.json().catch(() => ({}));
}

/* ---------- Validaciones por paso ---------- */
function validateStep(step) {
  alertBox.innerHTML = '';

  if (step === 1) {
    if (!fieldValid('dni')) {
      show('DNI inválido: deben ser 8 dígitos.');
      return false;
    }
    if (!fieldValid('emailUsuario')) {
      show('Ingresa un correo electrónico válido.');
      return false;
    }
  }

  if (step === 2 && !fieldValid('emailVerificacion')) {
    show('El correo para verificación no es válido.');
    return false;
  }

  if (step === 3 && !fieldValid('codigo')) {
    show('El código debe ser numérico de 6 dígitos.');
    return false;
  }

  return true;
}

/* ---------- Avance entre pasos ---------- */
async function nextStep(step) {
  if (!validateStep(step)) return;
  const btn = event?.target;

  // Paso 1: verificar DNI + correo
  if (step === 1) {
    const dni = document.getElementById('dni').value.trim();
    emailUsuario = document.getElementById('emailUsuario').value.trim(); // <-- GUARDAMOS AQUÍ

    try {
      btn.disabled = true;
      btn.innerHTML = '<span class="spinner-border spinner-border-sm"></span> Verificando…';

      await postJSON('/autenticacion/empleado', { dni, correo: emailUsuario });

      goToNext(step);
    } catch (err) {
      show(err.message);
    } finally {
      btn.disabled = false;
      btn.textContent = 'Siguiente';
    }
    return;
  }

  // Paso 2: enviar código a correo de verificación
  if (step === 2) {
    const emailVerificacion = document.getElementById('emailVerificacion').value.trim();

    try {
      btn.disabled = true;
      btn.innerHTML = '<span class="spinner-border spinner-border-sm"></span> Enviando…';

      await postJSON('/verificarEmail/enviar-codigo', {
        email: emailVerificacion
      });

      show('Código enviado. Revisa tu bandeja de entrada.', 'success');
      goToNext(step);
    } catch (err) {
      show(err.message);
    } finally {
      btn.disabled = false;
      btn.textContent = 'Enviar código';
    }
    return;
  }

  // Paso 3: validar código
  if (step === 3) {
    const codigo = document.getElementById('codigo').value.trim();

    try {
      btn.disabled = true;
      btn.innerHTML = '<span class="spinner-border spinner-border-sm"></span> Validando…';

      const { valido } = await postJSON(
        '/verificarEmail/verificar-codigo',
        {
          email: document.getElementById('emailVerificacion').value.trim(),
          codigo
        }
      );

      if (!valido) {
        show('El código ingresado no es válido.');
        return;
      }

      show('Código verificado correctamente.', 'success');
      goToNext(step);
    } catch (err) {
      show(err.message);
    } finally {
      btn.disabled = false;
      btn.textContent = 'Validar código';
    }
    return;
  }

  // Otros pasos sin lógica extra
  goToNext(step);
}

function goToNext(step) {
  document.getElementById(`step-${step}`).classList.add('d-none');
  currentStep = step + 1;
  document.getElementById(`step-${currentStep}`).classList.remove('d-none');
}

/* ---------- Paso 4: cambiar contraseña ---------- */
const form = document.getElementById('recoveryForm');
form.addEventListener('submit', async e => {
  e.preventDefault();
  alertBox.innerHTML = '';

  const newPass = document.getElementById('newPass').value;
  const confirmPass = document.getElementById('confirmPass').value;

  if (!fieldValid('newPass')) {
    show('La nueva contraseña debe tener al menos 6 caracteres.');
    return;
  }

  if (newPass !== confirmPass) {
    show('Las contraseñas no coinciden.');
    return;
  }

  const submitBtn = form.querySelector('button[type="submit"]');

  try {
    submitBtn.disabled = true;
    submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm"></span> Guardando…';

    await postJSON(
      '/autenticacion/cambiarClaveUsuario',
      {
        correo: emailUsuario,  // <-- Usamos el del paso 1
        clave: newPass,
        claveConfirmacion: confirmPass
      },
      'PATCH'
    );

    show('Contraseña cambiada correctamente. Redirigiendo...', 'success');
    setTimeout(() => window.location.href = '/administrador/ingresar', 2500);
  } catch (err) {
    show(err.message);
  } finally {
    submitBtn.disabled = false;
    submitBtn.textContent = 'Guardar nueva contraseña';
  }
});