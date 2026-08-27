package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.investor;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request.InvestorProfileUpdateRequest;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request.InvestorRegistrationRequest;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request.NomineeProfileUpdateRequest;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request.NomineeRegistrationRequest;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.InactiveInvestorResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.InvestorProfileResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.NomineeProfileResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.AuthenticationException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.InvalidRequestException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.ResourceNotFoundException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Investor;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Nominee;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.InvestorRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.NomineeRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.portfolio.I_PortfolioService;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.DateUtil;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.IdGeneratorUtil;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.security.EncryptionUtil;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.security.PasswordUtil;

import javax.transaction.Transactional;
import java.util.List;


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
     * Registers a new investor after validation, security processing, nominee persistence, and portfolio creation.
     *
     */
    @Override
    public Investor registerInvestor(
            InvestorRegistrationRequest request) {

        try {

            /*
             * Request-format validation has already been performed
             * by Bean Validation before this service method executes.
             */


            Investor investor =
                    new Investor();


            investor.setUserId(
                    IdGeneratorUtil
                            .generateInvestorId()
            );


            /*
             * Name normalization remains a service transformation.
             * It is not validation.
             */
            investor.setName(
                    request
                            .getName()
                            .trim()
                            .toUpperCase()
            );


            investor.setEmail(
                    request.getEmail()
            );


            investor.setPhoneNumber(
                    request.getPhoneNumber()
            );


//            investor.setRiskProfile(
//                    request.getRiskProfile()
//            );


            investor.setUserRole(
                    "INVESTOR"
            );


            investor.setActive(
                    true
            );


            investor.setRegistrationDate(
                    DateUtil.getCurrentDate()
            );


            /*
             * Passwords are hashed before persistence and are
             * never stored as plain text.
             */
            investor.setPassword(
                    PasswordUtil.hashPassword(
                            request.getPassword()
                    )
            );


            /*
             * Sensitive investor financial identifiers are encrypted
             * before they are stored in the database.
             */
            investor.setPanNumber(
                    EncryptionUtil.encrypt(
                            request.getPanNumber()
                    )
            );


            investor.setAccountNumber(
                    EncryptionUtil.encrypt(
                            request.getAccountNumber()
                    )
            );


            NomineeRegistrationRequest
                    nomineeRequest =
                    request.getNominee();


            Nominee nominee =
                    new Nominee();


            nominee.setNomineeId(
                    IdGeneratorUtil
                            .generateNomineeId()
            );


            nominee.setName(
                    nomineeRequest
                            .getName()
                            .trim()
                            .toUpperCase()
            );


            nominee.setAge(
                    nomineeRequest.getAge()
            );


            nominee.setGender(
                    nomineeRequest
                            .getGender()
                            .toUpperCase()
            );


            nominee.setRelationship(
                    nomineeRequest
                            .getRelationship()
            );


            /*
             * Nominee account information is encrypted before
             * persistence.
             */
            nominee.setAccountNumber(
                    EncryptionUtil.encrypt(
                            nomineeRequest
                                    .getAccountNumber()
                    )
            );


            investor.setNominee(
                    nominee
            );


            /*
             * Nominee is persisted before the investor because the
             * investor record references the nominee.
             */
            nomineeRepository.save(
                    nominee
            );


            investorRepository.save(
                    investor
            );


            portfolioService.createPortfolio(
                    investor.getUserId()
            );


            return investor;

        } catch (Exception e) {

            e.printStackTrace();

            throw new InvalidRequestException(
                    "Unable to register investor."
            );
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

     */
    @Override
    @Transactional
    public InvestorProfileResponse updateInvestorProfile(
            String investorId,
            InvestorProfileUpdateRequest request) {


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


        /*
         * Request-format validation has already been performed
         * by Bean Validation before this method executes.
         */

        investor.setName(
                request
                        .getName()
                        .trim()
                        .toUpperCase()
        );


        investor.setEmail(
                request.getEmail()
        );


        investor.setPhoneNumber(
                request.getPhoneNumber()
        );


        investor.setRiskProfile(
                request.getRiskProfile()
        );


        /*
         * PAN and account number arrive as plain request values
         * and are encrypted before persistence.
         */
        investor.setPanNumber(
                EncryptionUtil.encrypt(
                        request.getPanNumber()
                )
        );


        investor.setAccountNumber(
                EncryptionUtil.encrypt(
                        request.getAccountNumber()
                )
        );


        Nominee nominee =
                investor.getNominee();


        if (nominee == null) {

            throw new ResourceNotFoundException(
                    "Nominee details not found for investor: "
                            + investorId
            );
        }


        NomineeProfileUpdateRequest
                nomineeRequest =
                request.getNominee();


        /*
         * Existing nominee ID is preserved. Only editable
         * nominee information is changed.
         */
        nominee.setName(
                nomineeRequest
                        .getName()
                        .trim()
                        .toUpperCase()
        );


        nominee.setAge(
                nomineeRequest.getAge()
        );


        nominee.setGender(
                nomineeRequest
                        .getGender()
                        .toUpperCase()
        );


        nominee.setRelationship(
                nomineeRequest
                        .getRelationship()
        );


        nominee.setAccountNumber(
                EncryptionUtil.encrypt(
                        nomineeRequest
                                .getAccountNumber()
                )
        );


        nomineeRepository.save(
                nominee
        );


        investorRepository.save(
                investor
        );


        /*
         * Returns the normal profile DTO so encrypted database
         * values are decrypted only for the API response.
         */
        return getInvestorProfile(
                investorId
        );
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