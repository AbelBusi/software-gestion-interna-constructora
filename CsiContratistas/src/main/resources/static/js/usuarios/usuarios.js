function filtrarUsuarios() {
    const nombre = document.getElementById('filtroNombre').value.toLowerCase();
    const email = document.getElementById('filtroEmail').value.toLowerCase();
    const rol = document.getElementById('filtroRol').value.toLowerCase();

    document.querySelectorAll('#tablaUsuarios tbody tr').forEach(tr => {
        const tdNombre = tr.children[1].innerText.toLowerCase();
        const tdEmail = tr.children[2].innerText.toLowerCase();
        const tdRol = tr.children[4].innerText.toLowerCase();

        const coincideNombre = tdNombre.includes(nombre);
        const coincideEmail = tdEmail.includes(email);
        const coincideRol = rol === "" || tdRol === rol;

        if (coincideNombre && coincideEmail && coincideRol) {
            tr.style.display = "";
        } else {
            tr.style.display = "none";
        }
    });
}

document.getElementById('filtroNombre').addEventListener('input', filtrarUsuarios);
document.getElementById('filtroEmail').addEventListener('input', filtrarUsuarios);
document.getElementById('filtroRol').addEventListener('change', filtrarUsuarios);
document.addEventListener("DOMContentLoaded", () => {
    const btnReporte = document.getElementById("btnDescargarReporteUsuarios");

    if (btnReporte) {
        btnReporte.addEventListener("click", () => {
            Swal.fire({
                title: '¿Deseas generar el reporte en PDF?',
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Sí, descargar',
                cancelButtonText: 'Cancelar',
                confirmButtonColor: '#d33'
            }).then((result) => {
                if (result.isConfirmed) {
                    window.open('/administrador/usuarios/reporte/pdf', '_blank');
                }
            });
        });
    }
});

$(document).ready(function () {
    $('#modalNuevoUsuario').on('shown.bs.modal', function () {
        $('#id_empleado').select2({
            placeholder: "Buscar empleado...",
            allowClear: true,
            width: '100%',
            dropdownParent: $('#modalNuevoUsuario')
        });
        
        
        initPasswordValidation();
    });
    $('#modalEditarUsuario').on('shown.bs.modal', function () {
        $('#editarIdEmpleado').select2({
            placeholder: "Buscar empleado...",
            allowClear: true,
            width: '100%',
            dropdownParent: $('#modalEditarUsuario')
        });
    });

    
    $(document).on('click', '.btn-ver-detalles', function () {
        $('#detalleNombre').text($(this).data('nombre'));
        $('#detalleApellidos').text($(this).data('apellidos'));
        $('#detalleDni').text($(this).data('dni'));
        $('#detalleDireccion').text($(this).data('direccion'));
        $('#detalleTelefono').text($(this).data('telefono'));

        const modal = new bootstrap.Modal(document.getElementById('modalDetalleEmpleado'));
        modal.show();
    });

    
    $(document).on('click', '.btn-editar-usuario', function () {
        const idUsuario = $(this).data('id_usuario');
        const idEmpleado = $(this).data('id_empleado');
        const correo = $(this).data('correo');
        const estado = $(this).data('estado');
        const idRol = $(this).data('id_rol');

        
        $('#editarIdUsuario').val(idUsuario);
        $('#editarIdEmpleado').val(idEmpleado).trigger('change');
        $('input[type="email"][readonly]').val(correo);
        $('input[type="hidden"][name="correo"]').val(correo);
        $('#editarIdRol').val(idRol);
        $('#editarEstado').val(estado.toString());

        
        $('#formEditarUsuario').attr('action', `/administrador/usuarios/editar/${idUsuario}`);

        
        const modal = new bootstrap.Modal(document.getElementById('modalEditarUsuario'));
        modal.show();
    });

    
    $(document).on('click', '.btn-eliminar-usuario', function () {
        const idUsuario = $(this).data('id_usuario');
        
        Swal.fire({
            title: '¿Estás seguro?',
            text: "Esta acción no se puede deshacer",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            cancelButtonColor: '#3085d6',
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = `/administrador/usuarios/eliminar/${idUsuario}`;
            }
        });
    });
});
$(document).on('click', '.btn-historial-usuario', function () {
    const idUsuario = $(this).data('id_usuario');
    $.get('/administrador/usuarios/historial/' + idUsuario, function(historial) {
        let html = '';
        historial.forEach(item => {
            html += `<tr>
                <td>${item.fecha.replace('T', ' ').substring(0, 19)}</td>
                <td>${item.accion}</td>
                <td>${item.detalle}</td>
                <td>${item.usuarioResponsable}</td>
            </tr>`;
        });
        $('#historialUsuarioBody').html(html);
        const modal = new bootstrap.Modal(document.getElementById('modalHistorialUsuario'));
        modal.show();
    });
});
function initPasswordValidation() {
    const passwordInput = $('#clave_acceso');
    const togglePassword = $('#togglePassword');
    const eyeIcon = $('#eyeIcon');
    
   
    togglePassword.on('click', function() {
        const type = passwordInput.attr('type') === 'password' ? 'text' : 'password';
        passwordInput.attr('type', type);
        eyeIcon.toggleClass('fa-eye fa-eye-slash');
    });
    
    
    passwordInput.on('input', function() {
        validatePassword($(this).val());
    });
    
    
    validatePassword(passwordInput.val());
}


