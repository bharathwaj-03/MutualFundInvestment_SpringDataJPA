package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.admin;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.request.AdminProfileUpdateRequest;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.ResourceNotFoundException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.user.Admin;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.AdminRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.DateUtil;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.security.PasswordUtil;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;


/**
 * Provides business operations for administrator authentication, profile maintenance, and default administrator initialization.
 * The implementation coordinates business rules and delegates persistence to repository dependencies.
 */


public class AdminService implements I_AdminService {

    /**
     * Repository used for persistence and database queries required by this service.
     */

    private final AdminRepository adminRepository;


    /**
     * Creates the service with its required dependencies.
     * Constructor injection makes required collaborators explicit and allows Spring configuration to supply them.
     *
     * @param adminRepository adminRepository dependency used by the service
     */


    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * Initializes the predefined administrator accounts when the service is created.
     */
    @PostConstruct
    public void initializeAdmins() {

        System.out.println(
                "\n===== INITIALIZING ADMINS ====="
        );


        createAdmin(
                "ADM001",
                "Deepak",
                "Deep@37",
                "A001",
                "8909878678"
        );


        createAdmin(
                "ADM002",
                "Rahul",
                "Rahul@37",
                "A002",
                "7865674567"
        );


        createAdmin(
                "ADM003",
                "Manager",
                "Manager@37",
                "A003",
                "6789098789"
        );


        System.out.println(
                "===== ADMIN INITIALIZATION COMPLETE =====\n"
        );
    }

    /**
     * Creates a predefined administrator only when the supplied administrator ID does not already exist.
     *
     * @param userId user identifier
     * @param name administrator name
     * @param password plain password supplied for verification
     * @param adminCode administrator code
     * @param phNo administrator phone number
     */

    private void createAdmin(
            String userId,
            String name,
            String password,
            String adminCode,String phNo) {

        Admin existingAdmin = adminRepository.findById(userId).orElse(null);


        if (existingAdmin != null) {

            System.out.println(
                    "Admin "
                            + userId
                            + " already exists. Skipping."
            );

            return;
        }

        Admin admin =
                new Admin();


        admin.setUserId(
                userId
        );


        admin.setName(
                name
        );


        admin.setPassword(
                PasswordUtil.hashPassword(
                        password
                )
        );


        admin.setEmail(
                name.toLowerCase()
                        + "@gmail.com"
        );


        admin.setPhoneNumber(
              phNo
        );


        admin.setUserRole(
                "ADMIN"
        );


        admin.setAdminCode(
                adminCode
        );


        admin.setCreatedDate(
                DateUtil.getCurrentDate()
        );

        adminRepository.save(admin);


        System.out.println(
                "Admin "
                        + userId
                        + " created successfully."
        );
    }

    /**
     * Authenticates an administrator by verifying the supplied password against the stored password hash.
     *
     * @param userId user identifier
     * @param password plain password supplied for verification
     * @return true when the operation succeeds; otherwise false
     */
    @Override
    public boolean authenticateAdmin(
            String userId,
            String password) {


        Admin admin = adminRepository.findById(userId).orElse(null);


        if (admin == null) {

            System.out.println(
                    "Admin not found."
            );

            return false;
        }

        boolean validPassword =
                PasswordUtil.verifyPassword(
                        password,
                        admin.getPassword()
                );


        if (!validPassword) {

            System.out.println(
                    "Incorrect Password."
            );

            return false;
        }


        System.out.println(
                "\nWelcome Admin "
                        + admin.getName()
        );


        return true;
    }
    /**
     * Retrieves an administrator by user ID and reports a not-found condition when the ID is absent.
     *
     * @param userId user identifier
     * @return result of the business operation
     */
    @Override
    public Admin getAdminByUserId(
            String userId) {

        return adminRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Admin not found with id: " + userId
                ));
    }

    /**
     * Updates editable administrator profile information.
     *
     * @param adminId administrator information
     * @return true when the operation succeeds; otherwise false
     */
    @Override
    @Transactional
    public Admin updateAdminProfile(
            String adminId,
            AdminProfileUpdateRequest request) {


        Admin admin =
                adminRepository
                        .findById(
                                adminId
                        )
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Admin not found with id: "
                                                        + adminId
                                        )
                        );


        /*
         * Request validation has already completed successfully.
         * Protected administrator fields are preserved.
         */
        admin.setName(
                request
                        .getName()
                        .trim()
                        .toUpperCase()
        );


        admin.setEmail(
                request.getEmail()
        );


        admin.setPhoneNumber(
                request.getPhoneNumber()
        );




        return adminRepository.save(
                admin
        );
    }

    /**
     * Updates an administrator password after hashing
     * the supplied plain-text password.
     *
     * @param adminId administrator identifier
     * @param newPassword new plain password
     */
    @Override
    public void updateAdminPassword(
            String adminId,
            String newPassword) {


        Admin admin =
                adminRepository
                        .findById(
                                adminId
                        )
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Admin not found with id: "
                                                        + adminId
                                        )
                        );


        admin.setPassword(
                PasswordUtil.hashPassword(
                        newPassword
                )
        );


        adminRepository.save(
                admin
        );
    }
}