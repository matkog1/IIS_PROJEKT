package hr.algebra.validationservers.soap.model;

import hr.algebra.validationservers.model.SOAPSearchRequest;
import hr.algebra.validationservers.model.SOAPSearchResponse;
import hr.algebra.validationservers.service.XmlMakerFromDb;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import org.w3c.dom.*;

import java.io.File;
import java.util.List;

@Endpoint
@RequiredArgsConstructor
public class SOAPSearch {

    private final XmlMakerFromDb xmlMaker;

    private static final String NAMESPACE_URI = "http://algebra.hr/property";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SOAPSearchRequest")
    @ResponsePayload
    public SOAPSearchResponse search(@RequestPayload SOAPSearchRequest request) throws Exception {
        String field = request.getField();
        String value = request.getValue();

        List<String> allowedFields = List.of("id", "price", "bedrooms", "bathrooms", "city","state", "yearBuilt");
        if (!allowedFields.contains(field)) {
            return new SOAPSearchResponse("Invalid field: " + field + ". Allowed fields for searching are: " + allowedFields);

        }

        File file = xmlMaker.generateXml();
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        XPath xpath = XPathFactory.newInstance().newXPath();
        String xpathExpression = String.format("//property[%s='%s']", field, value);

        NodeList nodes = (NodeList)xpath.evaluate(xpathExpression, doc, XPathConstants.NODESET);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            NodeList children = node.getChildNodes();
            result.append("Match found:\n");
            for (int j = 0; j < children.getLength(); j++) {
                Node child = children.item(j);
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    result.append(child.getNodeName())
                            .append(": ")
                            .append(child.getTextContent())
                            .append("\n");
                }
            }
            result.append("\n");
        }

        return new SOAPSearchResponse(result.length() > 0 ? result.toString() : "No matches for: " + value);
    }
}
