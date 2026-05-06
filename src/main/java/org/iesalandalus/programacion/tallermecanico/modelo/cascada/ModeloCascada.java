package org.iesalandalus.programacion.tallermecanico.modelo.cascada;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.fichero.Trabajos;

import java.time.LocalDate;
import java.util.*;

public class ModeloCascada implements Modelo {

    private IClientes clientes;
    private IVehiculos vehiculos;
    private ITrabajos trabajos;

    public ModeloCascada(FabricaFuenteDatos fabricaFuenteDatos) {
        IFuenteDatos fuenteDatos = fabricaFuenteDatos.crear();
        clientes = fuenteDatos.crearClientes();
        vehiculos = fuenteDatos.crearVehiculos();
        trabajos = fuenteDatos.crearTrabajos();
    }

    @Override
    public void comenzar(){
        System.out.println("Modelo comenzado.");
    }

    @Override
    public void terminar(){
        System.out.println("Se termino de crear el modelo.");
    }

    @Override
    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        clientes.insertar(new Cliente(cliente));
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        vehiculos.insertar(vehiculo);
    }

    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"Necesitas una revisión que insertar");
        Cliente cliente = clientes.buscar(trabajo.getCliente());
        Vehiculo vehiculo = vehiculos.buscar(trabajo.getVehiculo());
        if (trabajo instanceof Mecanico mecanico){
            trabajo = new Mecanico(cliente,vehiculo,mecanico.getFechaInicio());
        } else if (trabajo instanceof Revision revision) {
            trabajo = new Revision(cliente,vehiculo,revision.getFechaInicio());
        }
        trabajos.insertar(trabajo);
    }

    @Override
    public Cliente buscar(Cliente cliente){
        Objects.requireNonNull(cliente,"Necesitas un cliente que buscar");
        return new Cliente(clientes.buscar(cliente));
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo){
        Objects.requireNonNull(vehiculo,"Necesitas un vehiculo que buscar");
        return Objects.requireNonNull(vehiculos.buscar(vehiculo),"El vehiculo a buscar no existe.") ;
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo,"Necesitas una revisión que buscar");
        return Trabajo.get(trabajos.buscar(trabajo).getVehiculo());

    }

    @Override
    public Cliente modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente,"Necesitas un cliente que modificar");
        return clientes.modificar(cliente,nombre,telefono);
    }

    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"Necesitas una revisión a la que añadirle horas.");
        return trabajos.anadirHoras(trabajo,horas);
    }

    @Override
    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"Necesitas una revisión a la que añadirle horas.");
        return trabajos.anadirPrecioMaterial(trabajo,precioMaterial);
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"Necesitas una revisión a la que darle cierre.");
        Objects.requireNonNull(trabajo,"Necesitas una fecha para el cierre");
        return trabajos.cerrar(trabajo,fechaFin);
    }

    @Override
    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente,"Necesitas un cliente que borrar.");
        List<Trabajo> revisionesCliente = trabajos.get(cliente);
        for (Trabajo trabajo : revisionesCliente){
            trabajos.borrar(trabajo);
        }
        clientes.borrar(cliente);
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo,"Necesitar tener un vehiculo que borrar");
        List<Trabajo> revisionesVehiculo = trabajos.get(vehiculo);
        for (Trabajo trabajo : revisionesVehiculo){
            trabajos.borrar(trabajo);
        }
        vehiculos.borrar(vehiculo);
    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"Necesitas tener una revisión que borrar");
        trabajos.borrar(trabajo);
    }

    @Override
    public List<Cliente> getClientes(){
        List<Cliente> coleccionClientes = new ArrayList<>();
        for (Cliente cliente : clientes.get()){
            Cliente cliente1 = new Cliente(cliente);
            coleccionClientes.add(cliente1);
        }

        return coleccionClientes;
    }

    @Override
    public List<Vehiculo> getVehiculos(){
        List<Vehiculo> coleccionVehiculos = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos.get()){
            coleccionVehiculos.add(vehiculo);
        }

        return coleccionVehiculos;
    }

    @Override
    public List<Trabajo> getRevisiones(){
        List<Trabajo> coleccionRevisiones = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get()){
            coleccionRevisiones.add(Trabajo.copiar(trabajo));
        }

        return coleccionRevisiones;
    }

    @Override
    public List<Trabajo> getRevisiones(Cliente cliente){
        List<Trabajo> coleccionRevisiones = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(cliente)){
            coleccionRevisiones.add(Trabajo.copiar(trabajo));
        }

        return coleccionRevisiones;
    }

    @Override
    public List<Trabajo> getRevisiones(Vehiculo vehiculo){
        List<Trabajo> coleccionRevisiones = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(vehiculo)){
            coleccionRevisiones.add(Trabajo.copiar(trabajo));
        }

        return coleccionRevisiones;
    }

    @Override
    public List<Trabajo> getTrabajos() {
        List<Trabajo> coleccionTrabajos = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get()){
            coleccionTrabajos.add(Trabajo.copiar(trabajo));
        }

        return coleccionTrabajos;
    }

    @Override
    public List<Trabajo> getTrabajos(Cliente cliente) {
        List<Trabajo> coleccionTrabajos = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(cliente)){
            coleccionTrabajos.add(Trabajo.copiar(trabajo));
        }

        return coleccionTrabajos;
    }

    @Override
    public List<Trabajo> getTrabajos(Vehiculo vehiculo) {
        List<Trabajo> coleccionTrabajos = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(vehiculo)){
            coleccionTrabajos.add(Trabajo.copiar(trabajo));
        }

        return coleccionTrabajos;
    }

    @Override
    public Map<TipoTrabajo, Integer> getEstadisticasMensuales(LocalDate mes) {
        int revisiones = 0;
        int trabajosMecanicos = 0;
        Map<TipoTrabajo,Integer> mapa = inicializarEstadisticas();

        for (Trabajo trabajo : getTrabajos()){
            if (trabajo.getFechaInicio().getMonth().equals(mes.getMonth()) && trabajo.getFechaInicio().getYear() == mes.getYear()){
                if (trabajo instanceof Revision){
                    revisiones += 1;
                } else {
                    trabajosMecanicos += 1;
                }
            }
        }

        mapa.put(TipoTrabajo.REVISION,revisiones);
        mapa.put(TipoTrabajo.MECANICO,trabajosMecanicos);

        return mapa;
    }

    private Map<TipoTrabajo,Integer> inicializarEstadisticas(){
        Map<TipoTrabajo,Integer> mapa = new EnumMap<>(TipoTrabajo.class);
        for (TipoTrabajo tipoTrabajo : TipoTrabajo.values()){
            mapa.put(tipoTrabajo,0);
        }
        return mapa;
    }
}
