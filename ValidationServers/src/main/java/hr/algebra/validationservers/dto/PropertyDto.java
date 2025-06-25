package hr.algebra.validationservers.dto;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class PropertyDto {

    private String message;
    private String Source;

    private PropertyAddress PropertyAddress;

    private int zestimate;
    private int Bedrooms;
    private int Bathrooms;

    @XmlElement(name = "AreaSqft")
    private int areaSqft;

    private int PropertyZPID;
    private int Price;
    private int yearBuilt;
    private int daysOnZillow;
    private String PropertyZillowURL;

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class PropertyAddress {
        private String streetAddress;
        private String city;
        private String state;
        private String zipcode;
        private String neighborhood;
        private String community;
        private String subdivision;

        public String getStreetAddress() {
            return streetAddress;
        }

        public void setStreetAddress(String streetAddress) {
            this.streetAddress = streetAddress;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getNeighborhood() {
            return neighborhood;
        }

        public void setNeighborhood(String neighborhood) {
            this.neighborhood = neighborhood;
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public String getSubdivision() {
            return subdivision;
        }

        public void setSubdivision(String subdivision) {
            this.subdivision = subdivision;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public PropertyAddress getPropertyAddress() {
        return PropertyAddress;
    }

    public void setPropertyAddress(PropertyAddress propertyAddress) {
        PropertyAddress = propertyAddress;
    }

    public int getZestimate() {
        return zestimate;
    }

    public void setZestimate(int zestimate) {
        this.zestimate = zestimate;
    }

    public int getBedrooms() {
        return Bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        Bedrooms = bedrooms;
    }

    public int getBathrooms() {
        return Bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        Bathrooms = bathrooms;
    }

    public int getAreaSqft() {
        return areaSqft;
    }

    public void setAreaSqft(int areaSqft) {
        this.areaSqft = areaSqft;
    }

    public int getPropertyZPID() {
        return PropertyZPID;
    }

    public void setPropertyZPID(int propertyZPID) {
        PropertyZPID = propertyZPID;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuilt(int yearBuilt) {
        this.yearBuilt = yearBuilt;
    }

    public int getDaysOnZillow() {
        return daysOnZillow;
    }

    public void setDaysOnZillow(int daysOnZillow) {
        this.daysOnZillow = daysOnZillow;
    }

    public String getPropertyZillowURL() {
        return PropertyZillowURL;
    }

    public void setPropertyZillowURL(String propertyZillowURL) {
        PropertyZillowURL = propertyZillowURL;
    }
}
