package com.dev.CsiContratistas.application.CasoUso.RolUsuario;

import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.domain.model.Rol_usuario;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LeerRolUsuarioCasoUsoImpl implements ILeerCasoUso<Rol_usuario> {

    private final ObjectRepositorioPort<Rol_usuario,Integer> rolUsuarioIntegerObjectRepositorioPort;



    @Override
    public Optional<Rol_usuario> leerObjeto(Integer id) {
        return rolUsuarioIntegerObjectRepositorioPort.leer(id);
    }

    @Override
    public List<Rol_usuario> leerObjetos() {
        return rolUsuarioIntegerObjectRepositorioPort.leerObjetos();
    }
}