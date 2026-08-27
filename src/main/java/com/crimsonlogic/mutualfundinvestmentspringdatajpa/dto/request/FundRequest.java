package com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request;

/**
 * Data transfer object used to receive fund data from an API request.
 *
 * This DTO keeps HTTP payload data separate from persistence entities and service-layer models.
 */
public class FundRequest {
    /**
     * Business code used to identify the mutual fund.
     */
    private String fundCode;
    /**
     * Display name of the mutual fund.
     */
    private String fundName;
    /**
     * Category to which the mutual fund belongs.
     */
    private String fundCategory;
    /**
     * Fund house or asset management company managing the fund.
     */
    private String fundHouse;
    /**
     * Risk classification assigned to the mutual fund.
     */
    private String riskLevel;
    /**
     * Current Net Asset Value of the mutual fund.
     */
    private double nav;
    /**
     * Minimum amount permitted for investment in the fund.
     */
    private double minimumInvestment;
    /**
     * Expected annual gain percentage used for SIP calculations.
     */
    private double sipGainPerYear;
    /**
     * Expected annual gain percentage used for lump-sum calculations.
     */
    private double lumpSumGainPerYear;

    /**
     * Creates a FundRequest object. This no-argument constructor supports request/response binding and object creation.
     */
    public FundRequest() {}

    /**
     * Returns the fund code.
     * @return business code used to identify the mutual fund.
     */
    public String getFundCode() { return fundCode; }
    /**
     * Updates the fund code carried by this DTO.
     * @param fundCode business code used to identify the mutual fund.
     */
    public void setFundCode(String fundCode) { this.fundCode = fundCode; }
    /**
     * Returns the fund name.
     * @return display name of the mutual fund.
     */
    public String getFundName() { return fundName; }
    /**
     * Updates the fund name carried by this DTO.
     * @param fundName display name of the mutual fund.
     */
    public void setFundName(String fundName) { this.fundName = fundName; }
    /**
     * Returns the fund category.
     * @return category to which the mutual fund belongs.
     */
    public String getFundCategory() { return fundCategory; }
    /**
     * Updates the fund category carried by this DTO.
     * @param fundCategory category to which the mutual fund belongs.
     */
    public void setFundCategory(String fundCategory) { this.fundCategory = fundCategory; }
    /**
     * Returns the fund house.
     * @return fund house or asset management company managing the fund.
     */
    public String getFundHouse() { return fundHouse; }
    /**
     * Updates the fund house carried by this DTO.
     * @param fundHouse fund house or asset management company managing the fund.
     */
    public void setFundHouse(String fundHouse) { this.fundHouse = fundHouse; }
    /**
     * Returns the risk level.
     * @return risk classification assigned to the mutual fund.
     */
    public String getRiskLevel() { return riskLevel; }
    /**
     * Updates the risk level carried by this DTO.
     * @param riskLevel risk classification assigned to the mutual fund.
     */
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
    /**
     * Returns the nav.
     * @return current Net Asset Value of the mutual fund.
     */
    public double getNav() { return nav; }
    /**
     * Updates the nav carried by this DTO.
     * @param nav current Net Asset Value of the mutual fund.
     */
    public void setNav(double nav) { this.nav = nav; }
    /**
     * Returns the minimum investment.
     * @return minimum amount permitted for investment in the fund.
     */
    public double getMinimumInvestment() { return minimumInvestment; }
    /**
     * Updates the minimum investment carried by this DTO.
     * @param minimumInvestment minimum amount permitted for investment in the fund.
     */
    public void setMinimumInvestment(double minimumInvestment) { this.minimumInvestment = minimumInvestment; }
    /**
     * Returns the sip gain per year.
     * @return expected annual gain percentage used for SIP calculations.
     */
    public double getSipGainPerYear() { return sipGainPerYear; }
    /**
     * Updates the sip gain per year carried by this DTO.
     * @param sipGainPerYear expected annual gain percentage used for SIP calculations.
     */
    public void setSipGainPerYear(double sipGainPerYear) { this.sipGainPerYear = sipGainPerYear; }
    /**
     * Returns the lump sum gain per year.
     * @return expected annual gain percentage used for lump-sum calculations.
     */
    public double getLumpSumGainPerYear() { return lumpSumGainPerYear; }
    /**
     * Updates the lump sum gain per year carried by this DTO.
     * @param lumpSumGainPerYear expected annual gain percentage used for lump-sum calculations.
     */
    public void setLumpSumGainPerYear(double lumpSumGainPerYear) { this.lumpSumGainPerYear = lumpSumGainPerYear; }
}
