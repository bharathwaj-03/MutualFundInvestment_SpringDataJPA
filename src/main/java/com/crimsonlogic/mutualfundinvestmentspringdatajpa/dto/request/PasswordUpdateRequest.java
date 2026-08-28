package com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Request DTO used to validate password update requests.
 */
public class PasswordUpdateRequest {

    /**
     * New password supplied by the user.
     */
    @NotBlank(
            message = "New password is required."
    )
    @Pattern(
            regexp =
                    "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$",
            message =
                    "Password must contain at least 6 characters, "
                            + "one uppercase letter, one lowercase letter, "
                            + "one digit and one special character."
    )
    private String newPassword;


    public PasswordUpdateRequest() {
    }


    public String getNewPassword() {
        return newPassword;
    }


    public void setNewPassword(
            String newPassword) {

        this.newPassword =
                newPassword;
    }
}