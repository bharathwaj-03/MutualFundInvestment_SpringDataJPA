package com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response;

import java.time.LocalDate;

/**
 * Data transfer object used to return investor profile information to API clients.
 *
 * This DTO keeps HTTP payload data separate from persistence entities and service-layer models.
 */
public class InvestorProfileResponse {

    /**
     * Unique identifier of the user attempting authentication.
     */
    private String userId;
    /**
     * Name carried by the request or response object.
     */
    private String name;
    /**
     * Value representing the email.
     */
    private String email;
    /**
     * Value representing the phone number.
     */
    private String phoneNumber;
    /**
     * Application role assigned to the user.
     */
    private String userRole;

    /**
     * Age of the person represented by the response.
     */
    private int age;

    /**
     * Value representing the pan number.
     */
    private String panNumber;
    /**
     * Bank account number associated with the payment or profile.
     */
    private String accountNumber;

    /**
     * Date on which the investor account was registered.
     */
    private LocalDate registrationDate;

    /**
     * Risk profile selected for the investor.
     */
    private String riskProfile;

    /**
     * Indicates whether the investor account is active.
     */
    private boolean active;

    /**
     * Nominee details associated with the investor profile.
     */
    private NomineeProfileResponse nominee;


    /**
     * Creates a InvestorProfileResponse object. This no-argument constructor supports request/response binding and object creation.
     */
    public InvestorProfileResponse() {
    }


    /**
     * Creates a InvestorProfileResponse object with the supplied data.
     * @param userId Unique identifier of the user attempting authentication.
     * @param name Name carried by the request or response object.
     * @param email email
     * @param phoneNumber phone number
     * @param userRole Application role assigned to the user.
     * @param age Age of the person represented by the response.
     * @param panNumber pan number
     * @param accountNumber Bank account number associated with the payment or profile.
     * @param registrationDate Date on which the investor account was registered.
     * @param riskProfile Risk profile selected for the investor.
     * @param active Indicates whether the investor account is active.
     * @param nominee Nominee details associated with the investor profile.
     */
    public InvestorProfileResponse(
            String userId,
            String name,
            String email,
            String phoneNumber,
            String userRole,
            int age,
            String panNumber,
            String accountNumber,
            LocalDate registrationDate,
            String riskProfile,
            boolean active,
            NomineeProfileResponse nominee) {

        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userRole = userRole;
        this.age = age;
        this.panNumber = panNumber;
        this.accountNumber = accountNumber;
        this.registrationDate = registrationDate;
        this.riskProfile = riskProfile;
        this.active = active;
        this.nominee = nominee;
    }


    /**
     * Returns the user id.
     * @return unique identifier of the user attempting authentication.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Updates the user id carried by this DTO.
     * @param userId unique identifier of the user attempting authentication.
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
     * Returns the email.
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Updates the email carried by this DTO.
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number.
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Updates the phone number carried by this DTO.
     * @param phoneNumber phone number
     */
    public void setPhoneNumber(
            String phoneNumber) {

        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the user role.
     * @return application role assigned to the user.
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * Updates the user role carried by this DTO.
     * @param userRole application role assigned to the user.
     */
    public void setUserRole(
            String userRole) {

        this.userRole = userRole;
    }

    /**
     * Returns the age.
     * @return age of the person represented by the response.
     */
    public int getAge() {
        return age;
    }

    /**
     * Updates the age carried by this DTO.
     * @param age age of the person represented by the response.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns the pan number.
     * @return pan number
     */
    public String getPanNumber() {
        return panNumber;
    }

    /**
     * Updates the pan number carried by this DTO.
     * @param panNumber pan number
     */
    public void setPanNumber(
            String panNumber) {

        this.panNumber = panNumber;
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

    /**
     * Returns the registration date.
     * @return date on which the investor account was registered.
     */
    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Updates the registration date carried by this DTO.
     * @param registrationDate date on which the investor account was registered.
     */
    public void setRegistrationDate(
            LocalDate registrationDate) {

        this.registrationDate =
                registrationDate;
    }

    /**
     * Returns the risk profile.
     * @return risk profile selected for the investor.
     */
    public String getRiskProfile() {
        return riskProfile;
    }

    /**
     * Updates the risk profile carried by this DTO.
     * @param riskProfile risk profile selected for the investor.
     */
    public void setRiskProfile(
            String riskProfile) {

        this.riskProfile = riskProfile;
    }

    /**
     * Returns the active.
     * @return indicates whether the investor account is active.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Updates the active carried by this DTO.
     * @param active indicates whether the investor account is active.
     */
    public void setActive(
            boolean active) {

        this.active = active;
    }

    /**
     * Returns the nominee.
     * @return nominee details associated with the investor profile.
     */
    public NomineeProfileResponse getNominee() {
        return nominee;
    }

    /**
     * Updates the nominee carried by this DTO.
     * @param nominee nominee details associated with the investor profile.
     */
    public void setNominee(
            NomineeProfileResponse nominee) {

        this.nominee = nominee;
    }
}