package hr.algebra.validationservers.service;

import hr.algebra.validationservers.dto.PropertyDto;
import jakarta.xml.bind.*;

import org.springframework.stereotype.Component;
import java.io.ByteArrayInputStream;

@Component
public class PropertyMapper {

    private final Unmarshaller unmarshaller;

    public PropertyMapper() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(PropertyDto.class);
        unmarshaller = context.createUnmarshaller();
    }

    public PropertyDto fromXml(byte[] xml) throws JAXBException {
        return (PropertyDto) unmarshaller.unmarshal(new ByteArrayInputStream(xml));
    }
}
