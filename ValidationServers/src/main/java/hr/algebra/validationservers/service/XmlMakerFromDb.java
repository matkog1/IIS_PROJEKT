package hr.algebra.validationservers.service;

import hr.algebra.validationservers.dto.PropertyListDto;
import hr.algebra.validationservers.model.Property;
import hr.algebra.validationservers.repo.PropertyRepo;
import hr.algebra.validationservers.soap.model.PropertyList;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
@Service
@RequiredArgsConstructor
public class XmlMakerFromDb {

    private final PropertyRepo repo;
    private final String folderPath =  "validationservers/src/main/java/hr/algebra/validationservers/generatedXmls/properties.xml";

    public File generateXml() throws Exception {
        List<Property> properties = repo.findAll();

        PropertyList wrapper = new PropertyList();
        wrapper.setProperties(properties);

        JAXBContext context = JAXBContext.newInstance(PropertyList.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        File file = new File(folderPath);
        file.getParentFile().mkdirs();
        marshaller.marshal(wrapper, file);
        System.out.println("XML saved to: " + file.getAbsolutePath());
        return file;
}
}
