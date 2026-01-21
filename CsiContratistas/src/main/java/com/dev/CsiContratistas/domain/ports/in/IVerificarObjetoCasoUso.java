package com.dev.CsiContratistas.domain.ports.in;

import java.util.Optional;

public interface IVerificarObjetoCasoUso<T> {

    boolean obtenerPorParametro(String parametro);

}