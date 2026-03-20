package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.util.Objects;

public class Vista {
    Controlador controlador;

    public void setControlador(Controlador controlador){
        Objects.requireNonNull(controlador,"El controlador no puede ser nulo.");
        this.controlador = controlador;
    }

    public void comenzar(){
        while (Consola.elegirOpcion() != Opcion.SALIR){
            Consola.mostrarMenu();
            ejecutar(Consola.elegirOpcion());
        }
    }

    public void terminar(){
        System.out.println("Chao!");
    }

    private void ejecutar(Opcion opcion){
        //Usar un switch para usar el metodo correspondiente
    }

    private void insertarCliente() throws TallerMecanicoExcepcion {
        controlador.insertarCliente(Consola.leerCliente());
    }

    private void insertarVehiculo() throws TallerMecanicoExcepcion {
        controlador.insertarVehiculo(Consola.leerVehiculo());
    }

    private void insertarRevision() throws TallerMecanicoExcepcion {
        controlador.insertarRevision(Consola.leerRevision());
    }

    private void buscarCliente(){
        controlador.buscarCliente(Consola.leerClienteDni());
    }

    private void buscarVehiculo(){
        controlador.buscarVehiculo(Consola.leerVehiculoMatricula());
    }

    private void buscarRevision(){
        controlador.buscarRevision(Consola.leerRevision());
    }

    private void modificarCliente() throws TallerMecanicoExcepcion {
        controlador.modificarCliente(Consola.leerCliente(),Consola.leerNuevoNombre(),Consola.leerNuevoTelefono());
    }

    private void anadirHoras() throws TallerMecanicoExcepcion {
        controlador.anadirHoras(Consola.leerRevision(),Consola.leerHoras());
    }

    private void anadirPrecioMaterial() throws TallerMecanicoExcepcion {
        controlador.anadirPrecioMaterial(Consola.leerRevision(),Consola.leerPrecioMaterial());
    }

    private void cerrarRevision() throws TallerMecanicoExcepcion {
        controlador.cerrarRevision(Consola.leerRevision(),Consola.leerFechaCierre());
    }

    private void borrarCliente() throws TallerMecanicoExcepcion {
        controlador.borrarCliente(Consola.leerCliente());
    }

    private void borrarVehiculo() throws TallerMecanicoExcepcion {
        controlador.borrarVehiculo(Consola.leerVehiculo());
    }

    private void borrarRevision() throws TallerMecanicoExcepcion {
        controlador.borrarRevision(Consola.leerRevision());
    }

    private void listarClientes(){
        controlador.listarClientes();
    }

    private void listarVehiculos(){
        controlador.listarVehiculos();
    }

    private void listarRevisiones(){
        controlador.listarRevisiones();
    }

    private void listarRevisionesCleinte(){
        controlador.listarRevisionesCliente(Consola.leerCliente());
    }

    private void listarRevisionesVehiculo(){
        controlador.listarRevisionesVehiculo(Consola.leerVehiculo());
    }

    private void salir(){

    }
}
