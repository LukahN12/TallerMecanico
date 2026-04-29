package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorEventos {
    static Map<Evento, List<ReceptorEventos>> receptores = new HashMap<>();

    public void GestorEventos(Evento... eventos){
        for (Evento evento : Evento.values()) {
            receptores.put(evento, new ArrayList<>());
        }
    }

    public void suscribir(ReceptorEventos receptor,Evento... eventos){
        for (Evento evento : Evento.values()) {
            receptores.get(evento).add(receptor);
        }
    }

    public void desuscribir(ReceptorEventos receptor,Evento... eventos){
        for (Evento evento : Evento.values()) {
            receptores.get(evento).remove(receptor);
        }
    }

    public void notificar(Evento evento){
        for (ReceptorEventos receptor : receptores.get(evento)) {
            receptor.actualizar(evento);
        }
    }
}


