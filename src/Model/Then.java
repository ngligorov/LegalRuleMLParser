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
 *       &lt;choice>
 *         &lt;element ref="{http://docs.oasis-open.org/legalruleml/ns/v1.0/}Prohibition"/>
 *         &lt;element ref="{http://docs.oasis-open.org/legalruleml/ns/v1.0/}Permission"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "prohibition",
    "permission"
})
@XmlRootElement(name = "then", namespace = "http://ruleml.org/spec")
public class Then {

    @XmlElement(name = "Prohibition")
    protected Prohibition prohibition;
    @XmlElement(name = "Permission")
    protected Permission permission;

    /**
     * Gets the value of the prohibition property.
     * 
     * @return
     *     possible object is
     *     {@link Prohibition }
     *     
     */
    public Prohibition getProhibition() {
        return prohibition;
    }

    /**
     * Sets the value of the prohibition property.
     * 
     * @param value
     *     allowed object is
     *     {@link Prohibition }
     *     
     */
    public void setProhibition(Prohibition value) {
        this.prohibition = value;
    }

    /**
     * Gets the value of the permission property.
     * 
     * @return
     *     possible object is
     *     {@link Permission }
     *     
     */
    public Permission getPermission() {
        return permission;
    }

    /**
     * Sets the value of the permission property.
     * 
     * @param value
     *     allowed object is
     *     {@link Permission }
     *     
     */
    public void setPermission(Permission value) {
        this.permission = value;
    }

	public String toString() {
		return "Then [prohibition=" + prohibition + ", permission=" + permission + "]";
	}

}
