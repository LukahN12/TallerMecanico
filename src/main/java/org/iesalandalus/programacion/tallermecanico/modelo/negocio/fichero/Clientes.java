package org.iesalandalus.programacion.tallermecanico.modelo.negocio.fichero;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Clientes implements IClientes {

    private static final String FICHERO_CLIENTES = String.format("%s%s%s", "datos", File.separator, "clientes.xml");
    private static final String RAIZ = "clientes";
    private static final String CLIENTE = "cliente";
    private static final String NOMBRE = "nomrbre";
    private static final String DNI = "dni";
    private static final String TELEFONO = "telefono";

    private List<Cliente> coleccionClientes;
    private static Clientes instancia;

    private Clientes() {
        coleccionClientes = new ArrayList<>();
    }

    static Clientes getInstancia() {
        if (instancia == null){
            instancia = new Clientes();
        }
        return instancia;
    }

    @Override
    public List<Cliente> get() {
        return coleccionClientes;
    }

    @Override
    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "No se puede insertar un cliente nulo.");
        if (coleccionClientes.contains(cliente)){
            throw new TallerMecanicoExcepcion("Ya existe un cliente con ese DNI.");
        }

        coleccionClientes.add(cliente);
    }

    @Override
    public Cliente modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "No se puede modificar un cliente nulo.");

        Cliente buscado = buscar(cliente);

        if (!coleccionClientes.contains(buscado)){
            throw new TallerMecanicoExcepcion("No existe ningún cliente con ese DNI.");
        }

        if (nombre != null) {
            buscado.setNombre(nombre);
        }

        if (telefono != null){
            buscado.setTelefono(telefono);
        }

        return buscado;
    }

    @Override
    public Cliente buscar(Cliente cliente){
        Objects.requireNonNull(cliente, "No se puede buscar un cliente nulo.");
        int indice = coleccionClientes.indexOf(cliente);
        return (indice != -1 ? coleccionClientes.get(indice) : null);
    }

    @Override
    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "No se puede borrar un cliente nulo.");
        Cliente buscado = buscar(cliente);

        if (!coleccionClientes.contains(buscado)){
            throw new TallerMecanicoExcepcion("No existe ningún cliente con ese DNI.");
        }

        coleccionClientes.remove(buscado);

    }

    @Override
    public void comenzar() {
        Document documentoXml = UtilidadesXml.leerDocumentoXml(FICHERO_CLIENTES);
        if (documentoXml != null) {
            System.out.println("Fichero leúido correctamente.");
            procesarDocumentoXml(documentoXml);
        }
    }

    @Override
    public void terminar() {

    }

    private Document crearDocumentoXML(){
        DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
        Document documentoXml = null;
        if (constructor != null) {
            documentoXml = constructor.newDocument();
            documentoXml.appendChild(documentoXml.createElement(CLIENTE));
            for (Cliente cliente : coleccionClientes) {
                Element elementoPersona = crearElementoClienteConAtributos(documentoXml, cliente);
                documentoXml.getDocumentElement().appendChild(elementoPersona);
            }
        }
    }

    private static Element crearElementoClienteConAtributos(Document documentoXML, Cliente cliente) {
        Element elementoPersona = documentoXML.createElement(CLIENTE);
        elementoPersona.setAttribute(DNI, cliente.getDni());
        elementoPersona.setAttribute(NOMBRE, cliente.getNombre());
        elementoPersona.setAttribute(TELEFONO, cliente.getTelefono());
        return elementoPersona;
    }

    private void procesarDocumentoXml(Document documentoXml){
        NodeList clientes = documentoXml.getElementsByTagName(CLIENTE);
        for (int i = 0; i < clientes.getLength(); i++) {
            Node cliente = clientes.item(i);
            try{
                insertar(getCliente((Element) cliente));
            } catch (TallerMecanicoExcepcion | NullPointerException e){
                System.out.print("ERROR al procesar el documento: " + e.getMessage());
            }
        }
    }

    private Cliente getCliente(Element elemento){
        if (elemento.getNodeType() == Node.ELEMENT_NODE) {
            String nombre = elemento.getAttribute(NOMBRE);
            String dni = (elemento.getAttribute(DNI));
            String telefono = (elemento.getAttribute(TELEFONO));
            new Cliente(nombre,dni,telefono);
        }
    }

    private Element getElemento(Document documentoXml,Cliente cliente){
        return null;
    }
}