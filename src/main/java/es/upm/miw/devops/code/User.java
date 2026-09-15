package es.upm.miw.devops.code;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    public enum Roll {
        USER,
        ADMIN
    }

    @Id
    private String id;

    private String name;
    private String familyName;
    private String email;
    private String identity;
    private String address;
    private String city;
    private String province;
    private String postalCode;
    private boolean active;
    private boolean billable;

    @Enumerated(EnumType.STRING)
    private Roll roll;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Fraction> fractions;

    public User() {
        this.fractions = new ArrayList<>();
    }

    public User(String id, String name, String familyName,
                String email, String identity, String address,
                String city, String province, String postalCode, Boolean active,Boolean billable,
                List<Fraction> fractions, Roll roll) {

        this.id = id;
        this.name = name;
        this.familyName = familyName;
        this.email = email;
        this.identity = identity;
        this.address = address;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.active=active;
        this.billable=billable;
        this.fractions = fractions;
        this.roll = roll;
    }

    public boolean isBillable() {
        return billable;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public Roll getRoll() {
        return roll;
    }

    public String getFamilyName() {
        return this.familyName;
    }

    public String getEmail() {
        return email;
    }

    public String getIdentity() {
        return identity;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setRoll(Roll roll) {
        this.roll = roll;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public void setIdentity(String identity) {
        this.identity = identity;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public List<Fraction> getFractions() {
        return fractions;
    }

    public void setFractions(List<Fraction> fractions) {
        this.fractions = fractions;
    }

    public void addFraction(Fraction fraction) {
        this.fractions.add(fraction);
    }

    public void setBillable(boolean billable) {
        this.billable = billable;
    }

    public String initials() {
        return this.name.charAt(0) + ".";
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + name + '\'' +
                ", initials='" + initials() + '\'' +
                ", familyName='" + familyName + '\'' +
                ", email='" + email + '\'' +
                ", identity='" + identity + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", fractions=" + fractions +
                ", active=" + active +
                ", billable=" + billable +
                ", roll=" + roll +
                '}';
    }

    public boolean calculateBillable() {
        return isNotBlank(name)
                && isNotBlank(familyName)
                && isNotBlank(email)
                && isNotBlank(identity)
                && isNotBlank(address)
                && isNotBlank(city)
                && isNotBlank(province)
                && isNotBlank(postalCode);
    }

    private boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }

}
