package gr.aueb.cf.petcity.model;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PET_OWNERS")
public class PetOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 3, max = 50)
    private String firstname;
    @NotBlank
    @Size(min = 3, max = 50)
    private String lastname;
    @NotNull
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNumber;
    @NotNull
    @Email(message = "Invalid email format")
    private String email;

    @OneToMany(mappedBy= "owner", cascade=CascadeType.ALL)
    private List<Dog> dogs = new ArrayList<>();

    public PetOwner() {}

    public PetOwner(Long id, String firstname, String lastname, String phoneNumber, String email, List<Dog> dogs) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dogs = dogs;
    }

    public PetOwner(String firstname, String lastname, String phoneNumber, String email, List<Dog> dogs) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dogs = dogs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(List<Dog> dogs) {
        this.dogs = dogs;
    }

    public void addDog(Dog dog) {
        dogs.add(dog);
        dog.setOwner(this);
    }

    public void removeDog(Dog dog) {
        dogs.remove(dog);
        dog.setOwner(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PetOwner petOwner = (PetOwner) o;

        if (!id.equals(petOwner.id)) return false;
        if (!firstname.equals(petOwner.firstname)) return false;
        if (!lastname.equals(petOwner.lastname)) return false;
        if (!phoneNumber.equals(petOwner.phoneNumber)) return false;
        if (!email.equals(petOwner.email)) return false;
        return dogs.equals(petOwner.dogs);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + firstname.hashCode();
        result = 31 * result + lastname.hashCode();
        result = 31 * result + phoneNumber.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + dogs.hashCode();
        return result;
    }
}
