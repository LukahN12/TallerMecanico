package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;
import org.iesalandalus.programacion.tallermecanico.vista.texto.Consola;

import java.time.LocalDate;

public interface Vista {
    static Cliente leerCliente() {
        String nombre = Consola.leerCadena("¿Como se llama el cliente? ");
        String dni = Consola.leerCadena("¿Cual es el dni del cliente? ");
        String telefono = Consola.leerCadena("¿Cual es el teléfono del cliente? ");
        return new Cliente(nombre, dni, telefono);
    }

    static Cliente leerClienteDni() {
        return Cliente.get(Consola.leerCadena("¿Que DNI quieres introducir? "));
    }

    static String leerNuevoNombre() {
        return Consola.leerCadena("¿Que nombre quieres introducir? ");
    }

    static String leerNuevoTelefono() {
        return Consola.leerCadena("¿Que teléfono quieres introducir? ");
    }

    static Vehiculo leerVehiculo() {
        return new Vehiculo(Consola.leerCadena("¿Que marca quieres introducir? "), leerCadena("¿Que modelo quieres introducir? "), leerCadena("¿Que matricula quieres introducir? "));
    }

    static Vehiculo leerVehiculoMatricula() {
        return Vehiculo.get(Consola.leerCadena("¿Que matricula quieres introducir? "));
    }

    static Trabajo leerRevision() {
        return Revision.get(Vehiculo.get(Consola.leerCadena("¿De que matricula quieres buscar la revision?")));
    }

    static Trabajo leerMecanico() {
        return Mecanico.get(Vehiculo.get(Consola.leerCadena("¿De que matricula quieres buscar el " +
                "mecánico?")));
    }

    static Trabajo leerTrabajoVehiculo() {
        return Trabajo.get(Vehiculo.get(Consola.leerCadena("¿De que matricula quieres buscar su trabajo?")));
    }

    static int leerHoras() {
        return Consola.leerEntero("¿Cuantas horas quieres introducir? ");
    }

    static float leerPrecioMaterial() {
        return Consola.leerReal("¿Que precio quieres introducir? ");
    }

    static LocalDate leerFechaCierre() {
        return Consola.leerFecha("¿Que fecha de cierre quieres introducir? ");
    }

    GestorEventos getGestorEventos();

    void comenzar();

    void terminar();

    void notificarResultado(Evento evento, String texto, Boolean exito);

    void mostrarCliente(Cliente cliente);

    void mostrarVehiculo(Vehiculo vehiculo);

    void mostrarTrabajo(Trabajo trabajo);

    void mostrarClientes(Cliente[] clientes);

    void mostrarVehiculos(Vehiculo[] vehiculos);

    void mostrarTrabajos(Trabajo[] trabajos);
}
