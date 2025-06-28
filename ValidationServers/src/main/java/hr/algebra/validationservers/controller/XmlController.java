package hr.algebra.validationservers.controller;

import hr.algebra.validationservers.dto.PropertyDto;
import hr.algebra.validationservers.service.*;
import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/xml")
@RequiredArgsConstructor
public class XmlController {

    private final XsdValidator xsdValidator;
    private final RngValidator rngValidator;
    private final XsdValidatorPropertyList propertyListValidator;
    private final PropertyMapper mapper;
    private final PropertyMapperDB dbmapper;
    private final String xmlPath =  "validationservers/src/main/java/hr/algebra/validationservers/generatedXmls/properties.xml";

    @PostMapping(value = "/xsd", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> validteWtihXsd(@RequestBody byte[] xml) {
        List<String> errors = xsdValidator.validate(xml);

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("valid", false, "errors", errors));
        }

        try {
            PropertyDto dto = mapper.fromXml(xml);
            dbmapper.save(dto);
            return ResponseEntity.ok(Map.of("valid", true, "preview", dto));
        } catch (JAXBException e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("valid", false, "errors", List.of("JAXB error: " + e.getMessage())));
        }
    }

    @PostMapping(value = "/rng", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> validateWithRng(@RequestBody byte[] xml) {
        List<String> errors = rngValidator.validate(xml);

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("valid", false, "errors", errors));
        }

        try {
            PropertyDto dto = mapper.fromXml(xml);
            dbmapper.save(dto);
            return ResponseEntity.ok(Map.of("valid", true, "preview", dto));
        } catch (JAXBException e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("valid", false, "errors", List.of("JAXB error: " + e.getMessage())));
        }
    }

    @GetMapping("propertyListXsd")
    public ResponseEntity<?> validateSoapXml() {
        try {
            File file = new File(xmlPath);
            List<String> errors = propertyListValidator.validate(file);

            if (errors.isEmpty()) {
                return ResponseEntity.ok(Map.of("valid", true));
            } else {
                return ResponseEntity.badRequest().body(Map.of("valid", false, "errors", errors));
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("valid", false, "error", e.getMessage()));
        }
    }

}
