package com.dev.CsiContratistas.application.CasoUso.Rama;

import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LeerRamaCasoUsoImpl implements ILeerCasoUso<Rama> {

    private final ObjectRepositorioPort<Rama,Integer> ramaIntegerObjectRepositorioPort;


    @Override
    public Optional<Rama> leerObjeto(Integer id) {
        return ramaIntegerObjectRepositorioPort.leer(id);
    }

    @Override
    public List<Rama> leerObjetos() {
        return ramaIntegerObjectRepositorioPort.leerObjetos();
    }
}