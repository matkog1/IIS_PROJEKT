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
        File xsdFile = context.getResource("classpath:xsd/property.xsd").getFile();
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        schema = factory.newSchema(xsdFile);
    }

    public List<String> validate(byte[] xml) {
        List<String> errors = new ArrayList<>();
        try {
            Validator validator = schema.newValidator();
            validator.setErrorHandler(new SimpleErrorHandler(errors));
            validator.validate(new StreamSource(new ByteArrayInputStream(xml)));
        } catch (Exception e) {
            errors.add("Error: " + e.getMessage());
        }
        return errors;
    }

    private static class SimpleErrorHandler implements ErrorHandler {
        private final List<String> errors;

        public SimpleErrorHandler(List<String> errors) {
            this.errors = errors;
        }

        @Override
        public void warning(SAXParseException exception) {}

        @Override
        public void error(SAXParseException exception) {
            errors.add("Invalid XML: " + exception.getMessage());
        }

        @Override
        public void fatalError(SAXParseException exception) {}
    }
}
