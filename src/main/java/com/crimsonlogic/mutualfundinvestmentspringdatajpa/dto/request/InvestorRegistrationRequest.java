package com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Request DTO containing the information required to register an investor.
 *
 * Bean Validation annotations validate request data before the
 * registration service operation is executed.
 */
public class InvestorRegistrationRequest {

    /**
     * Full name of the investor.
     */
    @NotBlank(
            message = "Name is required."
    )
    @Size(
            min = 3,
            max = 50,
            message =
                    "Name must contain 3 to 50 characters."
    )
    @Pattern.List({

            @Pattern(
                    regexp =
                            "^[A-Za-z]+(?: [A-Za-z]+)*$",
                    message =
                            "Name should contain only alphabets and spaces."
            ),

            @Pattern(
                    regexp =
                            "(?i)^(?!.*([a-z])\\1\\1).*$",
                    message =
                            "Name should not contain the same character 3 times continuously."
            )
    })
    private String name;


    /**
     * Email address used by the investor.
     */
    @NotBlank(
            message = "Email is required."
    )
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Please enter a valid email address. Ex: name@company.com"
    )
    private String email;



    /**
     * Ten-digit Indian mobile number of the investor.
     */
    @NotBlank(
            message =
                    "Phone number is required."
    )
    @Pattern(
            regexp =
                    "^[6-9][0-9]{9}$",
            message =
                    "Please enter a valid 10-digit phone number."
    )
    private String phoneNumber;


    /**
     * Password supplied during investor registration.
     */
    @NotBlank(
            message =
                    "Password cannot be empty."
    )
    @Pattern(
            regexp =
                    "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)"
                            + "(?=.*[^a-zA-Z0-9]).{6,}$",
            message =
                    "Password must contain at least 6 characters, "
                            + "one uppercase letter, one lowercase letter, "
                            + "one digit and one special character."
    )
    private String password;


    /**
     * PAN number belonging to the investor.
     */
    @NotBlank(
            message =
                    "PAN is required."
    )
    @Pattern(
            regexp =
                    "^[A-Z]{5}[0-9]{4}[A-Z]$",
            message =
                    "Please enter a valid PAN "
                            + "(5 letters in CAPS followed by 4 digits and 1 letter)."
    )
    private String panNumber;


    /**
     * Bank account number of the investor.
     */
    @NotBlank(
            message =
                    "Account number is required."
    )
    @Pattern(
            regexp =
                    "^\\d{9,18}$",
            message =
                    "Account number must contain 9 to 18 digits."
    )
    private String accountNumber;


    /**
     * Risk profile selected for the investor.
     */
    private String riskProfile;


    /**
     * Nominee information supplied during registration.
     *
     * @Valid enables recursive validation of fields contained
     * inside NomineeRegistrationRequest.
     */
    @NotNull(
            message =
                    "Nominee details are required."
    )
    @Valid
    private NomineeRegistrationRequest nominee;


    public InvestorRegistrationRequest() {
    }


    public String getName() {
        return name;
    }


    public void setName(
            String name) {

        this.name = name;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(
            String email) {

        this.email = email;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setPhoneNumber(
            String phoneNumber) {

        this.phoneNumber =
                phoneNumber;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(
            String password) {

        this.password = password;
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

        this.accountNumber =
                accountNumber;
    }


    public String getRiskProfile() {
        return riskProfile;
    }


    public void setRiskProfile(
            String riskProfile) {

        this.riskProfile =
                riskProfile;
    }


    public NomineeRegistrationRequest
    getNominee() {

        return nominee;
    }


    public void setNominee(
            NomineeRegistrationRequest nominee) {

        this.nominee = nominee;
    }
}