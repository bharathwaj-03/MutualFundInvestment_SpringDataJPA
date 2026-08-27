package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.investor;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.InactiveInvestorResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.InvestorProfileResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.NomineeProfileResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.AuthenticationException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.ResourceNotFoundException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.InvalidRequestException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.UserDataValidationException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.InvestorRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.NomineeRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.portfolio.Portfolio;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Nominee;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.portfolio.I_PortfolioService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.DateUtil;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.IdGeneratorUtil;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.security.EncryptionUtil;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.security.PasswordUtil;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.interfaces.UserDataValidation;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Provides investor registration, authentication, profile management, activation status, validation, and profile retrieval operations.
 * The implementation coordinates business rules and delegates persistence to repository dependencies.
 */


public class InvestorService implements I_InvestorService {

    /**
     * Repository used for persistence and database queries required by this service.
     */

    private final InvestorRepository investorRepository;
    /**
     * Repository used for persistence and database queries required by this service.
     */
    private final NomineeRepository nomineeRepository;
    /**
     * Collaborating service used to coordinate related business operations.
     */
    private I_PortfolioService portfolioService;

    /**
     * Creates the service with its required dependencies.
     * Constructor injection makes required collaborators explicit and allows Spring configuration to supply them.
     *
     * @param investorRepository investorRepository dependency used by the service
     * @param nomineeRepository nomineeRepository dependency used by the service
     * @param portfolioService portfolioService dependency used by the service
     */

    public InvestorService(
            InvestorRepository investorRepository,
            NomineeRepository nomineeRepository,
            I_PortfolioService portfolioService) {

        this.investorRepository = investorRepository;
        this.nomineeRepository = nomineeRepository;
        this.portfolioService = portfolioService;
    }

    /**
     * Validates investor names for presence, minimum length, alphabetic spacing, and repeated-character rules.
     */
    public UserDataValidation nameValidate =
            (String str) -> {

                if (str == null ||
                        str.trim().isEmpty()) {

                    throw new InvalidRequestException(
                            "Please enter a valid name."
                    );
                }

                str = str.trim();

                if (str.length() < 3) {

                    throw new InvalidRequestException(
                            "Name must contain at least 3 characters."
                    );
                }

                if (!str.matches(
                        "^[a-zA-Z]+(?: [a-zA-Z]+)*$")) {

                    throw new InvalidRequestException(
                            "Name should contain only alphabets and spaces."
                    );
                }

                String lowerName =
                        str.toLowerCase();

                for (int i = 0;
                     i <= lowerName.length() - 3;
                     i++) {

                    char first =
                            lowerName.charAt(i);

                    char second =
                            lowerName.charAt(i + 1);

                    char third =
                            lowerName.charAt(i + 2);


                    if (first == second &&
                            second == third) {

                        throw new InvalidRequestException(
                                "Name should not contain the same character "
                                        + "3 times continuously."
                        );
                    }
                }


                return str.toUpperCase();
            };


    /**
     * Validates that an investor email address follows the accepted email format.
     */
    public UserDataValidation emailValid =
            (String str) -> {

                if (str == null ||
                        str.trim().isEmpty()) {

                    throw new InvalidRequestException(
                            "Please enter a valid email address. Ex: name@company.com"
                    );
                }

                boolean isValid =
                        str.matches(
                                "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
                        );

                if (!isValid) {

                    throw new InvalidRequestException(
                            "Please enter a valid email address. Ex: name@company.com"
                    );
                }

                return str;
            };


    /**
     * Validates that an investor phone number is a valid 10-digit Indian mobile number.
     */
    public UserDataValidation phoneNum =
            (String str) -> {

                if (str == null ||
                        str.trim().isEmpty()) {

                    throw new InvalidRequestException(
                            "Please enter a valid 10-digit phone number."
                    );
                }

                boolean isValid =
                        str.matches(
                                "^[6-9]\\d{9}$"
                        );

                if (!isValid) {

                    throw new InvalidRequestException(
                            "Please enter a valid 10-digit phone number."
                    );
                }

                return str;
            };


