package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Consola {
   private static final String CADENA_FORMATO_FECHA ="dd/MM/yyyy";

   private Consola(){

   }

   public static void mostrarCabecera(String mensaje){
       for (int i = 0; i < mensaje.length();i++){
           System.out.print("_");
       }
       System.out.println();
       System.out.println(mensaje);
       for (int i = 0; i < mensaje.length();i++){
           System.out.print("_");
       }
       System.out.println();
   }

   public static void mostrarMenu(){
       mostrarCabecera("Gestión de un taller mecánico ");
       for (Evento evento1 : Evento.values()){
           System.out.println(evento1);
       }
   }

   public static float leerReal(String mensaje){
       System.out.print(mensaje );
       return Entrada.real();
   }

   public static int leerEntero(String mensaje){
       System.out.print(mensaje);
       return Entrada.entero();
   }

    public static String leerCadena(String mensaje){
        System.out.print(mensaje);
        return Entrada.cadena();
    }

    public static LocalDate leerFecha(String mensaje){
        LocalDate fecha;
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(CADENA_FORMATO_FECHA);
        mensaje = String.format("%s (%s) ", mensaje, CADENA_FORMATO_FECHA);
        try {
            fecha = LocalDate.parse(leerCadena(mensaje), formatoFecha);
        } catch (DateTimeParseException e){
            fecha = null;
        }
        return fecha;
    }

    public static Evento elegirOpcion(){
       int opcion;
       do {
           System.out.print("¿Que opción quieres elegir?: ");
          opcion = Entrada.entero();
       } while (!Evento.esValida(opcion));
       return Evento.get(opcion);
    }

}
