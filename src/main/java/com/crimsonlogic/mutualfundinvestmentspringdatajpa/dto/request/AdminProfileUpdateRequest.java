package com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Request DTO used to validate administrator profile updates.
 */
public class AdminProfileUpdateRequest {

    @NotBlank(message = "Admin name is required.")
    @Size(
            min = 3,
            max = 50,
            message =
                    "Admin name must contain 3 to 50 characters."
    )
    @Pattern.List({
            @Pattern(
                    regexp =
                            "^[A-Za-z]+(?: [A-Za-z]+)*$",
                    message =
                            "Admin name should contain only alphabets and spaces."
            ),
            @Pattern(
                    regexp =
                            "(?i)^(?!.*([a-z])\\1\\1).*$",
                    message =
                            "Admin name should not contain the same character "
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





    public AdminProfileUpdateRequest() {
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

        this.phoneNumber = phoneNumber;
    }



}