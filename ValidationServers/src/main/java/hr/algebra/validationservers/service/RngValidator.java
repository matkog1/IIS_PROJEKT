package hr.algebra.validationservers.service;

import com.thaiopensource.validate.ValidationDriver;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class RngValidator {

    public List<String> validate(byte[] xmlBytes) {
        List<String> errors = new ArrayList<>();

        ByteArrayOutputStream errBuffer = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errBuffer));

        try {
            Resource rngFile = new ClassPathResource("rng/property.rng");
            InputSource rng = new InputSource(rngFile.getInputStream());
            InputSource xml = new InputSource(new ByteArrayInputStream(xmlBytes));

            ValidationDriver driver = new ValidationDriver();

            if (!driver.loadSchema(rng)) {
                errors.add("Could not load RELAX NG schema.");
                return errors;
            }

            boolean valid = driver.validate(xml);

            if (!valid) {
                String rawError = errBuffer.toString();
                String formattedErrorMessage = formatErrorMessage(rawError);
                errors.add(formattedErrorMessage);
            }
        } catch (Exception e) {
            errors.add("RNG validation error: " + e.getMessage());
        }
        return errors;
    }

    private String formatErrorMessage(String rawError) {
        for (String line : rawError.split("\n")) {
            if (line.contains("missing required element")) {
                int secondQuoteStart = line.indexOf("missing required element \"");
                int prefixLength = "missing required element \"".length();
                int secondQuoteEnd = line.indexOf("\"", secondQuoteStart + prefixLength);
                if (secondQuoteStart != -1 && secondQuoteEnd != -1) {
                    String missingField = line.substring(secondQuoteStart + prefixLength, secondQuoteEnd);
                    return "The XML is missing a required field: '" + missingField + "'.";
                }
            }
        }
        return "Invalid XML: Structure does not match the RELAX NG schema.";
    }



}
