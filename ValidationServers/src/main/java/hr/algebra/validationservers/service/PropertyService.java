package hr.algebra.validationservers.service;

import hr.algebra.validationservers.dto.PropertyDto;
import hr.algebra.validationservers.model.Property;
import hr.algebra.validationservers.repo.PropertyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepo propertyRepo;

    public List<Property> getAll() {
        return propertyRepo.findAll();
    }

    public Optional<Property> getById(Long id) {
        return propertyRepo.findById(id);
    }

    public Property create(Property property) {
        return propertyRepo.save(property);
    }

    public Optional<Property> update(Long id, Property updatedProperty) {
        return propertyRepo.findById(id).map(existing -> {
            updatedProperty.setId(existing.getId());
            return propertyRepo.save(updatedProperty);
        });
    }

    public boolean delete(Long id) {
        if (propertyRepo.existsById(id)) {
            propertyRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