function validatePassword(password) {
    const validations = {
        length: password.length >= 6,
        uppercase: /[A-Z]/.test(password),
        lowercase: /[a-z]/.test(password),
        number: /\d/.test(password),
        special: /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(password)
    };
    
   
    const strength = calculatePasswordStrength(password, validations);
    
    
    updateStrengthBar(strength);
    
    
    updateValidationIndicator('lengthCheck', validations.length);
    updateValidationIndicator('uppercaseCheck', validations.uppercase);
    updateValidationIndicator('lowercaseCheck', validations.lowercase);
    updateValidationIndicator('numberCheck', validations.number);
    updateValidationIndicator('specialCheck', validations.special);
    
    
    const allValid = Object.values(validations).every(valid => valid);
    
    
    const submitButton = $('#formNuevoUsuario button[type="submit"]');
    if (allValid) {
        submitButton.prop('disabled', false);
        submitButton.removeClass('btn-secondary').addClass('btn-primary');
        submitButton.html('<i class="fas fa-check me-1"></i> Crear Usuario');
    } else {
        submitButton.prop('disabled', true);
        submitButton.removeClass('btn-primary').addClass('btn-secondary');
        submitButton.html('<i class="fas fa-lock me-1"></i> Completar Contraseña');
    }
    
    return allValid;
}


function calculatePasswordStrength(password, validations) {
    if (password.length === 0) return 0;
    
    let strength = 0;
    
    
    if (password.length >= 6) strength += 20;
    if (password.length >= 8) strength += 10;
    if (password.length >= 12) strength += 10;
    
    
    if (validations.uppercase) strength += 15;
    if (validations.lowercase) strength += 15;
    if (validations.number) strength += 15;
    if (validations.special) strength += 15;
    
    
    const uniqueChars = new Set(password).size;
    if (uniqueChars >= 8) strength += 10;
    
    return Math.min(strength, 100);
}


function updateStrengthBar(strength) {
    const strengthFill = $('#strengthFill');
    const strengthText = $('#strengthText');
    
    strengthFill.css('width', strength + '%');
    
    let text, color;
    if (strength === 0) {
        text = 'Ingresa una contraseña';
        color = '#6c757d';
    } else if (strength < 30) {
        text = 'Muy débil';
        color = '#e74c3c';
    } else if (strength < 50) {
        text = 'Débil';
        color = '#f39c12';
    } else if (strength < 70) {
        text = 'Moderada';
        color = '#f1c40f';
    } else if (strength < 90) {
        text = 'Fuerte';
        color = '#27ae60';
    } else {
        text = 'Muy fuerte';
        color = '#2ecc71';
    }
    
    strengthText.text(text).css('color', color);
    
    
    if (strength < 30) {
        strengthFill.css('background', 'linear-gradient(90deg, #e74c3c, #e74c3c)');
    } else if (strength < 50) {
        strengthFill.css('background', 'linear-gradient(90deg, #e74c3c, #f39c12)');
    } else if (strength < 70) {
        strengthFill.css('background', 'linear-gradient(90deg, #f39c12, #f1c40f)');
    } else if (strength < 90) {
        strengthFill.css('background', 'linear-gradient(90deg, #f1c40f, #27ae60)');
    } else {
        strengthFill.css('background', 'linear-gradient(90deg, #27ae60, #2ecc71)');
    }
}


function updateValidationIndicator(elementId, isValid) {
    const element = $(`#${elementId}`);
    const icon = element.find('.validation-icon i');
    
    element.removeClass('valid invalid pending');
    
    if (isValid) {
        element.addClass('valid');
        icon.removeClass('fa-circle fa-times-circle').addClass('fa-check');
        element.addClass('animate__animated animate__fadeInUp');
    } else {
        element.addClass('invalid');
        icon.removeClass('fa-circle fa-check').addClass('fa-times');
        element.addClass('animate__animated animate__shakeX');
    }
    
    
    setTimeout(() => {
        element.removeClass('animate__animated animate__fadeInUp animate__shakeX');
    }, 1000);
}
