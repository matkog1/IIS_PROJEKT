package hr.algebra.validationservers.dto;

import hr.algebra.validationservers.model.Property;
import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "properties")
@XmlAccessorType(XmlAccessType.FIELD)
public class PropertyListDto {

    @XmlElement(name = "property")
    private List<Property> property;

    public List<Property> getProperty() {
        return property;
    }
    public void setProperty(List<Property> property) {
        this.property = property;
    }
}
