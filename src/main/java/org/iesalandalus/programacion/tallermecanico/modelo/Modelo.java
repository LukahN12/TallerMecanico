package org.iesalandalus.programacion.tallermecanico.modelo;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.Clientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.Trabajos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.Vehiculos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Modelo {

    private Clientes clientes;
    private Vehiculos vehiculos;
    private Trabajos trabajos;

    public void comenzar(){
        clientes = new Clientes();
        vehiculos = new Vehiculos();
        trabajos = new Trabajos();
    }

    public void terminar(){
        System.out.println("Se termino de crear el modelo.");
    }

    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "No puedes insertar un cliente nulo.");
        Cliente cliente1 = new Cliente(cliente);
        clientes.insertar(cliente1);
    }

    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        vehiculos.insertar(vehiculo);
    }

    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"Necesitas una revisión que insertar");
        Trabajo trabajo1 = new Trabajo(clientes.buscar(trabajo.getCliente()),vehiculos.buscar(trabajo.getVehiculo()), trabajo.getFechaInicio());
        trabajos.insertar(trabajo1);
    }

    public Cliente buscar(Cliente cliente){
        Objects.requireNonNull(cliente,"Necesitas un cliente que buscar");
        return new Cliente(clientes.buscar(cliente));
    }

    public Vehiculo buscar(Vehiculo vehiculo){
        Objects.requireNonNull(vehiculo,"Necesitas un vehiculo que buscar");
        return Objects.requireNonNull(vehiculos.buscar(vehiculo),"El vehiculo a buscar no existe.") ;
    }

    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo,"Necesitas una revisión que buscar");
        return new Trabajo(Objects.requireNonNull(trabajos.buscar(trabajo), "La revisión a buscar no existe."));

    }

    public Cliente modificar(Cliente cliente,String nombre,String telefono) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente,"Necesitas un cliente que modificar");
        return clientes.modificar(cliente,nombre,telefono);
    }

    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"Necesitas una revisión a la que añadirle horas.");
        return trabajos.anadirHoras(trabajo,horas);
    }

    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"Necesitas una revisión a la que añadirle horas.");
        return trabajos.anadirPrecioMaterial(trabajo,precioMaterial);
    }

    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"Necesitas una revisión a la que darle cierre.");
        Objects.requireNonNull(trabajo,"Necesitas una fecha para el cierre");
        return trabajos.cerrar(trabajo,fechaFin);
    }

    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente,"Necesitas un cliente que borrar.");
        List<Trabajo> revisionesCliente = trabajos.get(cliente);
        for (Trabajo trabajo : revisionesCliente){
            trabajos.borrar(trabajo);
        }

        clientes.borrar(cliente);
    }

    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo,"Necesitar tener un vehiculo que borrar");
        List<Trabajo> revisionesVehiculo = trabajos.get(vehiculo);
        for (Trabajo trabajo : revisionesVehiculo){
            trabajos.borrar(trabajo);
        }
        vehiculos.borrar(vehiculo);
    }

    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"Necesitas tener una revisión que borrar");
        trabajos.borrar(trabajo);
    }

    public List<Cliente> getClientes(){
        List<Cliente> coleccionClientes = new ArrayList<>();
        for (Cliente cliente : clientes.get()){
            Cliente cliente1 = new Cliente(cliente);
            coleccionClientes.add(cliente1);
        }

        return coleccionClientes;
    }

    public List<Vehiculo> getVehiculos(){
        List<Vehiculo> coleccionVehiculos = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos.get()){
            coleccionVehiculos.add(vehiculo);
        }

        return coleccionVehiculos;
    }

    public List<Trabajo> getRevisiones(){
        List<Trabajo> coleccionRevisiones = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get()){
            Trabajo trabajo1 = new Trabajo(trabajo);
            coleccionRevisiones.add(trabajo1);
        }

        return coleccionRevisiones;
    }

    public List<Trabajo> getRevisiones(Cliente cliente){
        List<Trabajo> coleccionRevisiones = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(cliente)){
            coleccionRevisiones.add(new Trabajo(trabajo));
        }

        return coleccionRevisiones;
    }

    public List<Trabajo> getRevisiones(Vehiculo vehiculo){
        List<Trabajo> coleccionRevisiones = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(vehiculo)){
            coleccionRevisiones.add(new Trabajo(trabajo));
        }

        return coleccionRevisiones;
    }
}
