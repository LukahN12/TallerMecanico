package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.cascada.ModeloCascada;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Controlador {
    private ModeloCascada modeloCascada;
    private Vista vista;

    public Controlador(ModeloCascada modeloCascada, Vista vista) {
        Objects.requireNonNull(vista,"La vista no puede ser nula");
        Objects.requireNonNull(modeloCascada,"El modelo no puede ser nulo");
        vista.setControlador(this);
        this.modeloCascada = modeloCascada;
        this.vista = vista;
    }

    public void comenzar(){
        modeloCascada.comenzar();
        vista.comenzar();
    }

    public void terminar(){
        modeloCascada.terminar();
        vista.terminar();
    }

    public void insertarCliente(Cliente cliente) throws TallerMecanicoExcepcion {
        modeloCascada.insertar(cliente);
    }

    public void insertarVehiculo(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        modeloCascada.insertar(vehiculo);
    }

    public void insertarRevision(Trabajo trabajo) throws TallerMecanicoExcepcion {
        modeloCascada.insertar(trabajo);
    }

    public Cliente buscarCliente(Cliente cliente){
        return modeloCascada.buscar(cliente);
    }

    public Vehiculo buscarVehiculo(Vehiculo vehiculo){
        return modeloCascada.buscar(vehiculo);
    }

    public Trabajo buscarRevision(Trabajo trabajo){
        return modeloCascada.buscar(trabajo);
    }

    public Cliente modificarCliente(Cliente cliente,String nombre, String telefono) throws TallerMecanicoExcepcion {
        return modeloCascada.modificar(cliente, nombre, telefono);
    }

    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        return modeloCascada.anadirHoras(trabajo, horas);
    }

    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        return modeloCascada.anadirPrecioMaterial(trabajo,precioMaterial);
    }

    public Trabajo cerrarRevision(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        return modeloCascada.cerrar(trabajo,fechaFin);
    }

    public void borrarCliente(Cliente cliente) throws TallerMecanicoExcepcion {
        modeloCascada.borrar(cliente);
    }

    public void borrarVehiculo(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        modeloCascada.borrar(vehiculo);
    }

    public void borrarRevision(Trabajo trabajo) throws TallerMecanicoExcepcion {
        modeloCascada.borrar(trabajo);
    }

    public List<Cliente> listarClientes(){
        return modeloCascada.getClientes();
    }

    public List<Vehiculo> listarVehiculos(){
        return modeloCascada.getVehiculos();
    }

    public List<Trabajo> listarRevisiones(){
        return modeloCascada.getRevisiones();
    }

    public List<Trabajo> listarRevisionesCliente(Cliente cliente){
        return modeloCascada.getRevisiones(cliente);
    }

    public List<Trabajo> listarRevisionesVehiculo(Vehiculo vehiculo){
        return modeloCascada.getRevisiones(vehiculo);
    }
}
