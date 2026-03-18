package org.iesalandalus.programacion.tallermecanico.modelo;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Clientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Revisiones;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Vehiculos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Modelo {

    private Clientes clientes;
    private Vehiculos vehiculos;
    private Revisiones revisiones;

    public void comenzar(){
        clientes = new Clientes();
        vehiculos = new Vehiculos();
        revisiones = new Revisiones();
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

    public void insertar(Revision revision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision,"Necesitas una revisión que insertar");
        Revision revision1 = new Revision(clientes.buscar(revision.getCliente()),vehiculos.buscar(revision.getVehiculo()),revision.getFechaFin());
        revisiones.insertar(revision1);
    }

    public Cliente buscar(Cliente cliente){
        Objects.requireNonNull(cliente,"Necesitas un cliente que buscar");
        return new Cliente(clientes.buscar(cliente));
    }

    public Vehiculo buscar(Vehiculo vehiculo){
        Objects.requireNonNull(vehiculo,"Necesitas un vehiculo que buscar");
        return Objects.requireNonNull(vehiculos.buscar(vehiculo),"El vehiculo a buscar no existe.") ;
    }

    public Revision buscar(Revision revision) {
        Objects.requireNonNull(revision,"Necesitas una revisión que buscar");
        return new Revision(Objects.requireNonNull(revisiones.buscar(revision), "La revisión a buscar no existe."));

    }

    public Cliente modificar(Cliente cliente,String nombre,String telefono) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente,"Necesitas un cliente que modificar");
        return clientes.modificar(cliente,nombre,telefono);
    }

    public Revision anadirHoras(Revision revision,int horas) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision,"Necesitas una revisión a la que añadirle horas.");
        return revisiones.anadirHoras(revision,horas);
    }

    public Revision anadirPrecioMaterial(Revision revision, float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision,"Necesitas una revisión a la que añadirle horas.");
        return revisiones.anadirPrecioMaterial(revision,precioMaterial);
    }

    public Revision cerrar(Revision revision,LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision,"Necesitas una revisión a la que darle cierre.");
        Objects.requireNonNull(revision,"Necesitas una fecha para el cierre");
        return revisiones.cerrar(revision,fechaFin);
    }

    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente,"Necesitas un cliente que borrar.");
        List<Revision> revisionesCliente = revisiones.get(cliente);
        for (Revision revision : revisionesCliente){
            revisiones.borrar(revision);
        }

        clientes.borrar(cliente);
    }

    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo,"Necesitar tener un vehiculo que borrar");
        List<Revision> revisionesVehiculo = revisiones.get(vehiculo);
        for (Revision revision : revisionesVehiculo){
            revisiones.borrar(revision);
        }
        vehiculos.borrar(vehiculo);
    }

    public void borrar(Revision revision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision,"Necesitas tener una revisión que borrar");
        revisiones.borrar(revision);
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

    public List<Revision> getRevisiones(){
        List<Revision> coleccionRevisiones = new ArrayList<>();
        for (Revision revision : revisiones.get()){
            Revision revision1 = new Revision(revision);
            coleccionRevisiones.add(revision1);
        }

        return coleccionRevisiones;
    }

    public List<Revision> getRevisiones(Cliente cliente){
        List<Revision> coleccionRevisiones = new ArrayList<>();
        for (Revision revision : revisiones.get(cliente)){
            coleccionRevisiones.add(new Revision(revision));
        }

        return coleccionRevisiones;
    }

    public List<Revision> getRevisiones(Vehiculo vehiculo){
        List<Revision> coleccionRevisiones = new ArrayList<>();
        for (Revision revision : revisiones.get(vehiculo)){
            coleccionRevisiones.add(new Revision(revision));
        }

        return coleccionRevisiones;
    }
}
