package hr.algebra.validationservers.service;

import com.thaiopensource.validate.ValidationDriver;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class RngValidator {

    public List<String> validate(byte[] xmlBytes) {
        List<String> errors = new ArrayList<>();

        try {
            Resource rngFile = new ClassPathResource("rng/property.rng");
            InputSource rng = new InputSource(rngFile.getInputStream());
            InputSource xml = new InputSource(new ByteArrayInputStream(xmlBytes));

            ValidationDriver driver = new ValidationDriver();
            if (!driver.loadSchema(rng)) {
                errors.add("Could not load RELAX NG schema.");
                return errors;
            }

            if (!driver.validate(xml)) {
                errors.add("XML is not valid according to the RNG schema.");
            }

        } catch (Exception e) {
            errors.add("RNG validation error: " + e.getMessage());
        }

        return errors;
    }
}
