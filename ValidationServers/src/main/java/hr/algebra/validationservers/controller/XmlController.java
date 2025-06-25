package hr.algebra.validationservers.controller;

import hr.algebra.validationservers.dto.PropertyDto;
import hr.algebra.validationservers.service.PropertyMapper;
import hr.algebra.validationservers.service.XsdValidator;
import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/xml")
@RequiredArgsConstructor
public class XmlController {

    private final XsdValidator validator;
    private final PropertyMapper mapper;

    @PostMapping(value = "/xsd", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> validateAndPreview(@RequestBody byte[] xml) {
        List<String> errors = validator.validate(xml);

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("valid", false, "errors", errors));
        }

        try {
            PropertyDto dto = mapper.fromXml(xml);
            return ResponseEntity.ok(Map.of("valid", true, "preview", dto));
        } catch (JAXBException e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("valid", false, "errors", List.of("JAXB error: " + e.getMessage())));
        }
    }
}
