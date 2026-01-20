package com.dev.CsiContratistas.domain.ports.out;

public interface CambiarCorreoClaveRepositorioPort {

    boolean actualizarPassword(String correo, String nuevaPassword);


}
