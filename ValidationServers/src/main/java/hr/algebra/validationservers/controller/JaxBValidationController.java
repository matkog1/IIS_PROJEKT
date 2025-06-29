package hr.algebra.validationservers.controller;

import hr.algebra.validationservers.service.JaxbValidation;
import hr.algebra.validationservers.service.XmlMakerFromDb;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/xml/jaxb")
@RequiredArgsConstructor
public class JaxBValidationController {
    private final XmlMakerFromDb xmlMaker;
    private final JaxbValidation jaxbListValidator;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validateFullList() {
        try {
            File xmlFile = xmlMaker.generateXml();
            List<String> errors = jaxbListValidator.validate(xmlFile);
            if (errors.isEmpty()) {
                return ResponseEntity.ok(Map.of("valid", true));
            }
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("valid", false, "errors", errors));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("valid", false, "error", e.getMessage()));
        }
    }
}
