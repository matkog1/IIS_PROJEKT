package hr.algebra.validationservers.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SOAPSearchResponse", namespace = "http://algebra.hr/property")
@XmlAccessorType(XmlAccessType.FIELD)
public class SOAPSearchResponse {
    @XmlElement(namespace = "http://algebra.hr/property")
    private String result;

    public SOAPSearchResponse() {}

    public SOAPSearchResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