    /**
     * Validates the PAN format before the value is encrypted and persisted.
     */
    public UserDataValidation panValidate =
            (String str) -> {

                if (str == null ||
                        str.trim().isEmpty()) {

                    throw new InvalidRequestException(
                            "Please enter a valid PAN."
                    );
                }

                boolean isValid =
                        str.matches(
                                "^[A-Z]{5}[0-9]{4}[A-Z]{1}$"
                        );

                if (!isValid) {

                    throw new InvalidRequestException(
                            "Please enter a valid PAN (5 letters in CAPS followed by 4 digits and 1 letter)."
                    );
                }

                return str.toUpperCase();
            };

    /**
     * Validates account-number input according to the configured numeric-length rule.
     */
    public UserDataValidation accountNumberValidate =
            (String str) -> {

                if (str == null ||
                        str.trim().isEmpty()) {

                    throw new InvalidRequestException(
                            "Please enter account number."
                    );
                }

                if (!str.matches("\\d{16,19}")) {

                    throw new InvalidRequestException(
                            "Account number must contain 9 to 18 digits."
                    );
                }


                return str;
            };


    /**
     * Validates password strength requirements before hashing or password-related operations.
     */
    public UserDataValidation passwordValidate =
            (String str) -> {

                if (str == null ||
                        str.trim().isEmpty()) {

                    throw new InvalidRequestException(
                            "Password cannot be empty."
                    );
                }

                boolean isValid =
                        str.matches(
                                "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{6,}$"
                        );

                if (!isValid) {

                    throw new InvalidRequestException(
                            "Password must contain at least 6 characters, one uppercase letter, one lowercase letter, one digit and one special character."
                    );
                }

                return str;
            };

    /**
     * Validates nominee names using the same name-quality rules applied to investor names.
     */
    public UserDataValidation nomineeNameValidate =
            (String str) -> {

                if (str == null ||
                        str.trim().isEmpty()) {

                    throw new InvalidRequestException(
                            "Please enter a valid name."
                    );
                }

                str = str.trim();

                if (str.length() < 3) {

                    throw new InvalidRequestException(
                            "Name must contain at least 3 characters."
                    );
                }

                if (!str.matches(
                        "^[a-zA-Z]+(?: [a-zA-Z]+)*$")) {

                    throw new InvalidRequestException(
                            "Name should contain only alphabets and spaces."
                    );
                }

                String lowerName =
                        str.toLowerCase();

                for (int i = 0;
                     i <= lowerName.length() - 3;
                     i++) {

                    char first =
                            lowerName.charAt(i);

                    char second =
                            lowerName.charAt(i + 1);

                    char third =
                            lowerName.charAt(i + 2);


                    if (first == second &&
                            second == third) {

                        throw new InvalidRequestException(
                                "Name should not contain the same character "
                                        + "3 times continuously."
                        );
                    }
                }


                return str.toUpperCase();
            };


    /**
     * Validates that nominee gender contains one of the supported values.
     */
    public UserDataValidation genderValidate =
            (String str) -> {

                if (str == null ||
                        str.trim().isEmpty()) {

                    throw new InvalidRequestException(
                            "Please select nominee gender."
                    );
                }

                if (!str.matches(
                        "(?i)^(male|female)$")) {

                    throw new InvalidRequestException(
                            "Gender must be Male or Female."
                    );
                }

                return str.toUpperCase();
            };


    /**
     * Validates that the nominee relationship has been supplied.
     */
    public UserDataValidation relationshipValidate =
            (String str) -> {

                if (str == null ||
                        str.trim().isEmpty()) {

                    throw new InvalidRequestException(
                            "Please enter relationship with nominee."
                    );
                }

                return str;
            };

