package org.iesalandalus.programacion.tallermecanico.vista;

import java.util.HashMap;
import java.util.Map;

public enum Opcion {
    INSERTAR_CLIENTE (11, "Insertar cliente"),
    BUSCAR_CLIENTE (12, "Buscar cliente"),
    BORRAR_CLIENTE (13, "Borrar cliente"),
    LISTAR_CLIENTE (14, "Listar cliente"),
    MODIFICAR_CLIENTE (15,"Modificar cliente"),
    //-
    INSERTAR_VEHICULO (21,"Insertar vehiculo"),
    BUSCAR_VEHICULO (22, "Buscar vehiculo"),
    BORRAR_VEHICULO (23,"Borrar vehiculo"),
    LISTAR_VEHICULO (24,"Listar vehiculo"),
    //-
    INSERTAR_REVISION (31,"Insertar revisión"),
    BUSCAR_REVISION (32,"Buscar revisión"),
    BORRAR_REVISION (33,"Borrar revisión"),
    LISTAR_REVISION (34, "Listar revisión"),
    LISTAR_REVISION_CLIENTE (35, "Listar revisión cliente"),
    LISTAR_REVISION_VEHICULO (36,"Listar revisión vehiculo"),
    ANADIR_HORAS_REVISION (37, "Añadir horas a la revisión"),
    ANADIR_PRECIO_MATERIAL_REVISION (38,"Añadir precio material de revisión"),
    CERRAR_REVISION (39,"Cerrar revisión"),
    SALIR (1,"Salir");

    private int numeroOpcion;
    private String mensaje;
    static Map<Integer, Opcion> opciones = new HashMap<>();
    static {
        for (Opcion opcion : values()) {
            opciones.put(opcion.numeroOpcion, opcion);
        }
    }

    private Opcion(int numeroOpcion,String mensaje){
        this.numeroOpcion = numeroOpcion;
        this.mensaje = mensaje;
    }

    public static boolean esValida(int numeroOpcion){
        return opciones.containsKey(numeroOpcion);
    }

    public Opcion get(int numeroOpcion){
        if (!esValida(numeroOpcion)){
            throw new IllegalArgumentException("La opción no es valida");
        }

        return opciones.get(numeroOpcion);
    }

    @Override
    public String toString() {
        return String.format("%d.- %s", numeroOpcion, mensaje);
    }
}
