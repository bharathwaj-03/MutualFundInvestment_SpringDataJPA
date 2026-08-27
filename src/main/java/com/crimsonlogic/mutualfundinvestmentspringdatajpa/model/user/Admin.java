package com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.abstraction.User;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Represents an administrator account used to manage mutual-fund operations.
 *
 * JPA maps this entity to its corresponding database representation.
 */
@Entity
@Table(name = "admin")
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends User {

    /**
     * Administrative code assigned to the administrator.
     */
    @Column(name = "admin_code", unique = true, length = 20)
    private String adminCode;

    /**
     * Date on which the administrator record was created.
     */
    @Column(name = "created_date")
    private LocalDate createdDate;

    /**
     * Default constructor required for object creation and JPA entity instantiation where applicable.
     */
    public Admin() {
    }

    /**
     * Returns the admin code.
     *
     * @return admin code
     */
    public String getAdminCode() {
        return adminCode;
    }

    /**
     * Updates the admin code.
     *
     * @param adminCode new admin code value
     */
    public void setAdminCode(String adminCode) {
        this.adminCode = adminCode;
    }

    /**
     * Returns the created date.
     *
     * @return created date
     */
    public LocalDate getCreatedDate() {
        return createdDate;
    }

    /**
     * Updates the created date.
     *
     * @param createdDate new created date value
     */
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Returns a readable representation of the Admin object.
     *
     * @return result produced by the to string operation
     */
    @Override
    public String toString() {
        return "Admin{" +
                "userId='" + getUserId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", adminCode='" + adminCode + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }

    /**
     * Compares this Admin with another object for logical equality.
     *
     * @param o o supplied to the operation
     *
     * @return result produced by the equals operation
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(adminCode, admin.adminCode);
    }

    /**
     * Returns a hash code consistent with the equality definition of Admin.
     *
     * @return result produced by the hash code operation
     */
    @Override
    public int hashCode() {
        return Objects.hash(adminCode);
    }
}
