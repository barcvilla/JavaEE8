
package ar.com.ejb.ws.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para employee complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="employee">
 *   &lt;complexContent>
 *     &lt;extension base="{http://servicio.ejb.apress.com/}person">
 *       &lt;sequence>
 *         &lt;element name="department" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="manager" type="{http://servicio.ejb.apress.com/}fullTimeEmployee" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "employee", propOrder = {
    "department",
    "email",
    "manager"
})
@XmlSeeAlso({
    FullTimeEmployee.class,
    PartTimeEmployee.class
})
public abstract class Employee
    extends Person
{

    protected String department;
    protected String email;
    protected FullTimeEmployee manager;

    /**
     * Obtiene el valor de la propiedad department.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Define el valor de la propiedad department.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartment(String value) {
        this.department = value;
    }

    /**
     * Obtiene el valor de la propiedad email.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define el valor de la propiedad email.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Obtiene el valor de la propiedad manager.
     * 
     * @return
     *     possible object is
     *     {@link FullTimeEmployee }
     *     
     */
    public FullTimeEmployee getManager() {
        return manager;
    }

    /**
     * Define el valor de la propiedad manager.
     * 
     * @param value
     *     allowed object is
     *     {@link FullTimeEmployee }
     *     
     */
    public void setManager(FullTimeEmployee value) {
        this.manager = value;
    }

}
