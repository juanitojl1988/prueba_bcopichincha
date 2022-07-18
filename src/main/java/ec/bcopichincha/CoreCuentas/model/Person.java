package ec.bcopichincha.CoreCuentas.model;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identification_card", length = 15, nullable = false,unique=true)
    private String identificationCard;

    @Column(name = "name", length = 62, nullable = false)
    private String name;

    @Column(name = "gender", length = 1, nullable = false)
    private Character gender;

    @Column(name = "age", nullable = true)
    private Integer age;

    @Column(name = "address", length = 120, nullable = true)
    private String address;

    @Column(name = "telephone", length = 32, nullable = true)
    private String telephone;

    public Person() {
    }

    public Person(Long id) {
        this.id = id;
    }

    public Person(String identificationCard, String name, Character gender, Integer age, String address,
            String telephone) {
        this.identificationCard = identificationCard;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.telephone = telephone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificationCard() {
        return identificationCard;
    }

    public void setIdentificationCard(String identificationCard) {
        this.identificationCard = identificationCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}
