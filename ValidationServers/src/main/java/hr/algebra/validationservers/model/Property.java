package hr.algebra.validationservers.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String source;
    private String streetAddress;
    private String city;
    private String state;
    private String zipcode;
    private String neighborhood;
    private String community;
    private String subdivision;
    private int zestimate;
    private int bedrooms;
    private int bathrooms;
    private int areaSqft;
    private int propertyZPID;
    private int price;
    private int yearBuilt;
    private int daysOnZillow;
    private String propertyZillowURL;
}
