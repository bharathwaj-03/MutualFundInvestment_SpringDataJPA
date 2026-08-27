package com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .dto.request;

import javax.validation.constraints.*;

/**
 * Data transfer object used to receive mutual fund
 * creation and update information.
 *
 * Bean Validation annotations validate request data before
 * the controller delegates the operation to the service layer.
 */
public class FundRequest {

    /**
     * Business code used to identify the mutual fund.
     *
     * Example: EQ011.
     */
    @NotBlank(
            message =
                    "Fund code is required."
    )
    @Pattern(
            regexp =
                    "^[A-Z]{2}[0-9]{3}$",
            message =
                    "Fund code must contain 2 uppercase letters followed by 3 digits."
    )
    private String fundCode;


    /**
     * Display name of the mutual fund.
     */
    @NotBlank(
            message =
                    "Fund name is required."
    )
    @Size(
            min = 3,
            max = 100,
            message =
                    "Fund name must contain 3 to 100 characters."
    )
    @Pattern(
            regexp =
                    "^(?!\\s)(?!.*\\s$)[A-Za-z0-9 .&'-]+$",
            message =
                    "Fund name must contain valid characters and must not start or end with a space."
    )
    private String fundName;


    /**
     * Category to which the fund belongs.
     */
    @NotBlank(
            message =
                    "Fund category is required."
    )
    @Pattern(
            regexp =
                    "(?i)^(Equity Fund|Debt Fund|Hybrid Fund|EQUITY|DEBT|HYBRID)$",
            message =
                    "Fund category must be Equity Fund, Debt Fund or Hybrid Fund."
    )
    private String fundCategory;


    /**
     * Fund house or asset management company managing the fund.
     */
    @NotBlank(
            message =
                    "Fund house is required."
    )
    @Size(
            min = 2,
            max = 100,
            message =
                    "Fund house must contain 2 to 100 characters."
    )
    @Pattern(
            regexp =
                    "^(?!\\s)(?!.*\\s$)[A-Za-z0-9 .&'-]+$",
            message =
                    "Fund house must not start or end with a space."
    )
    private String fundHouse;


    /**
     * Risk classification assigned to the mutual fund.
     */
    @NotBlank(
            message =
                    "Risk level is required."
    )
    @Pattern(
            regexp =
                    "(?i)^(LOW|MODERATE|HIGH)$",
            message =
                    "Risk level must be Low, Moderate or High."
    )
    private String riskLevel;


    /**
     * Current Net Asset Value of the mutual fund.
     */
    @NotNull(
            message =
                    "NAV is required."
    )
    @Positive(
            message =
                    "NAV must be greater than 0."
    )
    private Double nav;


    /**
     * Minimum investment accepted for the fund.
     */
    @NotNull(
            message =
                    "Minimum investment is required."
    )
    @Positive(
            message =
                    "Minimum investment must be greater than 0."
    )
    private Double minimumInvestment;

    /**
     * Expected annual gain percentage used for SIP calculations.
     */
    @NotNull(
            message =
                    "SIP gain per year is required."
    )
    @DecimalMin(
            value = "1.0",
            inclusive = true,
            message =
                    "SIP gain per year must be at least 1."
    )
    private Double sipGainPerYear;


    /**
     * Expected annual gain percentage used for lump-sum calculations.
     */
    @NotNull(
            message =
                    "Lump sum gain per year is required."
    )
    @DecimalMin(
            value = "1.0",
            inclusive = true,
            message =
                    "Lump sum gain per year must be at least 1."
    )
    private Double lumpSumGainPerYear;


    public FundRequest() {
    }


    public String getFundCode() {
        return fundCode;
    }


    public void setFundCode(
            String fundCode) {

        this.fundCode =
                fundCode;
    }


    public String getFundName() {
        return fundName;
    }


    public void setFundName(
            String fundName) {

        this.fundName =
                fundName;
    }


    public String getFundCategory() {
        return fundCategory;
    }


    public void setFundCategory(
            String fundCategory) {

        this.fundCategory =
                fundCategory;
    }


    public String getFundHouse() {
        return fundHouse;
    }


    public void setFundHouse(
            String fundHouse) {

        this.fundHouse =
                fundHouse;
    }


    public String getRiskLevel() {
        return riskLevel;
    }


    public void setRiskLevel(
            String riskLevel) {

        this.riskLevel =
                riskLevel;
    }


    public Double getNav() {
        return nav;
    }


    public void setNav(
            Double nav) {

        this.nav =
                nav;
    }


    public Double getMinimumInvestment() {
        return minimumInvestment;
    }


    public void setMinimumInvestment(
            Double minimumInvestment) {

        this.minimumInvestment =
                minimumInvestment;
    }


    public Double getSipGainPerYear() {
        return sipGainPerYear;
    }


    public void setSipGainPerYear(
            Double sipGainPerYear) {

        this.sipGainPerYear =
                sipGainPerYear;
    }


    public Double getLumpSumGainPerYear() {
        return lumpSumGainPerYear;
    }


    public void setLumpSumGainPerYear(
            Double lumpSumGainPerYear) {

        this.lumpSumGainPerYear =
                lumpSumGainPerYear;
    }
}