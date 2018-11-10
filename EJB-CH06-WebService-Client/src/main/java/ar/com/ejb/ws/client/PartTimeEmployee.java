
package ar.com.ejb.ws.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para partTimeEmployee complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="partTimeEmployee">
 *   &lt;complexContent>
 *     &lt;extension base="{http://servicio.ejb.apress.com/}employee">
 *       &lt;sequence>
 *         &lt;element name="hourlyWage" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "partTimeEmployee", propOrder = {
    "hourlyWage"
})
public class PartTimeEmployee
    extends Employee
{

    protected double hourlyWage;

    /**
     * Obtiene el valor de la propiedad hourlyWage.
     * 
     */
    public double getHourlyWage() {
        return hourlyWage;
    }

    /**
     * Define el valor de la propiedad hourlyWage.
     * 
     */
    public void setHourlyWage(double value) {
        this.hourlyWage = value;
    }

}
