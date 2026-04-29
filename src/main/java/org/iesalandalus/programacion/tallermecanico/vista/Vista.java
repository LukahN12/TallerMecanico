package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;

import java.time.LocalDate;

public interface Vista {
    GestorEventos getGestorEventos();

    void comenzar();

    void terminar();

    void ejecutar(Evento evento);

    void notificarResultado(Evento evento, String texto, Boolean exito);

    Cliente leerCliente();

    Cliente leerClienteDni();

    String leerNuevoNombre();

    String leerNuevoTelefono();

    Vehiculo leerVehiculo();

    Vehiculo leerVehiculoMatricula();

    Trabajo leerRevision();

    Trabajo leerMecanico();

    Trabajo leerTrabajoVehiculo();

    int leerHoras();

    float leerPrecioMaterial();

    LocalDate leerFechaCierre();

    void mostrarCliente(Cliente cliente);

    void mostrarVehiculo(Vehiculo vehiculo);

    void mostrarTrabajo(Trabajo trabajo);

    void mostrarClientes(Cliente[] clientes);

    void mostrarVehiculos(Vehiculo[] vehiculos);

    void mostrarTrabajos(Trabajo[] trabajos);
}
