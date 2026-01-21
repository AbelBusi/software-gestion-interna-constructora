package com.dev.CsiContratistas.domain.ports.in;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public interface ILeerCasoUso <T>{

    Optional<T> leerObjeto(Integer id);

    List<T> leerObjetos();

}