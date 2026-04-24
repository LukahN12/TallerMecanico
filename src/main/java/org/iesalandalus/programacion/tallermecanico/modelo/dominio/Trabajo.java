package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public abstract class Trabajo {
    private static final float PRECIO_DIA = 10;
    static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Vehiculo vehiculo;
    private Cliente cliente;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int horas;

    public Trabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio){
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        setFechaInicio(fechaInicio);
    }

    public Trabajo(Trabajo trabajo){
        Objects.requireNonNull(trabajo,"El trabajo no puede ser nulo.");
        cliente = new Cliente(trabajo.cliente);
        vehiculo = trabajo.vehiculo;
        fechaInicio = trabajo.fechaInicio;
        fechaFin = trabajo.fechaFin;
        horas = trabajo.horas;
    }

    public static Trabajo copiar(Trabajo trabajo){
        Objects.requireNonNull(trabajo,"El trabajo no puede ser nulo.");
        Trabajo trabajo1 = null;
        if (trabajo instanceof Revision revision) {
            trabajo1 = new Revision(revision);
        } else if (trabajo instanceof Mecanico mecanico){
            trabajo1 = new Mecanico((mecanico));
        }
        return trabajo1;
    }

    public static Trabajo get(Vehiculo vehiculo){
        return new Revision(Cliente.get(String.valueOf(1111111)), vehiculo,LocalDate.of(1999,10,11));
    }

    public Cliente getCliente(){
        return cliente;
    }

    public Vehiculo getVehiculo(){
        return vehiculo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    private void setFechaInicio(LocalDate fechaInicio) {
        Objects.requireNonNull(fechaInicio,"La fecha de inicio no puede ser nula.");
        if (LocalDate.now().isBefore(fechaInicio)){
            throw new IllegalArgumentException("La fecha de inicio no puede ser futura.");
        }
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    private void setFechaFin(LocalDate fechaFin) {
        Objects.requireNonNull(fechaFin, "La fecha de fin no puede ser nula.");
        if (LocalDate.now().isBefore(fechaFin)){
            throw new IllegalArgumentException("La fecha de fin no puede ser futura.");
        }
        if (fechaFin.isBefore(fechaInicio)){
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        this.fechaFin = fechaFin;
    }

    public int getHoras() {
        return horas;
    }

    public boolean estaCerrado(){
        return fechaFin != null;
    }

    public void anadirHoras(int horas) throws TallerMecanicoExcepcion {
        if (estaCerrado()){
            throw new TallerMecanicoExcepcion("No se puede añadir horas, ya que el trabajo está cerrado.");
        }
        if (horas <= 0){
            throw new IllegalArgumentException("Las horas a añadir deben ser mayores que cero.");
        }
        this.horas += horas;
    }

    public void cerrar(LocalDate fechaFin) throws TallerMecanicoExcepcion {
        if (estaCerrado()){
            throw new TallerMecanicoExcepcion("El trabajo ya está cerrado.");
        }
        setFechaFin(fechaFin);
    }

    private float getDias(){
        return ChronoUnit.DAYS.between(fechaInicio,fechaFin);
    }

    public float getPrecio(){
        return (estaCerrado()) ? getPrecioFijo() + getPrecioEspecifico() : 0;
    }

    private float getPrecioFijo() {
        return getDias() * PRECIO_DIA;
    }

    public abstract float getPrecioEspecifico();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Trabajo trabajo = (Trabajo) o;
        return Objects.equals(vehiculo, trabajo.vehiculo) && Objects.equals(cliente, trabajo.cliente) && Objects.equals(fechaInicio, trabajo.fechaInicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehiculo, cliente, fechaInicio);
    }
}
