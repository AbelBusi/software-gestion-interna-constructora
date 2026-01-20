package com.dev.CsiContratistas.application.CasoUso.Profesion;

import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.model.Profesiones;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LeerProfesionCasoUsoImpl implements ILeerCasoUso<Profesiones> {

    private final ObjectRepositorioPort<Profesiones,Integer> profesionesObjectRepositorioPort;


    @Override
    public Optional<Profesiones> leerObjeto(Integer id) {
        return profesionesObjectRepositorioPort.leer(id);
    }

    @Override
    public List<Profesiones> leerObjetos() {
        return profesionesObjectRepositorioPort.leerObjetos();
    }
}