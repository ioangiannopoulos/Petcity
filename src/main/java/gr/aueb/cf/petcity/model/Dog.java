package gr.aueb.cf.petcity.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "DOGS")
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @Positive(message = "Age must be a positive number")
    @Min(value = 1, message = "Age must be at least 1")
    @Max(value = 32, message = "Age must not exceed 32")
    private Integer age;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private PetOwner owner;

    public Dog() {}

    public Dog(Long id, String name, Integer age, PetOwner owner) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PetOwner getOwner() {
        return owner;
    }

    public void setOwner(PetOwner owner) {
        this.owner = owner;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dog dog = (Dog) o;

        if (!id.equals(dog.id)) return false;
        if (!name.equals(dog.name)) return false;
        if (!age.equals(dog.age)) return false;
        return owner.equals(dog.owner);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + age.hashCode();
        result = 31 * result + owner.hashCode();
        return result;
    }
}
