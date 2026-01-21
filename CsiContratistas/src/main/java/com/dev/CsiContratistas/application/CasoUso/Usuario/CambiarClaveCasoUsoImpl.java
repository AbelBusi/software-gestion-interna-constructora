package com.dev.CsiContratistas.application.CasoUso.Usuario;

import com.dev.CsiContratistas.domain.ports.in.IcambiarClaveAccesoCasoUso;
import com.dev.CsiContratistas.domain.ports.out.CambiarCorreoClaveRepositorioPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CambiarClaveCasoUsoImpl implements IcambiarClaveAccesoCasoUso {

    private final CambiarCorreoClaveRepositorioPort cambiarCorreoClaveRepositorioPort;

    @Override
    public boolean actualizarPassword(String correo, String nuevaPassword) {
        return cambiarCorreoClaveRepositorioPort.actualizarPassword(correo,nuevaPassword);
    }
}
