package com.dev.CsiContratistas.domain.ports.out;

import org.springframework.context.annotation.Primary;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


public interface ObjectRepositorioPort <T,ID>{

    T guardar (T objeto);
    ID eliminar (ID id);

    Optional< T> leer(ID id);

    List<T> leerObjetos();

    Optional<T> modificar (ID id,T t);

}