    /**
     * Validates investor and nominee information and returns all field-specific validation errors found.
     *
     * @param investor investor information
     * @return map containing validation errors keyed by field name; empty when validation succeeds
     */
    @Override
    public Map<String, String> validateInvestor(
            Investor investor) {

        Map<String, String> errors =
                new LinkedHashMap<>();

        try {

            investor.setName(
                    nameValidate.validate(
                            investor.getName()
                    )
            );

        } catch (InvalidRequestException | UserDataValidationException e) {

            errors.put(
                    "name",
                    e.getMessage()
            );
        }

        try {

            passwordValidate.validate(
                    investor.getPassword()
            );

        } catch (InvalidRequestException | UserDataValidationException e) {

            errors.put(
                    "password",
                    e.getMessage()
            );
        }

        try {

            investor.setEmail(
                    emailValid.validate(
                            investor.getEmail()
                    )
            );

        } catch (InvalidRequestException | UserDataValidationException e) {

            errors.put(
                    "email",
                    e.getMessage()
            );
        }

        try {

            investor.setPhoneNumber(
                    phoneNum.validate(
                            investor.getPhoneNumber()
                    )
            );

        } catch (InvalidRequestException | UserDataValidationException e) {

            errors.put(
                    "phoneNumber",
                    e.getMessage()
            );
        }

        try {

            // Security rule: sensitive investor financial identifiers are encrypted before persistence.

            investor.setPanNumber(
                    panValidate.validate(
                            investor.getPanNumber()
                    )
            );

        } catch (InvalidRequestException | UserDataValidationException e) {

            errors.put(
                    "panNumber",
                    e.getMessage()
            );
        }

        try {

            investor.setAccountNumber(
                    accountNumberValidate.validate(
                            investor.getAccountNumber()
                    )
            );

        } catch (InvalidRequestException | UserDataValidationException e) {

            errors.put(
                    "accountNumber",
                    e.getMessage()
            );
        }

        Nominee nominee =
                investor.getNominee();

        if (nominee == null) {

            errors.put(
                    "nominee",
                    "Nominee details are required."
            );

            return errors;
        }

        try {

            nominee.setName(
                    nomineeNameValidate.validate(
                            nominee.getName()
                    )
            );

        } catch (InvalidRequestException | UserDataValidationException e) {

            errors.put(
                    "nominee.name",
                    e.getMessage()
            );
        }

        try {

            Integer age =
                    nominee.getAge();

            if (age == null) {

                throw new InvalidRequestException(
                        "Please enter nominee age."
                );
            }

            if (age <= 0) {

                throw new InvalidRequestException(
                        "Nominee age must be greater than 0."
                );
            }

        } catch (InvalidRequestException e) {

            errors.put(
                    "nominee.age",
                    e.getMessage()
            );
        }

        try {

            nominee.setGender(
                    genderValidate.validate(
                            nominee.getGender()
                    )
            );

        } catch (InvalidRequestException | UserDataValidationException e) {

            errors.put(
                    "nominee.gender",
                    e.getMessage()
            );
        }

        try {

            nominee.setRelationship(
                    relationshipValidate.validate(
                            nominee.getRelationship()
                    )
            );

        } catch (InvalidRequestException | UserDataValidationException e) {

            errors.put(
                    "nominee.relationship",
                    e.getMessage()
            );
        }

        try {

            nominee.setAccountNumber(
                    accountNumberValidate.validate(
                            nominee.getAccountNumber()
                    )
            );

        } catch (InvalidRequestException | UserDataValidationException e) {

            errors.put(
                    "nominee.accountNumber",
                    e.getMessage()
            );
        }


        return errors;
    }

