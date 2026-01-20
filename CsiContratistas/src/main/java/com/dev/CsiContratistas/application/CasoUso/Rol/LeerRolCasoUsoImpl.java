package com.dev.CsiContratistas.application.CasoUso.Rol;

import com.dev.CsiContratistas.domain.model.Rol;
import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LeerRolCasoUsoImpl implements ILeerCasoUso<Rol> {

    private final ObjectRepositorioPort<Rol,Integer> rolIntegerObjectRepositorioPort;


    @Override
    public Optional<Rol> leerObjeto(Integer id) {
        return rolIntegerObjectRepositorioPort.leer(id);
    }

    @Override
    public List<Rol> leerObjetos() {
        return rolIntegerObjectRepositorioPort.leerObjetos();
    }
}