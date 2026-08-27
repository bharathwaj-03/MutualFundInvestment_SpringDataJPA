package com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Request DTO used to validate editable investor profile information.
 */
public class InvestorProfileUpdateRequest {

    @NotBlank(message = "Name is required.")
    @Size(
            min = 3,
            max = 50,
            message = "Name must contain 3 to 50 characters."
    )
    @Pattern.List({
            @Pattern(
                    regexp = "^[A-Za-z]+(?: [A-Za-z]+)*$",
                    message =
                            "Name should contain only alphabets and spaces."
            ),
            @Pattern(
                    regexp =
                            "(?i)^(?!.*([a-z])\\1\\1).*$",
                    message =
                            "Name should not contain the same character "
                                    + "3 times continuously."
            )
    })
    private String name;


    @NotBlank(message = "Email is required.")
    @Pattern(
            regexp =
                    "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message =
                    "Please enter a valid email address. Ex: name@company.com"
    )
    private String email;


    @NotBlank(message = "Phone number is required.")
    @Pattern(
            regexp = "^[6-9][0-9]{9}$",
            message =
                    "Please enter a valid 10-digit phone number."
    )
    private String phoneNumber;


    @NotBlank(message = "PAN is required.")
    @Pattern(
            regexp = "^[A-Z]{5}[0-9]{4}[A-Z]$",
            message =
                    "Please enter a valid PAN "
                            + "(5 letters in CAPS followed by 4 digits and 1 letter)."
    )
    private String panNumber;


    @NotBlank(message = "Account number is required.")
    @Pattern(
            regexp = "^\\d{9,18}$",
            message =
                    "Account number must contain 9 to 18 digits."
    )
    private String accountNumber;


    @NotBlank(message = "Risk profile is required.")
    private String riskProfile;


    @NotNull(message = "Nominee details are required.")
    @Valid
    private NomineeProfileUpdateRequest nominee;


    public InvestorProfileUpdateRequest() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(
            String phoneNumber) {

        this.phoneNumber = phoneNumber;
    }


    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(
            String panNumber) {

        this.panNumber = panNumber;
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(
            String accountNumber) {

        this.accountNumber = accountNumber;
    }


    public String getRiskProfile() {
        return riskProfile;
    }

    public void setRiskProfile(
            String riskProfile) {

        this.riskProfile = riskProfile;
    }


    public NomineeProfileUpdateRequest
    getNominee() {

        return nominee;
    }

    public void setNominee(
            NomineeProfileUpdateRequest nominee) {

        this.nominee = nominee;
    }
}