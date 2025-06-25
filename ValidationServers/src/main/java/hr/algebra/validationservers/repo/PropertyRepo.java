package hr.algebra.validationservers.repo;

import hr.algebra.validationservers.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepo extends JpaRepository<Property, Long> {
}
