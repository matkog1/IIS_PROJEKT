package hr.algebra.validationservers.service;

import hr.algebra.validationservers.dto.PropertyDto;
import hr.algebra.validationservers.dto.PropertyListDto;
import hr.algebra.validationservers.soap.model.PropertyList;
import jakarta.xml.bind.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class JaxbValidation {
    private final Unmarshaller unmarshaller;

    public JaxbValidation() throws Exception {
        JAXBContext ctx = JAXBContext.newInstance(PropertyListDto.class);
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new ClassPathResource("xsd/propertyList.xsd").getURL());
        this.unmarshaller = ctx.createUnmarshaller();
        this.unmarshaller.setSchema(schema);
    }

    public List<String> validate(byte[] xmlBytes) throws JAXBException {
        List<String> errors = new ArrayList<>();
        unmarshaller.setEventHandler(evt -> {
            ValidationEventLocator loc = evt.getLocator();
            errors.add(String.format("Line %d:%d – %s",
                    loc.getLineNumber(), loc.getColumnNumber(),
                    evt.getMessage()));
            return true;
        });
        try {
            unmarshaller.unmarshal(new StreamSource(new ByteArrayInputStream(xmlBytes)));
        } catch (JAXBException e) {
            Throwable linked = e.getLinkedException();
            errors.add("Fatal: " + (linked != null ? linked.getMessage() : e.getMessage()));
        }
        return errors;
    }

    public List<String> validate(File xmlFile) throws IOException, JAXBException {
        byte[] bytes = Files.readAllBytes(xmlFile.toPath());
        return validate(bytes);
    }
}
