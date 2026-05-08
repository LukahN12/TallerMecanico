package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class VistaTexto implements Vista {
    private GestorEventos gestorEventos = new GestorEventos(Evento.values());

    @Override
    public GestorEventos getGestorEventos() {
        return gestorEventos;
    }

    @Override
    public void comenzar(){
        Evento evento;
        do{
            Consola.mostrarMenu();
            evento = Consola.elegirOpcion();
            ejecutar(evento);
        } while(evento != Evento.SALIR);
    }

    @Override
    public void terminar(){
        System.out.println("Chao!");
    }

    @Override
    public void ejecutar(Evento evento) {
        try {
            gestorEventos.notificar(evento);
        } catch (Exception e) {
            System.out.printf("ERROR: %s %n", e.getMessage());
        }
    }

    @Override
    public void notificarResultado(Evento evento, String texto, Boolean exito){
        if (exito){
            System.out.println(texto);
        } else {
            System.out.printf("ERROR: %s", texto);
        }
    }

    @Override
    public Cliente leerCliente() {
        String nombre = Consola.leerCadena("¿Como se llama el cliente? ");
        String dni = Consola.leerCadena("¿Cual es el dni del cliente? ");
        String telefono = Consola.leerCadena("¿Cual es el teléfono del cliente? ");
        return new Cliente(nombre, dni, telefono);
    }

    @Override
    public Cliente leerClienteDni() {
        return Cliente.get(Consola.leerCadena("¿Que DNI quieres introducir? "));
    }

    @Override
    public String leerNuevoNombre() {
        return Consola.leerCadena("¿Que nombre quieres introducir? ");
    }

    @Override
    public String leerNuevoTelefono() {
        return Consola.leerCadena("¿Que teléfono quieres introducir? ");
    }

    @Override
    public Vehiculo leerVehiculo() {
        return new Vehiculo(Consola.leerCadena("¿Que marca quieres introducir? "), Consola.leerCadena("¿Que modelo quieres introducir? "), Consola.leerCadena("¿Que matricula quieres introducir? "));
    }

    @Override
    public Vehiculo leerVehiculoMatricula() {
        return Vehiculo.get(Consola.leerCadena("¿Que matricula quieres introducir? "));
    }

    @Override
    public Trabajo leerRevision() {
        return Revision.get(Vehiculo.get(Consola.leerCadena("¿De que matricula quieres buscar la revision?")));
    }

    @Override
    public Trabajo leerMecanico() {
        return Mecanico.get(Vehiculo.get(Consola.leerCadena("¿De que matricula quieres buscar el " +
                "mecánico?")));
    }

    @Override
    public Trabajo leerTrabajoVehiculo() {
        return Trabajo.get(Vehiculo.get(Consola.leerCadena("¿De que matricula quieres buscar su trabajo?")));
    }

    @Override
    public int leerHoras() {
        return Consola.leerEntero("¿Cuantas horas quieres introducir? ");
    }

    @Override
    public float leerPrecioMaterial() {
        return Consola.leerReal("¿Que precio quieres introducir? ");
    }

    @Override
    public LocalDate leerFechaCierre() {
        return Consola.leerFecha("¿Que fecha de cierre quieres introducir? ");
    }

    @Override
    public void mostrarCliente(Cliente cliente){
        Consola.mostrarCabecera("Cliente elegido");

    }

    @Override
    public void mostrarVehiculo(Vehiculo vehiculo){
        Consola.mostrarCabecera("Vehiculo elegido");

    }

    @Override
    public void mostrarTrabajo(Trabajo trabajo){
        Consola.mostrarCabecera("Trabajo elegido");

    }

    @Override
    public void mostrarClientes(List<Cliente> clientes){
        Consola.mostrarCabecera("Lista de clientes");
        clientes.sort(Comparator.comparing(Cliente::getNombre).thenComparing(Cliente::getDni));
        if (clientes.isEmpty()){
            System.out.println("No hay clientes todavía.");
        } else {
            for(Cliente cliente : clientes){
                System.out.println(cliente);
            }
        }
    }

    @Override
    public void mostrarVehiculos(List<Vehiculo> vehiculos){
        Consola.mostrarCabecera("Lista de vehículos");
        vehiculos.sort(Comparator.comparing(Vehiculo::marca).thenComparing(Vehiculo::modelo).thenComparing(Vehiculo::matricula));
        if (vehiculos.isEmpty()){
            System.out.println("No hay vehículos todavía.");
        } else {
            for(Vehiculo vehiculo : vehiculos){
                System.out.println(vehiculo);
            }
        }
    }

    @Override
    public void mostrarTrabajos(List<Trabajo> trabajos){
        Consola.mostrarCabecera("Lista de trabajos");
        Comparator<Cliente> comparadorCliente = Comparator.comparing(Cliente::getNombre).thenComparing(Cliente::getDni);
        trabajos.sort(Comparator.comparing(Trabajo::getFechaInicio).thenComparing(Trabajo::getCliente,comparadorCliente));
        if (trabajos.isEmpty()){
            System.out.println("No hay trabajos todavía.");
        } else {
            for(Trabajo trabajo : trabajos){
                System.out.println(trabajo);
            }
        }
    }

    public LocalDate leerMes(){
        return Consola.leerFecha("Que mes quieres elegir?");
    }

    public void mostrarEstadisticasMensuales(Map<TipoTrabajo,Integer> estadisticas){
        Consola.mostrarCabecera("Estadisticas del mes");
        System.out.println(estadisticas);
    }
}
