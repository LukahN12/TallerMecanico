package org.iesalandalus.programacion.tallermecanico.modelo.negocio.fichero;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vehiculos implements IVehiculos {

    private List<Vehiculo> coleccionVehiculos;
    private static Vehiculos instancia;

    private Vehiculos() {
        coleccionVehiculos = new ArrayList<>();
    }

    private Vehiculos getInstancia(){
        if (instancia ==  null){
            instancia = new Vehiculos();
        }
        return instancia;
    }

    @Override
    public List<Vehiculo> get() {
        return coleccionVehiculos;
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo, "No se puede insertar un vehículo nulo.");
        if (coleccionVehiculos.contains(vehiculo)){
            throw new TallerMecanicoExcepcion("Ya existe un vehículo con esa matrícula.");
        }

        coleccionVehiculos.add(vehiculo);
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo){
        Objects.requireNonNull(vehiculo, "No se puede buscar un vehículo nulo.");
        int indice = coleccionVehiculos.indexOf(vehiculo);
        return (indice != -1 ? coleccionVehiculos.get(indice) : null);
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo, "No se puede borrar un vehículo nulo.");
        Vehiculo buscado = buscar(vehiculo);

        if (!coleccionVehiculos.contains(buscado)){
            throw new TallerMecanicoExcepcion("No existe ningún vehículo con esa matrícula.");
        }

        coleccionVehiculos.remove(buscado);
    }

    @Override
    public void comenzar() {

    }

    @Override
    public void terminar() {

    }
}
