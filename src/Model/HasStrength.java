//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.02.19 at 10:16:28 PM CET 
//


package Model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://docs.oasis-open.org/legalruleml/ns/v1.0/}Defeater"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "defeater"
})
@XmlRootElement(name = "hasStrength")
public class HasStrength {

    @XmlElement(name = "Defeater", required = true)
    protected Defeater defeater;

    /**
     * Gets the value of the defeater property.
     * 
     * @return
     *     possible object is
     *     {@link Defeater }
     *     
     */
    public Defeater getDefeater() {
        return defeater;
    }

    /**
     * Sets the value of the defeater property.
     * 
     * @param value
     *     allowed object is
     *     {@link Defeater }
     *     
     */
    public void setDefeater(Defeater value) {
        this.defeater = value;
    }

}
