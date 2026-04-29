package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;

import java.util.Objects;

public class Controlador implements IControlador {
    private Modelo modelo;
    private Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        Objects.requireNonNull(vista,"La vista no puede ser nula");
        Objects.requireNonNull(modelo,"El modelo no puede ser nulo");
        vista.getGestorEventos().suscribir(this,Evento.values());
        this.modelo = modelo;
        this.vista = vista;
    }

    public void comenzar(){
        modelo.comenzar();
        vista.comenzar();
    }

    public void terminar(){
        modelo.terminar();
        vista.terminar();
    }

    @Override
    public void actualizar(Evento evento) {

    }
}
