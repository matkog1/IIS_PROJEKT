package hr.algebra.validationservers.service;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class XsdValidator {

    private final Schema schema;

    public XsdValidator(ApplicationContext context) throws Exception {
        File xsd = context.getResource("classpath:xsd/property.xsd").getFile();
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        schema = factory.newSchema(xsd);
    }

    public List<String> validate(byte[] xml) {
        List<String> errors = new ArrayList<>();
        try {
            Validator validator = schema.newValidator();
            validator.setErrorHandler(new ErrorHandler() {
                public void error(SAXParseException e) { errors.add(e.getMessage()); }
                public void fatalError(SAXParseException e) { errors.add(e.getMessage()); }
                public void warning(SAXParseException e) {}
            });
            validator.validate(new StreamSource(new ByteArrayInputStream(xml)));
        } catch (Exception e) {
            errors.add("Fatal error: " + e.getMessage());
        }
        return errors;
    }
}
