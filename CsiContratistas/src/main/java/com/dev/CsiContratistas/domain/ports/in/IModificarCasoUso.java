package com.dev.CsiContratistas.domain.ports.in;

import java.util.Optional;

public interface IModificarCasoUso<T> {

    Optional<T> modificarObjeto (Integer id,T material);

}
