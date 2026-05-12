package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Trabajos implements ITrabajos {

    private static final String FICHERO_TRABAJOS = String.format("%s%s%s", "datos", File.separator, "trabajos.xml");
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String RAIZ = "trabajos";
    private static final String TRABAJO = "trabajo";
    private static final String CLIENTE = "cliente";
    private static final String VEHICULO = "vehiculo";
    private static final String FECHA_INICIO = "fechaInicio";
    private static final String FECHA_FIN = "fechaFin";
    private static final String HORAS = "horas";
    private static final String PRECIO_MATERIAL = "precioMaterial";
    private static final String TIPO = "tipo";
    private static final String REVISION = "revision";
    private static final String MECANICO = "mecanico";

    private List<Trabajo> coleccionTrabajos;
    private static Trabajos instancia;

    private Trabajos() {
        coleccionTrabajos = new ArrayList<>();
    }

    static Trabajos getInstancia(){
        if (instancia ==  null){
            instancia = new Trabajos();
        }
        return instancia;
    }

    @Override
    public List<Trabajo> get() {
        return new ArrayList<>(coleccionTrabajos);
    }

    @Override
    public List<Trabajo> get(Cliente cliente) {

        List<Trabajo> coleccionResultante;
        coleccionResultante = new ArrayList<>();

        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getCliente().equals(cliente)) {
                coleccionResultante.add(trabajo);
            }
        }

        return coleccionResultante;
    }

    @Override
    public List<Trabajo> get(Vehiculo vehiculo) {

        List<Trabajo> coleccionResultante;
        coleccionResultante = new ArrayList<>();

        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getVehiculo().equals(vehiculo)) {
                coleccionResultante.add(trabajo);
            }
        }

        return coleccionResultante;
    }

    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede insertar un trabajo nulo.");
        comprobarRevision(trabajo.getCliente(), trabajo.getVehiculo(), trabajo.getFechaInicio());
        coleccionTrabajos.add(trabajo);
    }

    private void comprobarRevision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaRevision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        Objects.requireNonNull(vehiculo, "El vehiculo no puede ser nulo");

        for (Trabajo trabajo : coleccionTrabajos) {
            if (!trabajo.estaCerrado()) {
                if (trabajo.getCliente().equals(cliente)) {
                    throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo en curso.");
                }
                if (trabajo.getVehiculo().equals(vehiculo)) {
                    throw new TallerMecanicoExcepcion("El vehículo está actualmente en el taller.");
                }
            }

            if (trabajo.estaCerrado()) {
                if (!fechaRevision.isAfter(trabajo.getFechaFin())) {
                    if (trabajo.getCliente().equals(cliente)) {
                        throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo posterior.");
                    }
                    if (trabajo.getVehiculo().equals(vehiculo)) {
                        throw new TallerMecanicoExcepcion("El vehículo tiene otro trabajo posterior.");
                    }
                }
            }
        }
    }

    private Trabajo getTrabajoAbierto(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Trabajo trabajoResultado = null;

        for (Trabajo trabajo : coleccionTrabajos){
            if (!trabajo.estaCerrado() && trabajo.getVehiculo().equals(vehiculo)) {
                trabajoResultado = trabajo;
            }
        }
        if (trabajoResultado == null){
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }


        return trabajoResultado;
    }

    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"No puedo añadir horas a un trabajo nulo.");
        Trabajo trabajo1 = getTrabajoAbierto(trabajo.getVehiculo());
        trabajo1.anadirHoras(horas);
        return trabajo1;
    }

    @Override
    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"No puedo añadir precio del material a un trabajo nulo.");
        Trabajo trabajo1 = getTrabajoAbierto(trabajo.getVehiculo());
        if (!(trabajo1 instanceof Mecanico mecanico)){
            throw new TallerMecanicoExcepcion("No se puede añadir precio al material para este tipo de trabajos.");
        }
        mecanico.anadirPrecioMaterial(precioMaterial);
        return trabajo1;
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"No puedo cerrar un trabajo nulo.");
        Trabajo trabajo1 = getTrabajoAbierto(trabajo.getVehiculo());
        trabajo1.cerrar(fechaFin);
        return trabajo1;
    }

    @Override
    public Trabajo buscar(Trabajo trabajo){
        Objects.requireNonNull(trabajo, "No se puede buscar un trabajo nulo.");
        int indice = coleccionTrabajos.indexOf(trabajo);
        return (indice != -1 ? coleccionTrabajos.get(indice) : null);
    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede borrar un trabajo nulo.");
        Trabajo buscado = buscar(trabajo);

        if (!coleccionTrabajos.contains(buscado)){
            throw new TallerMecanicoExcepcion("No existe ningún trabajo igual.");
        }

        coleccionTrabajos.remove(buscado);
    }

    public Map<TipoTrabajo,Integer> getEstadisticasMensuales(LocalDate mes){
        Objects.requireNonNull(mes,"El mes no puede ser nulo");
        Map<TipoTrabajo,Integer> mapa = inicializarEstadisticas();

        for (Trabajo trabajo : coleccionTrabajos){
            if (trabajo.getFechaInicio().getMonth().equals(mes.getMonth()) && trabajo.getFechaInicio().getYear() == mes.getYear()){
                TipoTrabajo tipo = TipoTrabajo.get(trabajo);
                mapa.put(tipo,mapa.get(tipo) + 1);
            }
        }

        return mapa;
    }

    private Map<TipoTrabajo,Integer> inicializarEstadisticas(){
        Map<TipoTrabajo,Integer> mapa = new EnumMap<>(TipoTrabajo.class);
        for (TipoTrabajo tipoTrabajo : TipoTrabajo.values()){
            mapa.put(tipoTrabajo,0);
        }
        return mapa;
    }

    @Override
    public void comenzar() {
        Document documentoXml = UtilidadesXml.leerDocumentoXml(FICHERO_TRABAJOS);
        if (documentoXml != null) {
            System.out.printf("Fichero %s leido correctamente.%n",FICHERO_TRABAJOS);
            procesarDocumentoXml(documentoXml);
        }
    }

    @Override
    public void terminar() {
        Document trabajos = crearDocumentoXML();
        UtilidadesXml.escribirDocumentoXml(trabajos, FICHERO_TRABAJOS);

    }

    private Document crearDocumentoXML(){
        DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
        Document documentoXml = null;
        if (constructor != null) {
            documentoXml = constructor.newDocument();
            documentoXml.appendChild(documentoXml.createElement(RAIZ));
            for (Trabajo trabajo : coleccionTrabajos) {
                Element elementoPersona = getElemento(documentoXml, trabajo);
                elementoPersona.setAttribute(FECHA_INICIO, trabajo.getFechaInicio().format(FORMATO_FECHA));
                if (trabajo.estaCerrado()){
                    elementoPersona.setAttribute(FECHA_FIN,trabajo.getFechaFin().format(FORMATO_FECHA));
                }
                documentoXml.getDocumentElement().appendChild(elementoPersona);
            }
        }
        return documentoXml;
    }

    private static Element getElemento(Document documentoXML, Trabajo trabajo) {
        Element elementoPersona = documentoXML.createElement(TRABAJO);
        elementoPersona.setAttribute(CLIENTE, trabajo.getCliente().getDni());
        if (trabajo.estaCerrado()){
            elementoPersona.setAttribute(FECHA_FIN, trabajo.getFechaFin().toString());
        }
        elementoPersona.setAttribute(FECHA_INICIO,trabajo.getFechaInicio().toString());
        if (trabajo instanceof Revision revision){
            elementoPersona.setAttribute(HORAS, String.format("%s",revision.getHoras()));
            elementoPersona.setAttribute(TIPO, REVISION);
        } else if (trabajo instanceof Mecanico mecanico) {
            elementoPersona.setAttribute(PRECIO_MATERIAL, String.format("%s", mecanico.getPrecioMaterial()));
            elementoPersona.setAttribute(TIPO,MECANICO);
        }
        elementoPersona.setAttribute(VEHICULO,trabajo.getVehiculo().matricula());
        return elementoPersona;
    }

    private void procesarDocumentoXml(Document documentoXml){
        NodeList trabajos = documentoXml.getElementsByTagName(TRABAJO);
        for (int i = 0; i <= trabajos.getLength(); i++) {
            Node trabajo = trabajos.item(i);
            try{
                insertar(getTrabajo((Element) trabajo));
            } catch (TallerMecanicoExcepcion | NullPointerException e){
                System.out.print("ERROR al procesar el documento: " + e.getMessage());
            }
        }
    }

    private Trabajo getTrabajo(Element elemento) throws TallerMecanicoExcepcion {
        Trabajo trabajo = null;
        if (elemento.getNodeType() == Node.ELEMENT_NODE) {
            Cliente cliente = Clientes.getInstancia().buscar(Cliente.get(elemento.getAttribute(CLIENTE)));
            LocalDate fechaInicio = LocalDate.parse((elemento.getAttribute(FECHA_INICIO)),FORMATO_FECHA);
            String tipo = elemento.getAttribute(TIPO);
            Vehiculo vehiculo = Vehiculos.getInstancia().buscar(Vehiculo.get((elemento.getAttribute(VEHICULO))));
            if (tipo.equals(REVISION)){
                trabajo = new Revision(cliente,vehiculo,fechaInicio);
            } else if(tipo.equals(MECANICO)){
                trabajo = new Mecanico(cliente,vehiculo,fechaInicio);
                if(elemento.hasAttribute(PRECIO_MATERIAL)){
                    float precioMaterial = Float.parseFloat((elemento.getAttribute(PRECIO_MATERIAL)));
                    ((Mecanico) trabajo).anadirPrecioMaterial(precioMaterial);
                }
            }
            if(elemento.hasAttribute(HORAS)){
                int horas = Integer.parseInt((elemento.getAttribute(HORAS)));
                assert trabajo != null;
                trabajo.anadirHoras(horas);
            }

            if (elemento.hasAttribute(FECHA_FIN)) {
                LocalDate fechaFin = LocalDate.parse((elemento.getAttribute(FECHA_FIN)),FORMATO_FECHA);
                assert trabajo != null;
                trabajo.cerrar(fechaFin);
            }
        }
        return trabajo;
    }
}
