package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;

import java.util.ArrayList;
import java.util.List;

public class VistaTexto implements Vista {
    private GestorEventos gestorEventos;

    @Override
    public GestorEventos getGestorEventos() {
        return new GestorEventos();
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
            System.out.printf("El evento ( %s ) ha fallado", evento);
        }
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
    public void mostrarClientes(Cliente[] clientes){
        Consola.mostrarCabecera("Lista de clientes");
        List<Cliente> listaClientes = new ArrayList<>();
        for (Cliente cliente : clientes){
            listaClientes.add(cliente);
        }
        if (listaClientes.isEmpty()){
            System.out.println("No hay clientes todavía.");
        } else {
            for(Cliente cliente : clientes){
                System.out.println(cliente);
            }
        }
    }

    @Override
    public void mostrarVehiculos(Vehiculo[] vehiculos){
        Consola.mostrarCabecera("Lista de vehículos");
        List<Vehiculo> listaVehiculos = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos){
            listaVehiculos.add(vehiculo);
        }
        if (listaVehiculos.isEmpty()){
            System.out.println("No hay vehículos todavía.");
        } else {
            for(Vehiculo vehiculo : listaVehiculos){
                System.out.println(vehiculo);
            }
        }
    }

    @Override
    public void mostrarTrabajos(Trabajo[] trabajos){
        Consola.mostrarCabecera("Lista de revisiones");
        List<Trabajo> listaTrabajos = new ArrayList<>();
        for (Trabajo trabajo : trabajos){
            listaTrabajos.add(trabajo);
        }
        if (listaTrabajos.isEmpty()){
            System.out.println("No hay revisiones todavía.");
        } else {
            for(Trabajo trabajo : listaTrabajos){
                System.out.println(trabajo);
            }
        }
    }
}
