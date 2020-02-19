//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.02.19 at 10:16:28 PM CET 
//


package Model;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element ref="{http://docs.oasis-open.org/legalruleml/ns/v1.0/}appliesAuthority" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{http://docs.oasis-open.org/legalruleml/ns/v1.0/}appliesSource"/>
 *           &lt;element ref="{http://docs.oasis-open.org/legalruleml/ns/v1.0/}appliesTemporalCharacteristics"/>
 *           &lt;element ref="{http://docs.oasis-open.org/legalruleml/ns/v1.0/}appliesJurisdiction"/>
 *         &lt;/choice>
 *         &lt;element ref="{http://docs.oasis-open.org/legalruleml/ns/v1.0/}toTarget" maxOccurs="unbounded"/>
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
    "appliesAuthority",
    "appliesSource",
    "appliesTemporalCharacteristics",
    "appliesJurisdiction",
    "toTarget"
})
@XmlRootElement(name = "Association")
public class Association {

    protected AppliesAuthority appliesAuthority;
    protected AppliesSource appliesSource;
    protected AppliesTemporalCharacteristics appliesTemporalCharacteristics;
    protected AppliesJurisdiction appliesJurisdiction;
    @XmlElement(required = true)
    protected List<ToTarget> toTarget;

    /**
     * Gets the value of the appliesAuthority property.
     * 
     * @return
     *     possible object is
     *     {@link AppliesAuthority }
     *     
     */
    public AppliesAuthority getAppliesAuthority() {
        return appliesAuthority;
    }

    /**
     * Sets the value of the appliesAuthority property.
     * 
     * @param value
     *     allowed object is
     *     {@link AppliesAuthority }
     *     
     */
    public void setAppliesAuthority(AppliesAuthority value) {
        this.appliesAuthority = value;
    }

    /**
     * Gets the value of the appliesSource property.
     * 
     * @return
     *     possible object is
     *     {@link AppliesSource }
     *     
     */
    public AppliesSource getAppliesSource() {
        return appliesSource;
    }

    /**
     * Sets the value of the appliesSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link AppliesSource }
     *     
     */
    public void setAppliesSource(AppliesSource value) {
        this.appliesSource = value;
    }

    /**
     * Gets the value of the appliesTemporalCharacteristics property.
     * 
     * @return
     *     possible object is
     *     {@link AppliesTemporalCharacteristics }
     *     
     */
    public AppliesTemporalCharacteristics getAppliesTemporalCharacteristics() {
        return appliesTemporalCharacteristics;
    }

    /**
     * Sets the value of the appliesTemporalCharacteristics property.
     * 
     * @param value
     *     allowed object is
     *     {@link AppliesTemporalCharacteristics }
     *     
     */
    public void setAppliesTemporalCharacteristics(AppliesTemporalCharacteristics value) {
        this.appliesTemporalCharacteristics = value;
    }

    /**
     * Gets the value of the appliesJurisdiction property.
     * 
     * @return
     *     possible object is
     *     {@link AppliesJurisdiction }
     *     
     */
    public AppliesJurisdiction getAppliesJurisdiction() {
        return appliesJurisdiction;
    }

    /**
     * Sets the value of the appliesJurisdiction property.
     * 
     * @param value
     *     allowed object is
     *     {@link AppliesJurisdiction }
     *     
     */
    public void setAppliesJurisdiction(AppliesJurisdiction value) {
        this.appliesJurisdiction = value;
    }

    /**
     * Gets the value of the toTarget property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the toTarget property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getToTarget().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ToTarget }
     * 
     * 
     */
    public List<ToTarget> getToTarget() {
        if (toTarget == null) {
            toTarget = new ArrayList<ToTarget>();
        }
        return this.toTarget;
    }

}
