package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import java.util.List;
import java.util.Objects;

public class Vista {
    Controlador controlador;

    public void setControlador(Controlador controlador){
        Objects.requireNonNull(controlador,"El controlador no puede ser nulo.");
        this.controlador = controlador;
    }

    public void comenzar(){
        Opcion opcion;
        do{
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            ejecutar(opcion);
        } while(opcion != Opcion.SALIR);
    }

    public void terminar(){
        System.out.println("Chao!");
    }

    private void ejecutar(Opcion opcion){
        try {
            switch (opcion){
                case INSERTAR_CLIENTE -> insertarCliente();
                case INSERTAR_VEHICULO -> insertarVehiculo();
                case INSERTAR_REVISION -> insertarRevision();
                case BUSCAR_CLIENTE -> buscarCliente();
                case BUSCAR_VEHICULO -> buscarVehiculo();
                case BUSCAR_REVISION -> buscarRevision();
                case MODIFICAR_CLIENTE -> modificarCliente();
                case ANADIR_HORAS_REVISION -> anadirHoras();
                case ANADIR_PRECIO_MATERIAL_REVISION -> anadirPrecioMaterial();
                case CERRAR_REVISION -> cerrarRevision();
                case BORRAR_CLIENTE -> borrarCliente();
                case BORRAR_VEHICULO -> borrarVehiculo();
                case BORRAR_REVISION -> borrarRevision();
                case LISTAR_CLIENTE -> listarClientes();
                case LISTAR_VEHICULO -> listarVehiculos();
                case LISTAR_REVISION -> listarRevisiones();
                case LISTAR_REVISION_CLIENTE -> listarRevisionesCliente();
                case LISTAR_REVISION_VEHICULO -> listarRevisionesVehiculo();
                case SALIR -> salir();
            }
        } catch (TallerMecanicoExcepcion | IllegalArgumentException e){
            System.out.printf("ERROR: %s %n", e.getMessage());
        }

    }

    private void insertarCliente() throws TallerMecanicoExcepcion {
        Consola.mostrarCabecera("Insertar un cliente");
        controlador.insertarCliente(Consola.leerCliente());
        System.out.println("Cliente insertado correctamente.");
    }

    private void insertarVehiculo() throws TallerMecanicoExcepcion {
        Consola.mostrarCabecera("Insertar un Vehiculo");
        controlador.insertarVehiculo(Consola.leerVehiculo());
        System.out.println("Vehiculo insertado correctamente.");
    }

    private void insertarRevision() throws TallerMecanicoExcepcion {
        Consola.mostrarCabecera("Insertar un revisión");
        controlador.insertarRevision(Consola.leerRevision());
        System.out.println("Revisión insertada correctamente.");
    }

    private void buscarCliente(){
        Consola.mostrarCabecera("Buscar un cliente");
        System.out.print(controlador.buscarCliente(Consola.leerClienteDni()));
    }

    private void buscarVehiculo(){
        Consola.mostrarCabecera("Buscar un vehiculo");
        System.out.println(controlador.buscarVehiculo(Consola.leerVehiculoMatricula()));
    }

    private void buscarRevision(){
        Consola.mostrarCabecera("Buscar un revisión");
        System.out.println(controlador.buscarRevision(Consola.leerRevision()));
    }

    private void modificarCliente() throws TallerMecanicoExcepcion {
        Consola.mostrarCabecera("Modificar un cliente");
        controlador.modificarCliente(Consola.leerClienteDni(),Consola.leerNuevoNombre(),Consola.leerNuevoTelefono());
        System.out.println("Se ha modificado el cliente correctamente.");
    }

    private void anadirHoras() throws TallerMecanicoExcepcion {
        Consola.mostrarCabecera("Añadir horas");
        controlador.anadirHoras(Consola.leerRevision(),Consola.leerHoras());
        System.out.println("Se han añadido las horas correctamente.");
    }

    private void anadirPrecioMaterial() throws TallerMecanicoExcepcion {
        Consola.mostrarCabecera("Añadir precio del material");
        controlador.anadirPrecioMaterial(Consola.leerRevision(),Consola.leerPrecioMaterial());
        System.out.println("Se ha añadido el precio correctamente.");
    }

    private void cerrarRevision() throws TallerMecanicoExcepcion {
        Consola.mostrarCabecera("Cerrar revisión");
        controlador.cerrarRevision(Consola.leerRevision(),Consola.leerFechaCierre());
        System.out.println("Se a cerrado la revisión correctamente.");
    }

    private void borrarCliente() throws TallerMecanicoExcepcion {
        Consola.mostrarCabecera("Borrar un cliente");
        controlador.borrarCliente(Consola.leerClienteDni());
        System.out.println("Cliente borrado correctamente.");
    }

    private void borrarVehiculo() throws TallerMecanicoExcepcion {
        Consola.mostrarCabecera("Borrar un vehiculo");
        controlador.borrarVehiculo(Consola.leerVehiculoMatricula());
        System.out.println("Vehiculo borrado correctamente.");
    }

    private void borrarRevision() throws TallerMecanicoExcepcion {
        Consola.mostrarCabecera("Borrar un revision");
        controlador.borrarRevision(Consola.leerRevision());
        System.out.println("Revisión borrada correctamente.");
    }

    private void listarClientes(){
        Consola.mostrarCabecera("Lista de clientes");
        List<Cliente> clientes = controlador.listarClientes();
        if (clientes.isEmpty()){
            System.out.println("No hay clientes todavía.");
        } else {
            for(Cliente cliente : clientes){
                System.out.println(cliente);
            }
        }
    }

    private void listarVehiculos(){
        Consola.mostrarCabecera("Lista de vehículos");
        List<Vehiculo> vehiculos = controlador.listarVehiculos();
        if (vehiculos.isEmpty()){
            System.out.println("No hay vehículos todavía.");
        } else {
            for(Vehiculo vehiculo : vehiculos){
                System.out.println(vehiculo);
            }
        }
    }

    private void listarRevisiones(){
        Consola.mostrarCabecera("Lista de revisiones");
        List<Revision> revisiones = controlador.listarRevisiones();
        if (revisiones.isEmpty()){
            System.out.println("No hay revisiones todavía.");
        } else {
            for(Revision revision : revisiones){
                System.out.println(revision);
            }
        }
    }

    private void listarRevisionesCliente(){
        Consola.mostrarCabecera("Lista de revisiones en base a los clientes");
        List<Revision> revisiones = controlador.listarRevisionesCliente(Consola.leerCliente());
        if (revisiones.isEmpty()){
            System.out.println("No hay revisiones adecuadas todavía.");
        } else {
            for(Revision revision : revisiones){
                System.out.println(revision);
            }
        }
    }

    private void listarRevisionesVehiculo(){
        Consola.mostrarCabecera("Lista de revisiones en base a los vehículos");
        List<Revision> revisiones = controlador.listarRevisionesVehiculo(Consola.leerVehiculo());
        if (revisiones.isEmpty()){
            System.out.println("No hay revisiones adecuadas todavía.");
        } else {
            for(Revision revision : revisiones){
                System.out.println(revision);
            }
        }
    }

    private void salir(){
        controlador.terminar();
    }
}
