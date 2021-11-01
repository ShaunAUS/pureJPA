package hello2Jpa;


import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Entity
@Embeddable
public class Address {


    private String city;
    private String zipcode;
    private int street;


    public Address(String city, String zipcode, int street) {
        this.city = city;
        this.zipcode = zipcode;
        this.street = street;
    }

    public Address() {
    }
}