    /**
     * Registers a new investor after validation, security processing, nominee persistence, and portfolio creation.
     *
     * @param investor investor information
     * @return true when the operation succeeds; otherwise false
     */
    @Override
    public boolean registerInvestor(
            Investor investor) {

        try {

            Map<String, String> errors =
                    validateInvestor(investor);

            if (!errors.isEmpty()) {

                return false;
            }

            if (investor.getUserId() == null ||
                    investor.getUserId().trim().isEmpty()) {

                investor.setUserId(
                        IdGeneratorUtil.generateInvestorId()
                );
            }

            Nominee nominee =
                    investor.getNominee();

            if (nominee.getNomineeId() == null ||
                    nominee.getNomineeId().trim().isEmpty()) {

                nominee.setNomineeId(
                        IdGeneratorUtil.generateNomineeId()
                );
            }

            investor.setUserRole("INVESTOR");

            investor.setActive(true);

            investor.setRegistrationDate(DateUtil.getCurrentDate());

            // Security rule: passwords are hashed before persistence and are never stored as plain text.

            investor.setPassword(
                    PasswordUtil.hashPassword(
                            investor.getPassword()
                    )
            );

            // Security rule: sensitive investor financial identifiers are encrypted before persistence.

            investor.setPanNumber(
                    EncryptionUtil.encrypt(
                            investor.getPanNumber()
                    )
            );

            investor.setAccountNumber(
                    EncryptionUtil.encrypt(
                            investor.getAccountNumber()
                    )
            );

            // Security rule: the nominee account number is encrypted before persistence.

            nominee.setAccountNumber(
                    EncryptionUtil.encrypt(
                            nominee.getAccountNumber()
                    )
            );

            nomineeRepository.save(nominee);

            
            investorRepository.save(investor);
            Portfolio portfolio =
                    portfolioService.createPortfolio(
                            investor.getUserId()
                    );


            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    /**
     * Authenticates an active investor using the supplied investor ID and password.
     *
     * @param investorId investor identifier
     * @param password plain password supplied for verification
     * @return result of the business operation
     */

    public Investor authenticateInvestor(
            String investorId,
            String password) {


        if (investorId == null ||
                investorId.trim().isEmpty()) {

            throw new AuthenticationException(
                    "Investor ID cannot be empty."
            );
        }


        Investor investor =
                investorRepository
                        .findById(
                                investorId
                        )
                        .orElseThrow(
                                () ->
                                        new AuthenticationException(
                                                "Invalid investor ID or password."
                                        )
                        );

        // Business rule: deactivated investor accounts cannot perform authenticated operations.

        if (!investor.isActive()) {

            throw new AuthenticationException(
                    "Admin deactivated your account. "
                            + "Please register yourself again."
            );
        }

        // Business rule: the supplied password must match the stored password hash.

        if (!PasswordUtil.verifyPassword(
                password,
                investor.getPassword())) {

            throw new AuthenticationException(
                    "Invalid investor ID or password."
            );
        }


        return investor;
    }

    /**
     * Retrieves an investor by user ID.
     *
     * @param userId user identifier
     * @return result of the business operation
     */
    @Override
    public Investor getInvestorByUserId(String userId) {

        return investorRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Investor not found with id: " + userId
                ));
    }

