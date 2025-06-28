package hr.algebra.validationservers.soap.model;

import hr.algebra.validationservers.model.Property;
import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "properties")
@XmlAccessorType(XmlAccessType.FIELD)
public class PropertyList {

    @XmlElement(name = "property")
    private List<Property> properties;

    // Getters & setters
    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
}
