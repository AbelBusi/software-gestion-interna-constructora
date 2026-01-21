package com.dev.CsiContratistas.domain.ports.out;

import java.util.Optional;

public interface ObjectValidRepositorioPort <ID>{

    boolean leerParametro(ID id);

}
