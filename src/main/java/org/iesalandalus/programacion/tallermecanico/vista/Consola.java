package org.iesalandalus.programacion.tallermecanico.vista;

import java.time.format.DateTimeFormatter;

public class Consola {
   private static final DateTimeFormatter CADENA_FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

   private Consola(){

   }

   public static void mostrarCabecera(String mensaje){
       int longi = mensaje.length();
       System.out.print(mensaje);
       for (int i = 0; i > longi ;i++){

       }
   }
}
