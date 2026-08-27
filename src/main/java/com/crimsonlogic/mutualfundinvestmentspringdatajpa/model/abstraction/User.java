package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Base JPA entity that stores common identity and authentication details shared by application users.
 *
 * JPA maps this entity to its corresponding database representation.
 */
@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    /**
     * Unique identifier of the user.
     */
    @Id
    @Column(name = "user_id", length = 20)
    private String userId;

    /**
     * Name associated with the entity.
     */
    @Column(name = "name", length = 100)
    private String name;

    /**
     * Email address used for user contact and identification.
     */
    @Column(name = "email", length = 100)
    private String email;

    /**
     * Contact phone number of the user.
     */
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    /**
     * Stored user password value used for authentication.
     */
    @Column(name = "password", length = 100)
    private String password;

    /**
     * Role assigned to the user for authorization.
     */
    @Column(name = "user_role", length = 20)
    private String userRole;

    /**
     * Age value associated with the person.
     */
    // The current user table has no age column. Kept only for compatibility with your old model.
    @Transient
    private int age;

    /**
     * Default constructor required for object creation and JPA entity instantiation where applicable.
     */
    public User() {
    }

    /**
     * Returns the user id.
     *
     * @return user id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Updates the user id.
     *
     * @param userId new user id value
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
     * Returns the email.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Updates the email.
     *
     * @param email new email value
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number.
     *
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Updates the phone number.
     *
     * @param phoneNumber new phone number value
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Updates the password.
     *
     * @param password new password value
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the user role.
     *
     * @return user role
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * Updates the user role.
     *
     * @param userRole new user role value
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
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
}
