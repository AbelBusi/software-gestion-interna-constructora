package com.dev.CsiContratistas.infrastructure.Configuration;

import com.dev.CsiContratistas.application.CasoUso.Cargos.CrearCargoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Cargos.EliminarCargoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Cargos.LeerCargoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Cargos.ModificarCargoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Cliente.CrearClienteCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Cliente.EliminarClienteCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Cliente.LeerClienteCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Cliente.ModificarClienteCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Empleado.*;
import com.dev.CsiContratistas.application.CasoUso.Financiamiento.CrearFinanciamientoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Financiamiento.EliminarFinanciamientoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Financiamiento.LeerFinanciamientoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Financiamiento.ModificarFinanciamientoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.FinanciamientoEmpleado.CrearFinanciamientoEmpleadoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.FinanciamientoEmpleado.EliminarFinanciamientoEmpleadoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.FinanciamientoEmpleado.LeerFinanciamientoEmpleadoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.FinanciamientoEmpleado.ModificarFinanciamientoEmpleadoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.FinanciamientoMaterial.CrearFinanciamientoMaterialCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.FinanciamientoMaterial.EliminarFinanciamientoMaterialCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.FinanciamientoMaterial.LeerFinanciamientoMaterialCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.FinanciamientoMaterial.ModificarFinanciamientoMaterialCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.FormaTerreno.CrearFormaTerrenoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.FormaTerreno.EliminarFormaTerrenoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.FormaTerreno.LeerFormaTerrenoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.FormaTerreno.ModificarFormaTerrenoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Material.CrearMaterialCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Material.EliminarMaterialCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Material.LeerMaterialCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Material.ModificarMaterialCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.ModeloObra.CrearModeloObraCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.ModeloObra.EliminarModeloObraCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.ModeloObra.LeerModeloObraCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.ModeloObra.ModificarModeloObraCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Permiso.CrearPermisoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Permiso.EliminarPermisoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Permiso.LeerPermisoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Permiso.ModificarPermisoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Profesion.CrearProfesionCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Profesion.EliminarProfesionCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Profesion.LeerProfesionCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Profesion.ModificarProfesionCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Rama.CrearRamaCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Rama.EliminarRamaCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Rama.LeerRamaCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Rama.ModificarRamaCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Rol.CrearRolCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Rol.EliminarRolCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Rol.LeerRolCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Rol.ModificarRolCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.RolPermiso.CrearRolPermisoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.RolPermiso.EliminarRolPermisoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.RolPermiso.LeerRolPermisoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.RolPermiso.ModificarRolPermisoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.RolUsuario.CrearRolUsuarioCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.RolUsuario.EliminarRolUsuarioUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.RolUsuario.LeerRolUsuarioCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.RolUsuario.ModificarRolUsuarioCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Terreno.CrearTerrenoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Terreno.EliminarTerrenoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Terreno.LeerTerrenoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Terreno.ModificarTerrenoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.TipoConstruccion.CrearTipoConstruccionCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.TipoConstruccion.EliminarTipoConstruccionCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.TipoConstruccion.LeerTipoConstruccionCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.TipoConstruccion.ModificarTipoConstruccionCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.TipoFinanciamiento.CrearTipoFinanciamientoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.TipoFinanciamiento.EliminarTipoFinanciamientoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.TipoFinanciamiento.LeerTipoFinanciamientoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.TipoFinanciamiento.ModificarTipoFinanciamientoCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.TipoMaterial.CrearTipoMaterialCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.TipoMaterial.EliminarTipoMaterialCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.TipoMaterial.LeerTipoMaterialCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.TipoMaterial.ModificarTipoMaterialCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.TipoObra.CrearTipoObraCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.TipoObra.EliminarTipoObraCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.TipoObra.LeerTipoObraCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.TipoObra.ModificarTipoObraCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.TipoSuelo.CrearTipoSueloCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.TipoSuelo.EliminarTipoSueloCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.TipoSuelo.LeerTipoSueloCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.TipoSuelo.ModificarTipoSueloCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.Usuario.*;
import com.dev.CsiContratistas.application.CasoUso.obras.CrearObraCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.obras.EliminarObraCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.obras.LeerObraCasoUsoImpl;
import com.dev.CsiContratistas.application.CasoUso.obras.ModificarObraCasoUsoImpl;
import com.dev.CsiContratistas.application.Services.*;
import com.dev.CsiContratistas.domain.model.*;
import com.dev.CsiContratistas.domain.ports.out.CambiarCorreoClaveRepositorioPort;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.domain.ports.out.ObjectValidRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Repository.on.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ApplicationConfig {

    @Bean
    public TipoMaterialServicio tipoMaterialServicio(ObjectRepositorioPort<Tipo_material,Integer> tipoMaterialRepositorioPort, JpaTipoMaterialRepositorioAdapter jpaTipoMaterialRepositorioAdapter) {

        return new TipoMaterialServicio(
                new LeerTipoMaterialCasoUsoImpl(tipoMaterialRepositorioPort),
                new CrearTipoMaterialCasoUsoImpl(tipoMaterialRepositorioPort),
                new ModificarTipoMaterialCasoUsoImpl(tipoMaterialRepositorioPort),
                new EliminarTipoMaterialCasoUsoImpl(tipoMaterialRepositorioPort),
                jpaTipoMaterialRepositorioAdapter
        );
    }

    @Bean
    @Primary
    public ObjectRepositorioPort<Tipo_material, Integer> TipoMaterialRepositorioPort(JpaTipoMaterialRepositorioAdapter jpaTipoMaterialRepositorioAdapter) {

        return jpaTipoMaterialRepositorioAdapter;

    }


    @Bean
    public RolServicio rolServicio(ObjectRepositorioPort<Rol,Integer> rolRepositorioPort, JpaRolRepositorioAdapter jpaRolRepositorioAdapter){

        return new RolServicio(
                new LeerRolCasoUsoImpl(rolRepositorioPort),
                new CrearRolCasoUsoImpl(rolRepositorioPort),
                new ModificarRolCasoUsoImpl(rolRepositorioPort),
                new EliminarRolCasoUsoImpl(rolRepositorioPort),
                jpaRolRepositorioAdapter
        );
    }

    @Bean
    @Primary
    public ObjectRepositorioPort<Rol, Integer> RolRepositorioPort(JpaRolRepositorioAdapter jpaRolRepositorioAdapter) {

        return jpaRolRepositorioAdapter;

    }

    @Bean
    public PermisoServicio permisoServicio(ObjectRepositorioPort<Permiso,Integer> permisoRepositorioPort){

        return new PermisoServicio(
                new LeerPermisoCasoUsoImpl(permisoRepositorioPort),
                new CrearPermisoCasoUsoImpl(permisoRepositorioPort),
                new ModificarPermisoCasoUsoImpl(permisoRepositorioPort),
                new EliminarPermisoCasoUsoImpl(permisoRepositorioPort)
        );
    }

    @Bean
    @Primary
    public ObjectRepositorioPort<Permiso, Integer> permisoRepositorioPort(JpaPermisoRepositorioAdapter jpaPermisoRepositorioAdapter) {

        return jpaPermisoRepositorioAdapter;

    }


    @Bean
    public ProfesionServicio profesionServicio(ObjectRepositorioPort<Profesiones,Integer> profesionRepositorioPort, JpaProfesionRepositorioAdapter jpaProfesionRepositorioAdapter){

        return new ProfesionServicio(
                new LeerProfesionCasoUsoImpl(profesionRepositorioPort),
                new CrearProfesionCasoUsoImpl(profesionRepositorioPort),
                new ModificarProfesionCasoUsoImpl(profesionRepositorioPort),
                new EliminarProfesionCasoUsoImpl(profesionRepositorioPort),
                jpaProfesionRepositorioAdapter
        );
    }

    @Bean
    @Primary
    public ObjectRepositorioPort<Profesiones, Integer> profesionRepositorioPort(JpaProfesionRepositorioAdapter jpaProfesionRepositorioAdapter) {

        return jpaProfesionRepositorioAdapter;

    }

    @Bean
    public RamaServicio ramaServicio(ObjectRepositorioPort<Rama,Integer> ramaRepositorioPort, JpaRamaRepositorioAdapter jpaRamaRepositorioAdapter){

        return new RamaServicio(
                new LeerRamaCasoUsoImpl(ramaRepositorioPort),
                new CrearRamaCasoUsoImpl(ramaRepositorioPort),
                new ModificarRamaCasoUsoImpl(ramaRepositorioPort),
                new EliminarRamaCasoUsoImpl(ramaRepositorioPort),
                jpaRamaRepositorioAdapter
        );
    }

    @Bean
    @Primary
    public ObjectRepositorioPort<Rama, Integer> ramaRepositorioPort(JpaRamaRepositorioAdapter jpaRamaRepositorioAdapter) {

        return jpaRamaRepositorioAdapter;

    }

    @Bean
    public EmpleadoServicio empleadoServicio(
            ObjectRepositorioPort<Empleado,Integer> empleadoRepositorioPort,
            @Qualifier("empleadoValidRepositorioPort") ObjectValidRepositorioPort<String> empleadoValidacionRepositorioPort,
            JpaEmpleadoRepositorioAdapter jpaEmpleadoRepositorioAdapter){

        return new EmpleadoServicio(
                new LeerEmpleadoCasoUsoImpl(empleadoRepositorioPort),
                new CrearEmpleadoCasoUsoImpl(empleadoRepositorioPort),
                new ModificarEmpleadoCasoUsoImpl(empleadoRepositorioPort),
                new EliminarEmpleadoCasoUsoImpl(empleadoRepositorioPort),
                new VerificarEmpleadoCasoUsoImpl(empleadoValidacionRepositorioPort),
                jpaEmpleadoRepositorioAdapter
        );
    }


    @Bean
    @Primary
    public ObjectRepositorioPort<Empleado, Integer> empleadoRepositorioPort(JpaEmpleadoRepositorioAdapter jpaEmpleadoRepositorioAdapter) {

        return jpaEmpleadoRepositorioAdapter;

    }

    @Bean
    public ObjectValidRepositorioPort<String> empleadoValidRepositorioPort(JpaEmpleadoRepositorioAdapter jpaEmpleadoRepositorioAdapter){

        return jpaEmpleadoRepositorioAdapter;
    }

    @Bean
    public CargoServicio cargoServicio(ObjectRepositorioPort<Cargo,Integer> cargoRepositorioPort){

        return new CargoServicio(
                new LeerCargoCasoUsoImpl(cargoRepositorioPort),
                new CrearCargoCasoUsoImpl(cargoRepositorioPort),
                new ModificarCargoCasoUsoImpl(cargoRepositorioPort),
                new EliminarCargoCasoUsoImpl(cargoRepositorioPort)
        );
    }

    @Bean
    @Primary
    public ObjectRepositorioPort<Cargo, Integer> cargoRepositorioPort(JpaCargoRepositorioAdapter jpaCargoRepositorioAdapter) {

        return jpaCargoRepositorioAdapter;

    }


    @Bean
    public UsuarioServicio usuarioServicio(
            ObjectRepositorioPort<Usuario,Integer> usuarioRepositorioPort,
            @Qualifier("usuarioValidadoRepositorioPort") ObjectValidRepositorioPort<String> usuarioValidacionCorreoRepositorioPort,@Qualifier("cambiarCorreoRepositorioPort") CambiarCorreoClaveRepositorioPort cambiarCorreoClaveRepositorioPort,
            JpaUsuarioRepositorioAdapter jpaUsuarioRepositorioAdapter,
            UsuarioHistorialServicio usuarioHistorialServicio){

        return new UsuarioServicio(
                new LeerUsuarioCasoUsoImpl(usuarioRepositorioPort),
                new CrearUsuarioCasoUsoImpl(usuarioRepositorioPort),
                new ModificarUsuarioCasoUsoImpl(usuarioRepositorioPort),
                new EliminarUsuarioCasoUsoImpl(usuarioRepositorioPort),
                new VerificarUsuarioCasoUsoImpl(usuarioValidacionCorreoRepositorioPort),
                new CambiarClaveCasoUsoImpl(cambiarCorreoClaveRepositorioPort),
                jpaUsuarioRepositorioAdapter,
                usuarioHistorialServicio
        );
    }


    @Bean
    @Primary
    public ObjectRepositorioPort<Usuario, Integer> usuarioRepositorioPort(JpaUsuarioRepositorioAdapter jpaUsuarioRepositorioAdapter) {

        return jpaUsuarioRepositorioAdapter;

    }

    @Bean
    public ObjectValidRepositorioPort<String> usuarioValidadoRepositorioPort (JpaUsuarioRepositorioAdapter jpaUsuarioRepositorioAdapter){

        return jpaUsuarioRepositorioAdapter;

    }

    @Bean
    public CambiarCorreoClaveRepositorioPort cambiarCorreoRepositorioPort (JpaUsuarioRepositorioAdapter jpaUsuarioRepositorioAdapter){

        return jpaUsuarioRepositorioAdapter;

    }

    @Bean
    public RolUsuarioServicio rolUsuarioServicio(ObjectRepositorioPort<Rol_usuario,Integer> RolUsuarioRepositorioPort){

        return new RolUsuarioServicio(
                new LeerRolUsuarioCasoUsoImpl(RolUsuarioRepositorioPort),
                new CrearRolUsuarioCasoUsoImpl(RolUsuarioRepositorioPort),
                new ModificarRolUsuarioCasoUsoImpl(RolUsuarioRepositorioPort),
                new EliminarRolUsuarioUsoImpl(RolUsuarioRepositorioPort)
        );
    }

    @Bean
    @Primary
    public ObjectRepositorioPort<Rol_usuario, Integer> usuarioRolRepositorioPort(JpaRolUsuarioRepositorioAdapter jpaRolUsuarioRepositorioAdapter) {

        return jpaRolUsuarioRepositorioAdapter;

    }

    @Bean
    public RolPermisoServicio rolPermisoServicio(ObjectRepositorioPort<Rol_Permiso,Integer> RolPermisoRepositorioPort){

        return new RolPermisoServicio(
                new LeerRolPermisoCasoUsoImpl(RolPermisoRepositorioPort),
                new CrearRolPermisoCasoUsoImpl(RolPermisoRepositorioPort),
                new ModificarRolPermisoCasoUsoImpl(RolPermisoRepositorioPort),
                new EliminarRolPermisoUsoImpl(RolPermisoRepositorioPort)
        );
    }

    @Bean
    @Primary
    public ObjectRepositorioPort<Rol_Permiso, Integer> usuarioRolPermisoRepositorioPort(JpaRolPermisoRepositorioAdapter jpaRolPermisoRepositorioAdapter) {

        return jpaRolPermisoRepositorioAdapter;

    }

    @Bean
    public MaterialServicio materialServicio(ObjectRepositorioPort<Material,Integer> materialRepositorioPort,
                                             JpaMaterialRepositorioAdapter jpaMaterialRepositorioAdapter){

        return new MaterialServicio(
                new LeerMaterialCasoUsoImpl(materialRepositorioPort),
                new CrearMaterialCasoUsoImpl(materialRepositorioPort),
                new ModificarMaterialCasoUsoImpl(materialRepositorioPort),
                new EliminarMaterialCasoUsoImpl(materialRepositorioPort),
                 jpaMaterialRepositorioAdapter
        );
    }

    @Bean
    @Primary
    public ObjectRepositorioPort<Material, Integer> materialRepositorioPort(JpaMaterialRepositorioAdapter materialRepositorioAdapter) {

        return materialRepositorioAdapter;

    }


    @Bean
    public TipoObraServicio tipoObraServicio(ObjectRepositorioPort<Tipo_obra,Integer> tipoObraRepositorioPort){

        return new TipoObraServicio(
                new LeerTipoObraCasoUsoImpl(tipoObraRepositorioPort),
                new CrearTipoObraCasoUsoImpl(tipoObraRepositorioPort),
                new ModificarTipoObraCasoUsoImpl(tipoObraRepositorioPort),
                new EliminarTipoObraCasoUsoImpl(tipoObraRepositorioPort)
        );
    }

    @Bean
    @Primary
    public ObjectRepositorioPort<Tipo_obra, Integer> tipoObraRepositorioPort(JpaTipoObraRepositorioAdapter jpaTipoObraRepositorioAdapter) {

        return jpaTipoObraRepositorioAdapter;

    }

    @Bean
    public TipoSueloServicio tipoSueloServicio(ObjectRepositorioPort<Tipo_suelo,Integer> tipoSueloRepositorioPort){

        return new TipoSueloServicio(
                new LeerTipoSueloCasoUsoImpl(tipoSueloRepositorioPort),
                new CrearTipoSueloCasoUsoImpl(tipoSueloRepositorioPort),
                new ModificarTipoSueloCasoUsoImpl(tipoSueloRepositorioPort),
                new EliminarTipoSueloCasoUsoImpl(tipoSueloRepositorioPort)
        );
    }

    @Bean
    @Primary
    public ObjectRepositorioPort<Tipo_suelo, Integer> tipoSueloRepositorioPort(JpaTipoSueloRepositorioAdapter jpaTipoSueloRepositorioAdapter) {

        return jpaTipoSueloRepositorioAdapter;

    }

    @Bean
    public FormaTerrenoServicio formaTerrenoServicio(ObjectRepositorioPort<Forma_terreno,Integer> formaTerrenoRepositorioPort){

        return new FormaTerrenoServicio(
                new LeerFormaTerrenoCasoUsoImpl(formaTerrenoRepositorioPort),
                new CrearFormaTerrenoCasoUsoImpl(formaTerrenoRepositorioPort),
                new ModificarFormaTerrenoCasoUsoImpl(formaTerrenoRepositorioPort),
                new EliminarFormaTerrenoCasoUsoImpl(formaTerrenoRepositorioPort)
        );
    }

    @Bean
    @Primary
    public ObjectRepositorioPort<Forma_terreno, Integer> formaTerrenoRepositorioPort(JpaFormaTerrenoRepositorioAdapter jpaFormaTerrenoRepositorioAdapter) {

        return jpaFormaTerrenoRepositorioAdapter;

    }

    @Bean
    public TerrenoServicio terrenoServicio(ObjectRepositorioPort<Terreno,Integer> terrenoRepositorioPort){

        return new TerrenoServicio(
                new LeerTerrenoCasoUsoImpl(terrenoRepositorioPort),
                new CrearTerrenoCasoUsoImpl(terrenoRepositorioPort),
                new ModificarTerrenoCasoUsoImpl(terrenoRepositorioPort),
                new EliminarTerrenoCasoUsoImpl(terrenoRepositorioPort)
        );
    }

    @Bean
    @Primary
    public ObjectRepositorioPort<Terreno, Integer> terrenoRepositorioPort(JpaTerrenoRepositorioAdapter jpaTerrenoRepositorioAdapter) {

        return jpaTerrenoRepositorioAdapter;

    }

    @Bean
    public ModeloObraServicio modeloObraServicio(ObjectRepositorioPort<Modelo_obra,Integer> modeloObraIntegerObjectRepositorioPort){

        return new ModeloObraServicio(
                new LeerModeloObraCasoUsoImpl(modeloObraIntegerObjectRepositorioPort),
                new CrearModeloObraCasoUsoImpl(modeloObraIntegerObjectRepositorioPort),
                new ModificarModeloObraCasoUsoImpl(modeloObraIntegerObjectRepositorioPort),
                new EliminarModeloObraCasoUsoImpl(modeloObraIntegerObjectRepositorioPort)
        );
    }

    @Bean
    @Primary
    public ObjectRepositorioPort<Modelo_obra, Integer> modeloObraRepositorioPort(JpaModeloObraRepositorioAdapter jpaModeloObraRepositorioAdapter) {

        return jpaModeloObraRepositorioAdapter;

    }

    @Bean
    public TipoConstruccionServicio tipoConstruccionServicio(ObjectRepositorioPort<Tipo_construccion,Integer> tipoConstruccionObjectRepositorioPort){

        return new TipoConstruccionServicio(
                new LeerTipoConstruccionCasoUsoImpl(tipoConstruccionObjectRepositorioPort),
                new CrearTipoConstruccionCasoUsoImpl(tipoConstruccionObjectRepositorioPort),
                new ModificarTipoConstruccionCasoUsoImpl(tipoConstruccionObjectRepositorioPort),
                new EliminarTipoConstruccionCasoUsoImpl(tipoConstruccionObjectRepositorioPort)
        );
    }

    @Bean
    @Primary
    public ObjectRepositorioPort<Tipo_construccion, Integer> tipoConstruccionRepositorioPort(JpaTipoConstruccionRepositorioAdapter jpaTipoConstruccionRepositorioAdapter) {

        return jpaTipoConstruccionRepositorioAdapter;

    }

    @Bean
    public TipoFinanciamientoServicio tipoFinanciamientoServicio(ObjectRepositorioPort<Tipo_financiamiento,Integer> tipoFinanciamientoIntegerObjectRepositorioPort){

        return new TipoFinanciamientoServicio(
                new LeerTipoFinanciamientoCasoUsoImpl(tipoFinanciamientoIntegerObjectRepositorioPort),
                new CrearTipoFinanciamientoCasoUsoImpl(tipoFinanciamientoIntegerObjectRepositorioPort),
                new ModificarTipoFinanciamientoCasoUsoImpl(tipoFinanciamientoIntegerObjectRepositorioPort),
                new EliminarTipoFinanciamientoCasoUsoImpl(tipoFinanciamientoIntegerObjectRepositorioPort)
        );
    }
    @Bean
    @Primary
    public ObjectRepositorioPort<Tipo_financiamiento, Integer> tipoFinanciamientoRepositorioPort(JpaTipoFinanciamientoRepositorioAdapter jpaTipoFinanciamientoRepositorioAdapter) {


        return jpaTipoFinanciamientoRepositorioAdapter;

    }

    @Bean
    public ClienteServicio clienteServicio(ObjectRepositorioPort<Cliente,Integer> clienteObjectRepositorioPort){

        return new ClienteServicio(
                new LeerClienteCasoUsoImpl(clienteObjectRepositorioPort),
                new CrearClienteCasoUsoImpl(clienteObjectRepositorioPort),
                new ModificarClienteCasoUsoImpl(clienteObjectRepositorioPort),
                new EliminarClienteCasoUsoImpl(clienteObjectRepositorioPort)
        );
    }
    @Bean
    @Primary
    public ObjectRepositorioPort<Cliente, Integer> clienteRepositorioPort(JpaClienteRepositorioAdapter jpaClienteRepositorioAdapter) {

        return jpaClienteRepositorioAdapter;

    }

    @Bean
    public FinanciamientoServicio financiamientoServicio(ObjectRepositorioPort<Financiamiento,Integer> financiamientoRepositorioPort){

        return new FinanciamientoServicio(
                new LeerFinanciamientoCasoUsoImpl(financiamientoRepositorioPort),
                new CrearFinanciamientoCasoUsoImpl(financiamientoRepositorioPort),
                new ModificarFinanciamientoCasoUsoImpl(financiamientoRepositorioPort),
                new EliminarFinanciamientoCasoUsoImpl(financiamientoRepositorioPort)
        );
    }
    @Bean
    @Primary
    public ObjectRepositorioPort<Financiamiento, Integer> financiamientoRepositorioPort(JpaFinanciamientoRepositorioAdapter jpaFinanciamientoRepositorioAdapter) {

        return jpaFinanciamientoRepositorioAdapter;

    }

    @Bean
    public FinanciamientoMaterialServicio financiamientoMaterialServicio(ObjectRepositorioPort<Financiamiento_material,Integer> financiamientoMaterialRepositorioPort){

        return new FinanciamientoMaterialServicio(
                new LeerFinanciamientoMaterialCasoUsoImpl(financiamientoMaterialRepositorioPort),
                new CrearFinanciamientoMaterialCasoUsoImpl(financiamientoMaterialRepositorioPort),
                new ModificarFinanciamientoMaterialCasoUsoImpl(financiamientoMaterialRepositorioPort),
                new EliminarFinanciamientoMaterialCasoUsoImpl(financiamientoMaterialRepositorioPort)
        );
    }
    @Bean
    @Primary
    public ObjectRepositorioPort<Financiamiento_material, Integer> financiamientoMaterialRepositorioPort(JpaFinanciamientoMaterialAdapter jpaFinanciamientoMaterialAdapter) {

        return jpaFinanciamientoMaterialAdapter;

    }

    @Bean
    public FinanciamientoEmpleadoServicio financiamientoEmpleadoServicio(ObjectRepositorioPort<Financiamiento_empleado,Integer> financiamientoEmpleadoRepositorioPort){

        return new FinanciamientoEmpleadoServicio(
                new LeerFinanciamientoEmpleadoCasoUsoImpl(financiamientoEmpleadoRepositorioPort),
                new CrearFinanciamientoEmpleadoCasoUsoImpl(financiamientoEmpleadoRepositorioPort),
                new ModificarFinanciamientoEmpleadoCasoUsoImpl(financiamientoEmpleadoRepositorioPort),
                new EliminarFinanciamientoEmpleadoCasoUsoImpl(financiamientoEmpleadoRepositorioPort)
        );
    }
    @Bean
    @Primary
    public ObjectRepositorioPort<Financiamiento_empleado, Integer> obraFinanciamintoPort(JpaFinanciamientoEmpleadoAdapter jpaFinanciamientoEmpleadoAdapter) {

        return jpaFinanciamientoEmpleadoAdapter;

    }


    @Bean
    public ObraServicio obraServicios(ObjectRepositorioPort<Obra,Integer> obraServicioIntegerObjectRepositorioPort){

        return new ObraServicio(
                new LeerObraCasoUsoImpl(obraServicioIntegerObjectRepositorioPort),
                new CrearObraCasoUsoImpl(obraServicioIntegerObjectRepositorioPort),
                new ModificarObraCasoUsoImpl(obraServicioIntegerObjectRepositorioPort),
                new EliminarObraCasoUsoImpl(obraServicioIntegerObjectRepositorioPort)
        );
    }
    @Bean
    @Primary
    public ObjectRepositorioPort<Obra, Integer> obraRepositorioPort(JpaObraRepositorioAdapter jpaObraRepositorioAdapter) {

        return jpaObraRepositorioAdapter;

    }

}