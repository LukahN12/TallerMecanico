package org.iesalandalus.programacion.tallermecanico.modelo.negocio.fichero;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import java.time.LocalDate;
import java.util.*;

public class Trabajos implements ITrabajos {

    private List<Trabajo> coleccionTrabajos;
    private static Trabajos instancia;

    private Trabajos() {
        coleccionTrabajos = new ArrayList<>();
    }

    private Trabajos getInstancia(){
        if (instancia ==  null){
            instancia = new Trabajos();
        }
        return instancia;
    }

    @Override
    public List<Trabajo> get() {
        return new ArrayList<>(coleccionTrabajos);
    }

    @Override
    public List<Trabajo> get(Cliente cliente) {

        List<Trabajo> coleccionResultante;
        coleccionResultante = new ArrayList<>();

        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getCliente().equals(cliente)) {
                coleccionResultante.add(trabajo);
            }
        }

        return coleccionResultante;
    }

    @Override
    public List<Trabajo> get(Vehiculo vehiculo) {

        List<Trabajo> coleccionResultante;
        coleccionResultante = new ArrayList<>();

        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getVehiculo().equals(vehiculo)) {
                coleccionResultante.add(trabajo);
            }
        }

        return coleccionResultante;
    }

    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede insertar un trabajo nulo.");
        comprobarRevision(trabajo.getCliente(), trabajo.getVehiculo(), trabajo.getFechaInicio());
        coleccionTrabajos.add(trabajo);
    }

    private void comprobarRevision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaRevision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        Objects.requireNonNull(vehiculo, "El vehiculo no puede ser nulo");

        for (Trabajo trabajo : coleccionTrabajos) {
            if (!trabajo.estaCerrado()) {
                if (trabajo.getCliente().equals(cliente)) {
                    throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo en curso.");
                }
                if (trabajo.getVehiculo().equals(vehiculo)) {
                    throw new TallerMecanicoExcepcion("El vehículo está actualmente en el taller.");
                }
            }

            if (trabajo.estaCerrado()) {
                if (!fechaRevision.isAfter(trabajo.getFechaFin())) {
                    if (trabajo.getCliente().equals(cliente)) {
                        throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo posterior.");
                    }
                    if (trabajo.getVehiculo().equals(vehiculo)) {
                        throw new TallerMecanicoExcepcion("El vehículo tiene otro trabajo posterior.");
                    }
                }
            }
        }
    }

    private Trabajo getTrabajoAbierto(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Trabajo trabajoResultado = null;

        for (Trabajo trabajo : coleccionTrabajos){
            if (!trabajo.estaCerrado() && trabajo.getVehiculo().equals(vehiculo)) {
                trabajoResultado = trabajo;
            }
        }
        if (trabajoResultado == null){
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }


        return trabajoResultado;
    }

    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"No puedo añadir horas a un trabajo nulo.");
        Trabajo trabajo1 = getTrabajoAbierto(trabajo.getVehiculo());
        trabajo1.anadirHoras(horas);
        return trabajo1;
    }

    @Override
    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"No puedo añadir precio del material a un trabajo nulo.");
        Trabajo trabajo1 = getTrabajoAbierto(trabajo.getVehiculo());
        if (!(trabajo1 instanceof Mecanico mecanico)){
            throw new TallerMecanicoExcepcion("No se puede añadir precio al material para este tipo de trabajos.");
        }
        mecanico.anadirPrecioMaterial(precioMaterial);
        return trabajo1;
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"No puedo cerrar un trabajo nulo.");
        Trabajo trabajo1 = getTrabajoAbierto(trabajo.getVehiculo());
        trabajo1.cerrar(fechaFin);
        return trabajo1;
    }

    @Override
    public Trabajo buscar(Trabajo trabajo){
        Objects.requireNonNull(trabajo, "No se puede buscar un trabajo nulo.");
        int indice = coleccionTrabajos.indexOf(trabajo);
        return (indice != -1 ? coleccionTrabajos.get(indice) : null);
    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede borrar un trabajo nulo.");
        Trabajo buscado = buscar(trabajo);

        if (!coleccionTrabajos.contains(buscado)){
            throw new TallerMecanicoExcepcion("No existe ningún trabajo igual.");
        }

        coleccionTrabajos.remove(buscado);
    }

    public Map<TipoTrabajo,Integer> getEstadisticasMensuales(LocalDate mes){
        Map<TipoTrabajo,Integer> mapa = inicializarEstadisticas();

        for (Trabajo trabajo : coleccionTrabajos){
            if (trabajo.getFechaInicio().getMonth().equals(mes.getMonth()) && trabajo.getFechaInicio().getYear() == mes.getYear()){
                TipoTrabajo tipo = TipoTrabajo.get(trabajo);
                mapa.put(tipo,mapa.get(tipo) + 1);
            }
        }

        return mapa;
    }

    private Map<TipoTrabajo,Integer> inicializarEstadisticas(){
        Map<TipoTrabajo,Integer> mapa = new EnumMap<>(TipoTrabajo.class);
        for (TipoTrabajo tipoTrabajo : TipoTrabajo.values()){
            mapa.put(tipoTrabajo,0);
        }
        return mapa;
    }

    @Override
    public void comenzar() {

    }

    @Override
    public void terminar() {

    }


}
