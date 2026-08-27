package com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Request DTO used to validate nominee details during
 * investor profile update.
 */
public class NomineeProfileUpdateRequest {

    @NotBlank(message = "Nominee name is required.")
    @Size(
            min = 3,
            max = 50,
            message =
                    "Nominee name must contain 3 to 50 characters."
    )
    @Pattern.List({
            @Pattern(
                    regexp =
                            "^[A-Za-z]+(?: [A-Za-z]+)*$",
                    message =
                            "Nominee name should contain only alphabets and spaces."
            ),
            @Pattern(
                    regexp =
                            "(?i)^(?!.*([a-z])\\1\\1).*$",
                    message =
                            "Nominee name should not contain the same character "
                                    + "3 times continuously."
            )
    })
    private String name;


    @NotNull(message = "Nominee age is required.")
    @Min(
            value = 1,
            message =
                    "Nominee age must be greater than 0."
    )
    private Integer age;


    @NotBlank(message = "Nominee gender is required.")
    @Pattern(
            regexp = "(?i)^(male|female)$",
            message = "Gender must be Male or Female."
    )
    private String gender;


    @NotBlank(
            message =
                    "Please enter relationship with nominee."
    )
    private String relationship;


    @NotBlank(
            message =
                    "Nominee account number is required."
    )
    @Pattern(
            regexp = "^\\d{9,18}$",
            message =
                    "Account number must contain 9 to 18 digits."
    )
    private String accountNumber;


    public NomineeProfileUpdateRequest() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(
            String relationship) {

        this.relationship = relationship;
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(
            String accountNumber) {

        this.accountNumber = accountNumber;
    }
}