package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Controlador {
    private Modelo modelo;
    private Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        Objects.requireNonNull(vista,"La vista no puede ser nula");
        Objects.requireNonNull(modelo,"El modelo no puede ser nulo");
        vista.setControlador(this);
        this.modelo = modelo;
        this.vista = vista;
    }

    public void comenzar(){
        modelo.comenzar();
        vista.comenzar();
    }

    public void terminar(){
        modelo.terminar();
        vista.terminar();
    }

    public void insertarCliente(Cliente cliente) throws TallerMecanicoExcepcion {
        modelo.insertar(cliente);
    }

    public void insertarVehiculo(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        modelo.insertar(vehiculo);
    }

    public void insertarRevision(Revision revision) throws TallerMecanicoExcepcion {
        modelo.insertar(revision);
    }

    public Cliente buscarCliente(Cliente cliente){
        return modelo.buscar(cliente);
    }

    public Vehiculo buscarVehiculo(Vehiculo vehiculo){
        return modelo.buscar(vehiculo);
    }

    public Revision buscarRevision(Revision revision){
        return modelo.buscar(revision);
    }

    public Cliente modificarCliente(Cliente cliente,String nombre, String telefono) throws TallerMecanicoExcepcion {
        return modelo.modificar(cliente, nombre, telefono);
    }

    public Revision anadirHoras(Revision revision, int horas) throws TallerMecanicoExcepcion {
        return modelo.anadirHoras(revision, horas);
    }

    public Revision anadirPrecioMaterial(Revision revision, float precioMaterial) throws TallerMecanicoExcepcion {
        return modelo.anadirPrecioMaterial(revision,precioMaterial);
    }

    public Revision cerrarRevision(Revision revision, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        return modelo.cerrar(revision,fechaFin);
    }

    public void borrarCliente(Cliente cliente) throws TallerMecanicoExcepcion {
        modelo.borrar(cliente);
    }

    public void borrarVehiculo(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        modelo.borrar(vehiculo);
    }

    public void borrarRevision(Revision revision) throws TallerMecanicoExcepcion {
        modelo.borrar(revision);
    }

    public List<Cliente> listarClientes(){
        return modelo.getClientes();
    }

    public List<Vehiculo> listarVehiculos(){
        return modelo.getVehiculos();
    }

    public List<Revision> listarRevisiones(){
        return modelo.getRevisiones();
    }

    public List<Revision> listarRevisionesCliente(Cliente cliente){
        return modelo.getRevisiones(cliente);
    }

    public List<Revision> listarRevisionesVehiculo(Vehiculo vehiculo){
        return modelo.getRevisiones(vehiculo);
    }
}
