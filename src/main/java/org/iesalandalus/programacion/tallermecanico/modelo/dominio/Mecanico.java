package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.time.LocalDate;
import java.util.Objects;

public class Mecanico extends Trabajo {
    private static float FACTOR_HORA = 30f;
    private static float FACTOR_PRECIO_MATERIAL = 1.5f;
    private float precioMaterial;

    public Mecanico(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        super(cliente, vehiculo, fechaInicio);
    }

    public Mecanico(Mecanico mecanico) {
        super(mecanico);
        precioMaterial = mecanico.getPrecioMaterial();
    }

    public void anadirPrecioMaterial(float precioMaterial) throws TallerMecanicoExcepcion {
        if (estaCerrado()){
            throw new TallerMecanicoExcepcion("No se puede añadir precio del material, ya que el trabajo mecánico está cerrado.");
        }
        if (precioMaterial <= 0){
            throw new IllegalArgumentException("El precio del material a añadir debe ser mayor que cero.");
        }
        this.precioMaterial += precioMaterial;
    }

    public float getPrecioMaterial() {
        return precioMaterial;
    }

    @Override
    public float getPrecioEspecifico() {
        return getHoras() * FACTOR_HORA + precioMaterial * FACTOR_PRECIO_MATERIAL;
    }

    @Override
    public String toString() {
        return String.format((estaCerrado()) ? String.format("Mecánico -> %s - %s (%s - %s): %s horas,%5.2f € en material, %5.2f € total", getCliente(), getVehiculo(), getFechaInicio().format(FORMATO_FECHA), getFechaFin().format(FORMATO_FECHA), getHoras(),getPrecioMaterial() ,getPrecio()) : String.format("Mecánico -> %s - %s (%s - ): %s horas,%5.2f € en material",  getCliente(), getVehiculo(), getFechaInicio().format(FORMATO_FECHA), getHoras(),getPrecio()));
    }
}
