package com.crimsonlogic.mutualfundinvestmentspringdatajpa.exception;

import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.ErrorResponse;
import com.crimsonlogic.mutualfundinvestmentspringdatajpa.dto.response.ValidationErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {

        handler =
                new GlobalExceptionHandler();

        request =
                new MockHttpServletRequest();

        request.setRequestURI(
                "/api/test"
        );
    }


    @Test
    void shouldReturn404ForResourceNotFound() {

        ResponseEntity<ErrorResponse> response =
                handler.handleResourceNotFound(
                        new ResourceNotFoundException(
                                "Investor not found."
                        ),
                        request
                );

        assertEquals(
                HttpStatus.NOT_FOUND,
                response.getStatusCode()
        );

        assertNotNull(
                response.getBody()
        );

        assertEquals(
                404,
                response.getBody().getStatus()
        );

        assertEquals(
                "Not Found",
                response.getBody().getError()
        );

        assertEquals(
                "Investor not found.",
                response.getBody().getMessage()
        );

        assertEquals(
                "/api/test",
                response.getBody().getPath()
        );

        assertNotNull(
                response.getBody().getTimestamp()
        );
    }


    @Test
    void shouldReturn409ForDuplicateResource() {

        ResponseEntity<ErrorResponse> response =
                handler.handleDuplicateResource(
                        new DuplicateResourceException(
                                "Resource already exists."
                        ),
                        request
                );

        assertEquals(
                HttpStatus.CONFLICT,
                response.getStatusCode()
        );

        assertEquals(
                "Resource already exists.",
                response.getBody().getMessage()
        );
    }


    @Test
    void shouldReturn400ForInvalidRequest() {

        ResponseEntity<ErrorResponse> response =
                handler.handleBadRequest(
                        new InvalidRequestException(
                                "Invalid amount."
                        ),
                        request
                );

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );

        assertEquals(
                "Invalid amount.",
                response.getBody().getMessage()
        );
    }


    @Test
    void shouldReturn400ForInvalidFundType() {

        ResponseEntity<ErrorResponse> response =
                handler.handleBadRequest(
                        new InvalidFundTypeException(
                                "Invalid fund category."
                        ),
                        request
                );

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );
    }


    @Test
    void shouldReturn400ForIllegalArgument() {

        ResponseEntity<ErrorResponse> response =
                handler.handleBadRequest(
                        new IllegalArgumentException(
                                "Required value missing."
                        ),
                        request
                );

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );

        assertEquals(
                "Required value missing.",
                response.getBody().getMessage()
        );
    }


    @Test
    void shouldReturn401ForAuthenticationFailure() {

        ResponseEntity<ErrorResponse> response =
                handler.handleAuthentication(
                        new AuthenticationException(
                                "Invalid credentials."
                        ),
                        request
                );

        assertEquals(
                HttpStatus.UNAUTHORIZED,
                response.getStatusCode()
        );

        assertEquals(
                "Invalid credentials.",
                response.getBody().getMessage()
        );
    }


    @Test
    void shouldReturn400ForPaymentFailure() {

        ResponseEntity<ErrorResponse> response =
                handler.handlePaymentFailure(
                        new PaymentFailedException(
                                "Payment failed."
                        ),
                        request
                );

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );

        assertEquals(
                "Payment failed.",
                response.getBody().getMessage()
        );
    }


    @Test
    void shouldReturn409ForInsufficientUnits() {

        ResponseEntity<ErrorResponse> response =
                handler.handleInsufficientUnits(
                        new InsufficientUnitsException(
                                "Insufficient units."
                        ),
                        request
                );

        assertEquals(
                HttpStatus.CONFLICT,
                response.getStatusCode()
        );

        assertEquals(
                "Insufficient units.",
                response.getBody().getMessage()
        );
    }


    @Test
    void shouldReturn400ForMalformedJson() {

        HttpMessageNotReadableException exception =
                mock(
                        HttpMessageNotReadableException.class
                );

        ResponseEntity<ErrorResponse> response =
                handler.handleUnreadableJson(
                        exception,
                        request
                );

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );

        assertEquals(
                "Invalid or malformed JSON request body.",
                response.getBody().getMessage()
        );
    }


    @Test
    void shouldReturn400ForMissingRequestParameter()
            throws Exception {

        MissingServletRequestParameterException exception =
                new MissingServletRequestParameterException(
                        "fundId",
                        "String"
                );

        ResponseEntity<ErrorResponse> response =
                handler.handleMissingRequestParameter(
                        exception,
                        request
                );

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );

        assertEquals(
                "Required request parameter 'fundId' is missing.",
                response.getBody().getMessage()
        );
    }


    @Test
    void shouldReturnValidationErrorsForInvalidArguments()
            throws Exception {

        Object target =
                new Object();

        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(
                        target,
                        "request"
                );

        bindingResult.addError(
                new FieldError(
                        "request",
                        "email",
                        "Email is invalid."
                )
        );

        Method method =
                GlobalExceptionHandlerTest.class
                        .getDeclaredMethod(
                                "dummyMethod"
                        );

        MethodArgumentNotValidException exception =
                new MethodArgumentNotValidException(
                        new org.springframework.core.MethodParameter(
                                method,
                                -1
                        ),
                        bindingResult
                );

        ResponseEntity<ValidationErrorResponse> response =
                handler.handleValidationErrors(
                        exception,
                        request
                );

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );

        assertNotNull(
                response.getBody()
        );

        assertEquals(
                "Request validation failed.",
                response.getBody().getMessage()
        );

        assertEquals(
                "Email is invalid.",
                response.getBody()
                        .getFieldErrors()
                        .get("email")
        );
    }


    @Test
    void shouldReturn409ForDataIntegrityViolation() {

        DataIntegrityViolationException exception =
                new DataIntegrityViolationException(
                        "Duplicate key"
                );

        ResponseEntity<ErrorResponse> response =
                handler.handleDataIntegrityViolation(
                        exception,
                        request
                );

        assertEquals(
                HttpStatus.CONFLICT,
                response.getStatusCode()
        );

        assertEquals(
                "Database constraint violation. " +
                "The supplied data conflicts with " +
                "an existing record or relationship.",
                response.getBody().getMessage()
        );
    }


    @Test
    void shouldReturn500ForUnexpectedException() {

        ResponseEntity<ErrorResponse> response =
                handler.handleUnexpectedException(
                        new RuntimeException(
                                "Unexpected failure"
                        ),
                        request
                );

        assertEquals(
                HttpStatus.INTERNAL_SERVER_ERROR,
                response.getStatusCode()
        );

        assertEquals(
                "An unexpected server error occurred.",
                response.getBody().getMessage()
        );

        assertEquals(
                "/api/test",
                response.getBody().getPath()
        );
    }


    private void dummyMethod() {
    }
}
