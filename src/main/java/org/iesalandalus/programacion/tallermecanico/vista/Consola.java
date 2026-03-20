package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Consola {
   private static final DateTimeFormatter CADENA_FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

   private Consola(){

   }

   public static void mostrarCabecera(String mensaje){
       System.out.print(mensaje);
       for (int i = 0; i < mensaje.length();i++){
           System.out.println("_");
       }
   }

   public static void mostrarMenu(){
       mostrarCabecera("Gestión de un taller mecanico");
       for (Opcion opcion1 : Opcion.values()){
           System.out.print(opcion1);
       }
   }

   public static float leerReal(String mensaje){
       System.out.println(mensaje);
       return Entrada.real();
   }

   public static int leerEntero(String mensaje){
       System.out.println(mensaje);
       return Entrada.entero();
   }

    public static String leerCadena(String mensaje){
        System.out.println(mensaje);
        return Entrada.cadena();
    }

    public static LocalDate leerFecha(String mensaje){
        System.out.println(mensaje);
        return LocalDate.parse(Entrada.cadena().formatted(CADENA_FORMATO_FECHA));
    }

    public static Opcion elegirOpcion(){
       int opcion = Entrada.entero();
       return Opcion.opciones.get(opcion);
    }

    public static Cliente leerCliente(){
        String nombre = leerCadena("Como se llama el cliente?");
        String dni = leerCadena("¿Cual es el dni del cliente?");
        String telefono = leerCadena("¿Cual es el telefono del cliente?");
        return new Cliente(nombre,dni,telefono);
    }

    public static Cliente leerClienteDni(){
        return Cliente.get(leerCadena("¿Que DNI quieres introducir?"));
    }

    public static String leerNuevoNombre(){
       return leerCadena("¿Que nombre quieres introducir?");
    }

    public static String leerNuevoTelefono(){
        return leerCadena("¿Que telefono quieres introducir?");
    }

    public static Vehiculo leerVehiculo(){
        return new Vehiculo(leerCadena("¿Que marca quieres introducir?"),leerCadena("¿Que modelo quieres introducir?"),leerCadena("¿Que matricula quieres introducir?"));
    }

    public static Vehiculo leerVehiculoMatricula(){
        return Vehiculo.get(leerCadena("¿Que matricula quieres introducir?"));
    }

    public static Revision leerRevision(){
       return new Revision(leerCliente(),leerVehiculo(),leerFecha("¿Que fecha de inicio quieres introducir?"));
    }

    public static int leerHoras(){
       return leerEntero("¿Cuantas horas quieres introducir?");
    }

    public static float leerPrecioMaterial(){
       return leerReal("¿Que precio quieres introducir?");
    }

    public static LocalDate leerFechaCierre(){
       return leerFecha("¿Que fecha de cierre quieres introducir?");
    }
}
