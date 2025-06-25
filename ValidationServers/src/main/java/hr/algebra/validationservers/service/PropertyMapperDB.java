package hr.algebra.validationservers.service;
import hr.algebra.validationservers.dto.PropertyDto;
import hr.algebra.validationservers.model.Property;
import hr.algebra.validationservers.repo.PropertyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropertyMapperDB {
    private final PropertyRepo repository;

    public Property save(PropertyDto dto) {
        Property p = new Property();
        p.setMessage(dto.getMessage());
        p.setSource(dto.getSource());
        p.setStreetAddress(dto.getPropertyAddress().getStreetAddress());
        p.setCity(dto.getPropertyAddress().getCity());
        p.setState(dto.getPropertyAddress().getState());
        p.setZipcode(dto.getPropertyAddress().getZipcode());
        p.setNeighborhood(dto.getPropertyAddress().getNeighborhood());
        p.setCommunity(dto.getPropertyAddress().getCommunity());
        p.setSubdivision(dto.getPropertyAddress().getSubdivision());
        p.setZestimate(dto.getZestimate());
        p.setBedrooms(dto.getBedrooms());
        p.setBathrooms(dto.getBathrooms());
        p.setAreaSqft(dto.getAreaSqft());
        p.setPropertyZPID(dto.getPropertyZPID());
        p.setPrice(dto.getPrice());
        p.setYearBuilt(dto.getYearBuilt());
        p.setDaysOnZillow(dto.getDaysOnZillow());
        p.setPropertyZillowURL(dto.getPropertyZillowURL());
        return repository.save(p);
}
}
