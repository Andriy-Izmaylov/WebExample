package ua.org.oa.expertiza;

/**
 * Created by expertiza on 03.11.2015.
 */
public class Address {
    private int adressId;
    private String country;
    private String street;
    private int zipCode;
    private  User user;

    public int getAdressId() {
        return adressId;
    }

    public void setAdressId(int adressId) {
        this.adressId = adressId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Address{" +
                "adressId=" + adressId +
                ", country='" + country + '\'' +
                ", street='" + street + '\'' +
                ", zipCode=" + zipCode +
                ", user=" + user +
                '}';
    }
}
