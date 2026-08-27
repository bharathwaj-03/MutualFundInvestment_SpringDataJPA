package com.crimsonlogic.mutualfundinvestmentspringdatajpa.services.payment;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.ResourceNotFoundException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.PaymentFailedException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception.InvalidRequestException;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.PaymentRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.repository.InvestorRepository;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.interfaces.Payable;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment.BankPayment;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment.CardPayment;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment.Payment;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.model.payment.UpiPayment;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.IdGeneratorUtil;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.security.EncryptionUtil;

import java.time.LocalDateTime;

/**
 * Coordinates payment validation, processing, persistence, and receipt generation for investor transactions.
 * The implementation coordinates business rules and delegates persistence to repository dependencies.
 */

public class PaymentService
        implements I_PaymentService {


    /**
     * Repository used for persistence and database queries required by this service.
     */


    private final PaymentRepository paymentRepository;
    /**
     * Repository used for persistence and database queries required by this service.
     */
    private final InvestorRepository investorRepository;

    /**
     * Creates the service with its required dependencies.
     * Constructor injection makes required collaborators explicit and allows Spring configuration to supply them.
     *
     * @param paymentRepository paymentRepository dependency used by the service
     * @param investorRepository investorRepository dependency used by the service
     */

    public PaymentService(PaymentRepository paymentRepository,
                          InvestorRepository investorRepository) {
        this.paymentRepository = paymentRepository;
        this.investorRepository = investorRepository;
    }

    /**
     * Validates the amount and delegates payment execution to the supplied payment method.
     *
     * @param paymentMethod payment strategy used to execute the payment
     * @param amount monetary amount for the operation
     * @return true when the operation succeeds; otherwise false
     */
    @Override
    public boolean processPayment(
            Payable paymentMethod,
            double amount) {

        if (!validatePayment(amount)) {

            throw new InvalidRequestException(
                    "Payment amount must be greater than 0."
            );
        }


        if (paymentMethod == null) {

            throw new InvalidRequestException(
                    "Please select a payment method."
            );
        }


        try {

            paymentMethod.processPayment(amount);

            return true;

        } catch (Exception e) {

            throw new PaymentFailedException(
                    "Payment processing failed.",
                    e
            );
        }
    }

    /**
     * Creates and persists a payment record for an investor after a successful payment operation.
     *
     * @param investorId investor identifier
     * @param paymentMethod payment strategy used to execute the payment
     * @param amount monetary amount for the operation
     * @return result of the business operation
     */
    @Override
    public Payment savePayment(
            String investorId,
            Payable paymentMethod,
            double amount) {

        if (investorId == null ||
                investorId.trim().isEmpty()) {

            throw new InvalidRequestException(
                    "Investor ID cannot be empty."
            );
        }


        if (paymentMethod == null) {

            throw new InvalidRequestException(
                    "Payment method cannot be null."
            );
        }


        Payment payment =
                new Payment();


        payment.setPaymentId(
                IdGeneratorUtil.generatePaymentId()
        );


        payment.setInvestor(
                investorRepository.findById(investorId)
                        .orElseThrow(() -> new ResourceNotFoundException("Investor not found with id: " + investorId))
        );


        payment.setPaymentStatus(
                "SUCCESS"
        );


        payment.setPaymentDate(
                LocalDateTime.now()
        );

        if (paymentMethod instanceof UpiPayment) {

            UpiPayment upiPayment =
                    (UpiPayment) paymentMethod;


            payment.setPaymentMethod(
                    "UPI"
            );


            payment.setUpiId(
                    EncryptionUtil.encrypt(
                            upiPayment.getUpiId()
                    )
            );
        }

        else if (paymentMethod instanceof CardPayment) {

            CardPayment cardPayment =
                    (CardPayment) paymentMethod;


            payment.setPaymentMethod(
                    "CARD"
            );


            payment.setCardNumber(
                    EncryptionUtil.encrypt(
                            cardPayment.getCardNumber()
                    )
            );

            payment.setCardHolderName(
                    cardPayment.getCardHolderName()
            );
        }

        else if (paymentMethod instanceof BankPayment) {

            BankPayment bankPayment =
                    (BankPayment) paymentMethod;


            payment.setPaymentMethod(
                    "BANK"
            );


            payment.setAccountNumber(
                    EncryptionUtil.encrypt(
                            bankPayment.getAccountNumber()
                    )
            );

            payment.setBankName(
                    bankPayment.getBankName()
            );
        }


        else {

            throw new InvalidRequestException(
                    "Unsupported payment method."
            );
        }

        try {

            paymentRepository.save(payment);

            return payment;

        } catch (Exception e) {

            e.printStackTrace();

            throw new IllegalStateException(
                    "Unable to save payment details.",
                    e
            );
        }
    }

    /**
     * Retrieves a payment by its unique payment ID.
     *
     * @param paymentId payment identifier
     * @return result of the business operation
     */
    @Override
    public Payment getPaymentById(
            String paymentId) {

        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found with id: " + paymentId
                ));
    }

    /**
     * Checks whether a payment amount satisfies the service payment rules.
     *
     * @param amount monetary amount for the operation
     * @return true when the operation succeeds; otherwise false
     */
    @Override
    public boolean validatePayment(
            double amount) {

        return amount > 0;
    }

    /**
     * Generates receipt output for a completed payment.
     *
     * @param amount monetary amount for the operation
     * @param paymentType payment method type
     */
    @Override
    public void generateReceipt(
            double amount,
            String paymentType) {

        System.out.println(
                "\n===== PAYMENT RECEIPT ====="
        );

        System.out.println(
                "Payment Mode : "
                        + paymentType
        );

        System.out.println(
                "Amount : ₹"
                        + String.format(
                        "%.2f",
                        amount
                )
        );

        System.out.println(
                "Status : SUCCESS"
        );
    }
}