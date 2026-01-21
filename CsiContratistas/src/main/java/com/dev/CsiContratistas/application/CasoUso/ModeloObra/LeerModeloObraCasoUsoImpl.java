package com.dev.CsiContratistas.application.CasoUso.ModeloObra;

import com.dev.CsiContratistas.domain.model.Material;
import com.dev.CsiContratistas.domain.model.Modelo_obra;
import com.dev.CsiContratistas.domain.ports.in.ILeerCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LeerModeloObraCasoUsoImpl implements ILeerCasoUso<Modelo_obra> {

    private final ObjectRepositorioPort<Modelo_obra,Integer> modeloObraIntegerObjectRepositorioPort;


    @Override
    public Optional<Modelo_obra> leerObjeto(Integer id) {
        return modeloObraIntegerObjectRepositorioPort.leer(id);
    }

    @Override
    public List<Modelo_obra> leerObjetos() {
        return modeloObraIntegerObjectRepositorioPort.leerObjetos();
    }
}