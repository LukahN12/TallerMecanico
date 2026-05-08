package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vehiculos implements IVehiculos {

    private static final String FICHERO_VEHICULOS = String.format("%s%s%s", "datos", File.separator, "vehiculos.xml");
    private static final String RAIZ = "vehiculos";
    private static final String VEHICULO = "vehiculo";
    private static final String MARCA = "marca";
    private static final String MODELO = "modelo";
    private static final String MATRICULA = "matricula";

    private List<Vehiculo> coleccionVehiculos;
    private static Vehiculos instancia;

    private Vehiculos() {
        coleccionVehiculos = new ArrayList<>();
    }

    static Vehiculos getInstancia(){
        if (instancia ==  null){
            instancia = new Vehiculos();
        }
        return instancia;
    }

    @Override
    public List<Vehiculo> get() {
        return coleccionVehiculos;
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo, "No se puede insertar un vehículo nulo.");
        if (coleccionVehiculos.contains(vehiculo)){
            throw new TallerMecanicoExcepcion("Ya existe un vehículo con esa matrícula.");
        }

        coleccionVehiculos.add(vehiculo);
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo){
        Objects.requireNonNull(vehiculo, "No se puede buscar un vehículo nulo.");
        int indice = coleccionVehiculos.indexOf(vehiculo);
        return (indice != -1 ? coleccionVehiculos.get(indice) : null);
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo, "No se puede borrar un vehículo nulo.");
        Vehiculo buscado = buscar(vehiculo);

        if (!coleccionVehiculos.contains(buscado)){
            throw new TallerMecanicoExcepcion("No existe ningún vehículo con esa matrícula.");
        }

        coleccionVehiculos.remove(buscado);
    }

    @Override
    public void comenzar() {
        Document documentoXml = UtilidadesXml.leerDocumentoXml(FICHERO_VEHICULOS);
        if (documentoXml != null) {
            System.out.printf("Fichero %s leido correctamente.%n",FICHERO_VEHICULOS);
            procesarDocumentoXml(documentoXml);
        }
    }

    @Override
    public void terminar() {
        Document clientes = crearDocumentoXML();
        UtilidadesXml.escribirDocumentoXml(clientes, FICHERO_VEHICULOS);

    }

    private Document crearDocumentoXML(){
        DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
        Document documentoXml = null;
        if (constructor != null) {
            documentoXml = constructor.newDocument();
            documentoXml.appendChild(documentoXml.createElement(RAIZ));
            for (Vehiculo vehiculo : coleccionVehiculos) {
                Element elementoPersona = getElemento(documentoXml, vehiculo);
                documentoXml.getDocumentElement().appendChild(elementoPersona);
            }
        }
        return documentoXml;
    }

    private static Element getElemento(Document documentoXML, Vehiculo vehiculo) {
        Element elementoPersona = documentoXML.createElement(VEHICULO);
        elementoPersona.setAttribute(MODELO, vehiculo.modelo());
        elementoPersona.setAttribute(MARCA, vehiculo.marca());
        elementoPersona.setAttribute(MATRICULA, vehiculo.matricula());
        return elementoPersona;
    }

    private void procesarDocumentoXml(Document documentoXml){
        NodeList clientes = documentoXml.getElementsByTagName(VEHICULO);
        for (int i = 0; i < clientes.getLength(); i++) {
            Node cliente = clientes.item(i);
            try{
                insertar(getVehiculo((Element) cliente));
            } catch (TallerMecanicoExcepcion | NullPointerException e){
                System.out.print("ERROR al procesar el documento: " + e.getMessage());
            }
        }
    }

    private Vehiculo getVehiculo(Element elemento){
        Vehiculo vehiculo = null;
        if (elemento.getNodeType() == Node.ELEMENT_NODE) {
            String marca = elemento.getAttribute(MARCA);
            String modelo = (elemento.getAttribute(MODELO));
            String matricula = (elemento.getAttribute(MATRICULA));
            vehiculo = new Vehiculo(marca,modelo,matricula);
        }
        return vehiculo;
    }
}
