package org.iesalandalus.programacion.tallermecanico;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;

public class Main {
    public static void main(String[] args) {
        Vista vista1 = new Vista();
        Modelo modelo1 = new Modelo();
        Controlador controlador1 = new Controlador(modelo1, vista1);
        controlador1.comenzar();
    }
}
