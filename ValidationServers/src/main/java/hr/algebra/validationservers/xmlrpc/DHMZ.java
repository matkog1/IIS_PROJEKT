package hr.algebra.validationservers.xmlrpc;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class DHMZ {
    public List<String> getTemperaturesByCity(String cityPart) throws Exception {
        List<String> rezultati = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new URL("https://vrijeme.hr/hrvatska_n.xml").openStream());
        doc.getDocumentElement().normalize();
        NodeList gradList = doc.getElementsByTagName("Grad");

        for (int i = 0; i < gradList.getLength(); i++) {
            Node gradNode = gradList.item(i);

            if (gradNode.getNodeType() == Node.ELEMENT_NODE) {
                Element gradElement = (Element) gradNode;

                String cityName = gradElement.getElementsByTagName("GradIme").item(0).getTextContent().trim();

                if (cityName.toLowerCase().contains(cityPart.toLowerCase())) {
                    Element podatciElement = (Element) gradElement.getElementsByTagName("Podatci").item(0);
                    String temp = podatciElement.getElementsByTagName("Temp").item(0).getTextContent().trim();
                    rezultati.add(cityName + ": " + temp + " °C");
                }
            }
        }

        return rezultati;
    }

}
