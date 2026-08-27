package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents nominee information associated with an investor account.
 *
 * JPA maps this entity to its corresponding database representation.
 */
@Entity
@Table(name = "nominee")
public class Nominee {

    /**
     * Unique identifier of the nominee.
     */
    @Id
    @Column(name = "nominee_id", length = 20)
    private String nomineeId;

    /**
     * Name associated with the entity.
     */
    @Column(name = "name", length = 100)
    private String name;

    /**
     * Age value associated with the person.
     */
    @Column(name = "age")
    private int age;

    /**
     * Gender recorded for the nominee.
     */
    @Column(name = "gender", length = 20)
    private String gender;

    /**
     * Relationship of the nominee to the investor.
     */
    @Column(name = "relationship", length = 50)
    private String relationship;

    /**
     * Bank account number associated with the record.
     */
    @Column(name = "account_number")
    private String accountNumber;

    /**
     * Default constructor required for object creation and JPA entity instantiation where applicable.
     */
    public Nominee() {
    }

    /**
     * Returns the nominee id.
     *
     * @return nominee id
     */
    public String getNomineeId() {
        return nomineeId;
    }

    /**
     * Updates the nominee id.
     *
     * @param nomineeId new nominee id value
     */
    public void setNomineeId(String nomineeId) {
        this.nomineeId = nomineeId;
    }

    /**
     * Returns the name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the name.
     *
     * @param name new name value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the age.
     *
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * Updates the age.
     *
     * @param age new age value
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns the gender.
     *
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Updates the gender.
     *
     * @param gender new gender value
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns the relationship.
     *
     * @return relationship
     */
    public String getRelationship() {
        return relationship;
    }

    /**
     * Updates the relationship.
     *
     * @param relationship new relationship value
     */
    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    /**
     * Returns the account number.
     *
     * @return account number
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Updates the account number.
     *
     * @param accountNumber new account number value
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Returns a readable representation of the Nominee object.
     *
     * @return result produced by the to string operation
     */
    @Override
    public String toString() {
        return "Nominee{" +
                "nomineeId='" + nomineeId + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", relationship='" + relationship + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }

    /**
     * Compares this Nominee with another object for logical equality.
     *
     * @param o o supplied to the operation
     *
     * @return result produced by the equals operation
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nominee nominee = (Nominee) o;
        return age == nominee.age
                && Objects.equals(nomineeId, nominee.nomineeId)
                && Objects.equals(name, nominee.name)
                && Objects.equals(gender, nominee.gender)
                && Objects.equals(relationship, nominee.relationship);
    }

    /**
     * Returns a hash code consistent with the equality definition of Nominee.
     *
     * @return result produced by the hash code operation
     */
    @Override
    public int hashCode() {
        return Objects.hash(nomineeId, name, age, gender, relationship);
    }
}
