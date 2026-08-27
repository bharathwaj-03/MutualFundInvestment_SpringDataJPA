package com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response;

/**
 * Data transfer object used to return nominee profile information to API clients.
 *
 * This DTO keeps HTTP payload data separate from persistence entities and service-layer models.
 */
public class NomineeProfileResponse {

    /**
     * Unique identifier of the investor nominee.
     */
    private String nomineeId;
    /**
     * Name carried by the request or response object.
     */
    private String name;
    /**
     * Age of the person represented by the response.
     */
    private Integer age;
    /**
     * Gender of the nominee.
     */
    private String gender;
    /**
     * Relationship of the nominee to the investor.
     */
    private String relationship;
    /**
     * Bank account number associated with the payment or profile.
     */
    private String accountNumber;


    /**
     * Creates a NomineeProfileResponse object. This no-argument constructor supports request/response binding and object creation.
     */
    public NomineeProfileResponse() {
    }


    /**
     * Creates a NomineeProfileResponse object with the supplied data.
     * @param nomineeId Unique identifier of the investor nominee.
     * @param name Name carried by the request or response object.
     * @param age Age of the person represented by the response.
     * @param gender Gender of the nominee.
     * @param relationship Relationship of the nominee to the investor.
     * @param accountNumber Bank account number associated with the payment or profile.
     */
    public NomineeProfileResponse(
            String nomineeId,
            String name,
            Integer age,
            String gender,
            String relationship,
            String accountNumber) {

        this.nomineeId = nomineeId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.relationship = relationship;
        this.accountNumber = accountNumber;
    }


    /**
     * Returns the nominee id.
     * @return unique identifier of the investor nominee.
     */
    public String getNomineeId() {
        return nomineeId;
    }

    /**
     * Updates the nominee id carried by this DTO.
     * @param nomineeId unique identifier of the investor nominee.
     */
    public void setNomineeId(String nomineeId) {
        this.nomineeId = nomineeId;
    }

    /**
     * Returns the name.
     * @return name carried by the request or response object.
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the name carried by this DTO.
     * @param name name carried by the request or response object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the age.
     * @return age of the person represented by the response.
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Updates the age carried by this DTO.
     * @param age age of the person represented by the response.
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Returns the gender.
     * @return gender of the nominee.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Updates the gender carried by this DTO.
     * @param gender gender of the nominee.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns the relationship.
     * @return relationship of the nominee to the investor.
     */
    public String getRelationship() {
        return relationship;
    }

    /**
     * Updates the relationship carried by this DTO.
     * @param relationship relationship of the nominee to the investor.
     */
    public void setRelationship(
            String relationship) {

        this.relationship = relationship;
    }

    /**
     * Returns the account number.
     * @return bank account number associated with the payment or profile.
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Updates the account number carried by this DTO.
     * @param accountNumber bank account number associated with the payment or profile.
     */
    public void setAccountNumber(
            String accountNumber) {

        this.accountNumber = accountNumber;
    }
}