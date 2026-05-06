package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

public enum TipoTrabajo {
    MECANICO("Mecanico"),
    REVISION("Revision");

    private String nombre;

    TipoTrabajo(String nombre) {
        this.nombre = nombre;
    }

    public static TipoTrabajo get(Trabajo trabajo) {
        if (trabajo instanceof Revision) {
            return REVISION;
        } else {
            return MECANICO;
        }
    }
}