    /**
     * Updates editable investor and nominee profile information while preserving protected credentials.
     *
     * @param investor investor information
     * @return true when the operation succeeds; otherwise false
     */
    @Override
    public boolean updateInvestorProfile(
            Investor investor) {

        try {

            if (investor == null ||
                    investor.getUserId() == null ||
                    investor.getUserId().trim().isEmpty()) {

                return false;
            }

            investor.setName(
                    nameValidate.validate(
                            investor.getName()
                    )
            );

            investor.setEmail(
                    emailValid.validate(
                            investor.getEmail()
                    )
            );

            investor.setPhoneNumber(
                    phoneNum.validate(
                            investor.getPhoneNumber()
                    )
            );

            // Security rule: sensitive investor financial identifiers are encrypted before persistence.

            investor.setPanNumber(
                    panValidate.validate(
                            investor.getPanNumber()
                    )
            );

            investor.setAccountNumber(
                    accountNumberValidate.validate(
                            investor.getAccountNumber()
                    )
            );

            Nominee nominee =
                    investor.getNominee();

            if (nominee == null) {

                return false;
            }


            nominee.setName(
                    nomineeNameValidate.validate(
                            nominee.getName()
                    )
            );

            nominee.setGender(
                    genderValidate.validate(
                            nominee.getGender()
                    )
            );

            nominee.setRelationship(
                    relationshipValidate.validate(
                            nominee.getRelationship()
                    )
            );

            nominee.setAccountNumber(
                    accountNumberValidate.validate(
                            nominee.getAccountNumber()
                    )
            );


            if (nominee.getAge() <= 0) {

                return false;
            }

            // Security rule: sensitive investor financial identifiers are encrypted before persistence.

            investor.setPanNumber(
                    EncryptionUtil.encrypt(
                            investor.getPanNumber()
                    )
            );

            investor.setAccountNumber(
                    EncryptionUtil.encrypt(
                            investor.getAccountNumber()
                    )
            );

            // Security rule: the nominee account number is encrypted before persistence.

            nominee.setAccountNumber(
                    EncryptionUtil.encrypt(
                            nominee.getAccountNumber()
                    )
            );


            nomineeRepository.save(nominee);
            investorRepository.save(investor);


            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    /**
     * Soft-deactivates an active investor account without deleting its stored data.
     *
     * @param investorId investor identifier
     * @return true when the operation succeeds; otherwise false
     */
    @Override
    // Transaction boundary: the following business operation must complete atomically.
    @Transactional
    public boolean deactivateInvestor(
            String investorId) {


        if (investorId == null ||
                investorId.trim().isEmpty()) {

            throw new InvalidRequestException(
                    "Investor ID cannot be empty."
            );
        }


        Investor investor =
                investorRepository
                        .findById(
                                investorId
                        )
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Investor not found with ID: "
                                                        + investorId
                                        )
                        );


        // Business rule: deactivated investor accounts cannot perform authenticated operations.


        if (!investor.isActive()) {

            throw new InvalidRequestException(
                    "Investor account is already deactivated."
            );
        }


        int updatedRows =
                investorRepository
                        .deactivateInvestor(
                                investorId
                        );


        return updatedRows > 0;
    }
    /**
     * Retrieves inactive investors and converts them to response objects suitable for administration views.
     *
     * @return list of matching records or response objects
     */
    @Override
    public List<InactiveInvestorResponse>
    getInactiveInvestors() {

        List<Investor> investors =
                investorRepository
                        .findInactiveInvestorsWithNominee();


        return investors
                .stream()
                .map(
                        investor ->
                                new InactiveInvestorResponse(
                                        investor.getUserId(),
                                        investor.getName(),
                                        investor.getEmail(),
                                        investor.getPhoneNumber(),
                                        investor.getRiskProfile(),
                                        investor.isActive()
                                )
                )
                .toList();
    }
    /**
     * Reactivates an investor account that is currently inactive.
     *
     * @param investorId investor identifier
     * @return true when the operation succeeds; otherwise false
     */
    @Override
    // Transaction boundary: the following business operation must complete atomically.
    @Transactional
    public boolean activateInvestor(
            String investorId) {

        if (investorId == null ||
                investorId.trim().isEmpty()) {

            throw new InvalidRequestException(
                    "Investor ID cannot be empty."
            );
        }


        Investor investor =
                investorRepository
                        .findById(investorId)
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Investor not found with ID: "
                                                        + investorId
                                        )
                        );


        // Business rule: deactivated investor accounts cannot perform authenticated operations.


        if (investor.isActive()) {

            throw new InvalidRequestException(
                    "Investor account is already active."
            );
        }


        investor.setActive(true);

        investorRepository.save(investor);

        return true;
    }

    /**
     * Builds an investor profile response with nominee details and decrypted sensitive values while excluding the password.
     *
     * @param investorId investor identifier
     * @return result of the business operation
     */
    @Override
    public InvestorProfileResponse
    getInvestorProfile(
            String investorId) {


        if (investorId == null ||
                investorId.trim().isEmpty()) {

            throw new InvalidRequestException(
                    "Investor ID cannot be empty."
            );
        }


        Investor investor =
                investorRepository
                        .findByIdWithNominee(
                                investorId
                        )
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Investor not found with id: "
                                                        + investorId
                                        )
                        );

        String decryptedPan =
                EncryptionUtil.decrypt(
                        investor.getPanNumber()
                );


        String decryptedAccountNumber =
                EncryptionUtil.decrypt(
                        investor.getAccountNumber()
                );

        NomineeProfileResponse
                nomineeResponse = null;


        Nominee nominee =
                investor.getNominee();


        if (nominee != null) {

            String decryptedNomineeAccount =
                    EncryptionUtil.decrypt(
                            nominee.getAccountNumber()
                    );


            nomineeResponse =
                    new NomineeProfileResponse(
                            nominee.getNomineeId(),
                            nominee.getName(),
                            nominee.getAge(),
                            nominee.getGender(),
                            nominee.getRelationship(),
                            decryptedNomineeAccount
                    );
        }

        return new InvestorProfileResponse(
                investor.getUserId(),
                investor.getName(),
                investor.getEmail(),
                investor.getPhoneNumber(),
                investor.getUserRole(),
                investor.getAge(),
                decryptedPan,
                decryptedAccountNumber,
                investor.getRegistrationDate(),
                investor.getRiskProfile(),
                investor.isActive(),
                nomineeResponse
        );
    }
}