
package ar.com.ejb.ws.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ar.com.ejb.ws.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ListarFullTimeEmployeesResponse_QNAME = new QName("http://servicio.ejb.apress.com/", "listarFullTimeEmployeesResponse");
    private final static QName _ListarPartTimeEmployeesResponse_QNAME = new QName("http://servicio.ejb.apress.com/", "listarPartTimeEmployeesResponse");
    private final static QName _ListarPartTimeEmployees_QNAME = new QName("http://servicio.ejb.apress.com/", "listarPartTimeEmployees");
    private final static QName _ListarEmployeesResponse_QNAME = new QName("http://servicio.ejb.apress.com/", "listarEmployeesResponse");
    private final static QName _ListarEmployees_QNAME = new QName("http://servicio.ejb.apress.com/", "listarEmployees");
    private final static QName _ListarFullTimeEmployees_QNAME = new QName("http://servicio.ejb.apress.com/", "listarFullTimeEmployees");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ar.com.ejb.ws.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListarPartTimeEmployeesResponse }
     * 
     */
    public ListarPartTimeEmployeesResponse createListarPartTimeEmployeesResponse() {
        return new ListarPartTimeEmployeesResponse();
    }

    /**
     * Create an instance of {@link ListarPartTimeEmployees }
     * 
     */
    public ListarPartTimeEmployees createListarPartTimeEmployees() {
        return new ListarPartTimeEmployees();
    }

    /**
     * Create an instance of {@link ListarEmployeesResponse }
     * 
     */
    public ListarEmployeesResponse createListarEmployeesResponse() {
        return new ListarEmployeesResponse();
    }

    /**
     * Create an instance of {@link ListarFullTimeEmployeesResponse }
     * 
     */
    public ListarFullTimeEmployeesResponse createListarFullTimeEmployeesResponse() {
        return new ListarFullTimeEmployeesResponse();
    }

    /**
     * Create an instance of {@link ListarEmployees }
     * 
     */
    public ListarEmployees createListarEmployees() {
        return new ListarEmployees();
    }

    /**
     * Create an instance of {@link ListarFullTimeEmployees }
     * 
     */
    public ListarFullTimeEmployees createListarFullTimeEmployees() {
        return new ListarFullTimeEmployees();
    }

    /**
     * Create an instance of {@link Address }
     * 
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link FullTimeEmployee }
     * 
     */
    public FullTimeEmployee createFullTimeEmployee() {
        return new FullTimeEmployee();
    }

    /**
     * Create an instance of {@link PartTimeEmployee }
     * 
     */
    public PartTimeEmployee createPartTimeEmployee() {
        return new PartTimeEmployee();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListarFullTimeEmployeesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.ejb.apress.com/", name = "listarFullTimeEmployeesResponse")
    public JAXBElement<ListarFullTimeEmployeesResponse> createListarFullTimeEmployeesResponse(ListarFullTimeEmployeesResponse value) {
        return new JAXBElement<ListarFullTimeEmployeesResponse>(_ListarFullTimeEmployeesResponse_QNAME, ListarFullTimeEmployeesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListarPartTimeEmployeesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.ejb.apress.com/", name = "listarPartTimeEmployeesResponse")
    public JAXBElement<ListarPartTimeEmployeesResponse> createListarPartTimeEmployeesResponse(ListarPartTimeEmployeesResponse value) {
        return new JAXBElement<ListarPartTimeEmployeesResponse>(_ListarPartTimeEmployeesResponse_QNAME, ListarPartTimeEmployeesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListarPartTimeEmployees }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.ejb.apress.com/", name = "listarPartTimeEmployees")
    public JAXBElement<ListarPartTimeEmployees> createListarPartTimeEmployees(ListarPartTimeEmployees value) {
        return new JAXBElement<ListarPartTimeEmployees>(_ListarPartTimeEmployees_QNAME, ListarPartTimeEmployees.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListarEmployeesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.ejb.apress.com/", name = "listarEmployeesResponse")
    public JAXBElement<ListarEmployeesResponse> createListarEmployeesResponse(ListarEmployeesResponse value) {
        return new JAXBElement<ListarEmployeesResponse>(_ListarEmployeesResponse_QNAME, ListarEmployeesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListarEmployees }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.ejb.apress.com/", name = "listarEmployees")
    public JAXBElement<ListarEmployees> createListarEmployees(ListarEmployees value) {
        return new JAXBElement<ListarEmployees>(_ListarEmployees_QNAME, ListarEmployees.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListarFullTimeEmployees }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.ejb.apress.com/", name = "listarFullTimeEmployees")
    public JAXBElement<ListarFullTimeEmployees> createListarFullTimeEmployees(ListarFullTimeEmployees value) {
        return new JAXBElement<ListarFullTimeEmployees>(_ListarFullTimeEmployees_QNAME, ListarFullTimeEmployees.class, null, value);
    }

}
