package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
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
       for (Opcion opcion1 : Opcion.values()){
           System.out.println(opcion1);
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

    public static Opcion elegirOpcion(){
        int opcion;
       do {
           System.out.print("¿Que opción quieres elegir?: ");
          opcion = Entrada.entero();
       } while (!Opcion.esValida(opcion));
       return Opcion.opciones.get(opcion);
    }

    public static Cliente leerCliente(){
        String nombre = leerCadena("¿Como se llama el cliente? ");
        String dni = leerCadena("¿Cual es el dni del cliente? ");
        String telefono = leerCadena("¿Cual es el teléfono del cliente? ");
        return new Cliente(nombre,dni,telefono);
    }

    public static Cliente leerClienteDni(){
        return Cliente.get(leerCadena("¿Que DNI quieres introducir? "));
    }

    public static String leerNuevoNombre(){
       return leerCadena("¿Que nombre quieres introducir? ");
    }

    public static String leerNuevoTelefono(){
        return leerCadena("¿Que teléfono quieres introducir? ");
    }

    public static Vehiculo leerVehiculo(){
        return new Vehiculo(leerCadena("¿Que marca quieres introducir? "),leerCadena("¿Que modelo quieres introducir? "),leerCadena("¿Que matricula quieres introducir? "));
    }

    public static Vehiculo leerVehiculoMatricula(){
        return Vehiculo.get(leerCadena("¿Que matricula quieres introducir? "));
    }

    public static Trabajo leerRevision(){
       Cliente nombre = leerClienteDni();
       Vehiculo vehiculo = leerVehiculoMatricula();
       LocalDate fecha = leerFecha("¿Que fecha de inicio quieres introducir? ");
       return new Trabajo(nombre,vehiculo,fecha);
    }

    public static int leerHoras(){
       return leerEntero("¿Cuantas horas quieres introducir? ");
    }

    public static float leerPrecioMaterial(){
       return leerReal("¿Que precio quieres introducir? ");
    }

    public static LocalDate leerFechaCierre(){
       return leerFecha("¿Que fecha de cierre quieres introducir? ");
    }
}
