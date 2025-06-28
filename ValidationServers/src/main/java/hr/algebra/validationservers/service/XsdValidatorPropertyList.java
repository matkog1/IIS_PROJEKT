package hr.algebra.validationservers.service;

import org.springframework.stereotype.Service;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import org.springframework.context.ApplicationContext;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class XsdValidatorPropertyList {
    private final Schema schema;

    public XsdValidatorPropertyList(ApplicationContext context) throws Exception {
        File xsdFile = context.getResource("classpath:xsd/propertyList.xsd").getFile();
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        this.schema = factory.newSchema(xsdFile);
    }

    public List<String> validate(File xmlFile) {
        List<String> errors = new ArrayList<>();
        try {
            Validator validator = schema.newValidator();
            validator.setErrorHandler(new ErrorHandler() {
                public void warning(SAXParseException e) {}
                public void error(SAXParseException e) { errors.add("Invalid XML: " + e.getMessage()); }
                public void fatalError(SAXParseException e) { errors.add("Fatal error: " + e.getMessage()); }
            });

            validator.validate(new StreamSource(xmlFile));
        } catch (Exception e) {
            errors.add("Validation exception: " + e.getMessage());
        }

        return errors;
    }
}
