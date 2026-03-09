package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.util.Objects;

public class Cliente {
    private final static String ER_NOMBRE = "^[A-ZÀÈÒÙÌÑ][a-zàèìòùñ]*";
    private final static String ER_DNI = "\\d{8}[A-ZÑ] ";
    private final static String ER_TELEFONO = "\\d{9}";
    private String nombre;
    private String dni;
    private String telefono;

    public void setNombre(String nombre) {
        Objects.requireNonNull(nombre,"No puedes tener un nombre nulo");
        if (nombre.matches(ER_NOMBRE)) {
            this.nombre = nombre;
        } else {
            throw new IllegalArgumentException("El nombre no es valido");
        }

    }

    private void setDni(String dni) {
        Objects.requireNonNull(dni, "No puedes tener un dni nulo");
        if (dni.matches(ER_DNI) && comprobarLetraDNI(dni)) {
            this.dni = dni;
        } else {
            throw new IllegalArgumentException("El DNI no es valido");
        }

    }

    private boolean comprobarLetraDNI(String dni) {
        return (dni.substring(9).matches("[A-ZÑ]"));
    }


    public void setTelefono(String telefono) {
        Objects.requireNonNull(telefono,"El telefono no puede ser nulo");
        if (telefono.matches(ER_TELEFONO)){
            this.telefono = telefono;
        } else {
            throw new IllegalArgumentException("El telefono no es valido");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getDni() {
        return dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public Cliente(String nombre, String dni, String telefono) {
        setNombre(nombre);
        setDni(dni);
        setTelefono(telefono);
    }

    public Cliente(Cliente cliente){
        setNombre(cliente.getNombre());
        setDni(cliente.getDni());
        setTelefono(cliente.getTelefono());
    }

    public static Cliente get(String dni){
        return (new Cliente("Manue",dni,"123456789"));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(nombre, cliente.nombre) && Objects.equals(dni, cliente.dni) && Objects.equals(telefono, cliente.telefono);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, dni, telefono);
    }

    @Override
    public String toString() {
        return String.format("Cliente (nombre=%s, dni=%s, telefono=%s)", nombre, dni, telefono);
    }
}
