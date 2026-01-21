package com.dev.CsiContratistas.infrastructure.Entity;

import com.dev.CsiContratistas.domain.model.Cargo;
import com.dev.CsiContratistas.domain.model.Empleado;
import com.dev.CsiContratistas.domain.model.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USUARIO",schema ="\"gestionUsuarios\"")
public class UsuarioEntidad implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id_usuario;

    @OneToOne
    @JoinColumn(name = "id_empleado", nullable = false)
    private EmpleadoEntidad id_empleado;


    @Column(name = "correo",length = 50)
    private String correo;

    @Column(name = "clave_acceso")
    private String clave_acceso;

    @Column(name = "fecha_asignacion")
    private LocalDateTime fecha_asignacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "estado")
    private Boolean estado;

    @OneToMany(mappedBy = "id_usuario",fetch = FetchType.EAGER)
    private List<RolUsuarioEntidad> Usuarios = new ArrayList<>();
    @PrePersist
    public void prePersist() {
        if (this.fecha_asignacion == null) {
            this.fecha_asignacion = LocalDateTime.now();
        }
    }
    @PreUpdate
    protected void onUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
    public static UsuarioEntidad fromDomainModel(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        UsuarioEntidad entidad = new UsuarioEntidad();
        entidad.setId_usuario(usuario.getId_usuario());
        entidad.setCorreo(usuario.getCorreo());
        entidad.setClave_acceso(usuario.getClave_acceso());
        entidad.setFecha_asignacion(usuario.getFecha_asignacion());
        entidad.setEstado(usuario.getEstado());

        if (usuario.getId_empleado() != null) {
            EmpleadoEntidad empleadoEntidad = new EmpleadoEntidad();
            empleadoEntidad.setId_empleado(usuario.getId_empleado().getId_empleado());
            entidad.setId_empleado(empleadoEntidad);
        } else {
            entidad.setId_empleado(null);
        }

        return entidad;
    }





    public Usuario toDomainModel() {
        return new Usuario(
                id_usuario,
                id_empleado != null ? id_empleado.toDomainModel() : null,
                correo,
                clave_acceso,
                fecha_asignacion,
                estado
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Usuarios.stream()
                .map(rolUsuario -> new SimpleGrantedAuthority("ROLE_" + rolUsuario.getId_rol().getNombre().toUpperCase()))
                .collect(Collectors.toList());    }

    @Override
    public String getPassword() {
        return clave_acceso;
    }

    @Override
    public String getUsername() {
        return correo;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(estado);
    }
}