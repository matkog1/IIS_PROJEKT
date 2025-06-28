package hr.algebra.validationservers.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SOAPSearchRequest", namespace = "http://algebra.hr/property")
@XmlAccessorType(XmlAccessType.FIELD)
public class SOAPSearchRequest {
    @XmlElement(namespace = "http://algebra.hr/property")
    private String field;

    @XmlElement(namespace = "http://algebra.hr/property")
    private String value;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
