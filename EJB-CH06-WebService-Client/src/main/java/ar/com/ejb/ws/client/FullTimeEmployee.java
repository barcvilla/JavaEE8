
package ar.com.ejb.ws.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para fullTimeEmployee complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="fullTimeEmployee">
 *   &lt;complexContent>
 *     &lt;extension base="{http://servicio.ejb.apress.com/}employee">
 *       &lt;sequence>
 *         &lt;element name="annualSalary" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fullTimeEmployee", propOrder = {
    "annualSalary"
})
public class FullTimeEmployee
    extends Employee
{

    protected double annualSalary;

    /**
     * Obtiene el valor de la propiedad annualSalary.
     * 
     */
    public double getAnnualSalary() {
        return annualSalary;
    }

    /**
     * Define el valor de la propiedad annualSalary.
     * 
     */
    public void setAnnualSalary(double value) {
        this.annualSalary = value;
    }

}
