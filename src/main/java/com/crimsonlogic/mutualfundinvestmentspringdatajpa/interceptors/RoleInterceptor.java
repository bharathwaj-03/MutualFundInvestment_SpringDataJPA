package com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .interceptors;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;


public class RoleInterceptor
        implements HandlerInterceptor {


    public RoleInterceptor() {
    }


    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler)
            throws Exception {


        // =========================================================
        // GET APPLICATION-RELATIVE PATH
        // =========================================================

        String requestUri =
                request.getRequestURI();


        String contextPath =
                request.getContextPath();


        String path =
                requestUri;


        if (contextPath != null &&
                !contextPath.isEmpty() &&
                requestUri.startsWith(
                        contextPath
                )) {

            path =
                    requestUri.substring(
                            contextPath.length()
                    );
        }


        // =========================================================
        // PUBLIC ROUTES
        //
        // These routes should never require login.
        //
        // Even though spring-servlet.xml currently does not map
        // RoleInterceptor to /api/auth/**, keeping this here makes
        // the interceptor safer if mappings change later.
        // =========================================================

        if (path.startsWith(
                "/api/auth/")) {

            return true;
        }


        // =========================================================
        // GET EXISTING SESSION
        //
        // false means:
        // DO NOT create a new session just for checking login.
        // =========================================================

        HttpSession session =
                request.getSession(
                        false
                );


        // =========================================================
        // USER NOT LOGGED IN
        // =========================================================

        if (session == null) {

            sendErrorResponse(
                    response,
                    HttpServletResponse
                            .SC_UNAUTHORIZED,
                    "Unauthorized",
                    "Please login before accessing this resource.",
                    path
            );


            return false;
        }


        Object roleAttribute =
                session.getAttribute(
                        "ROLE"
                );


        if (roleAttribute == null) {

            sendErrorResponse(
                    response,
                    HttpServletResponse
                            .SC_UNAUTHORIZED,
                    "Unauthorized",
                    "Please login before accessing this resource.",
                    path
            );


            return false;
        }


        String role =
                roleAttribute
                        .toString()
                        .trim();


        // =========================================================
        // ADMIN-ONLY ROUTES
        //
        // Examples:
        //
        // /api/admin/dashboard
        // /api/admin/funds
        // /api/admin/investments
        // /api/admin/sip
        // /api/admin/fund-category-performance
        // =========================================================

        if (isAdminRoute(
                path
        )) {

            if (!"ADMIN"
                    .equalsIgnoreCase(
                            role
                    )) {

                sendErrorResponse(
                        response,
                        HttpServletResponse
                                .SC_FORBIDDEN,
                        "Forbidden",
                        "Access denied. Admin access only.",
                        path
                );


                return false;
            }


            return true;
        }


        // =========================================================
        // INVESTOR-ONLY ROUTES
        //
        // Supports BOTH your newer routes and older routes.
        //
        // NEW:
        // /api/investor/**
        //
        // EXISTING / LEGACY:
        // /api/investors/**
        // /api/investments/**
        // /api/sips/**
        // /api/portfolios/**
        // /api/redemptions/**
        // =========================================================

        if (isInvestorRoute(
                path
        )) {

            if (!"INVESTOR"
                    .equalsIgnoreCase(
                            role
                    )) {

                sendErrorResponse(
                        response,
                        HttpServletResponse
                                .SC_FORBIDDEN,
                        "Forbidden",
                        "Access denied. Investor access only.",
                        path
                );


                return false;
            }


            return true;
        }


        // =========================================================
        // ROUTE HAS NO ROLE RESTRICTION
        //
        // Example:
        // /api/funds/**
        //
        // If interceptor happens to receive such a route,
        // allow it.
        // =========================================================

        return true;
    }


    // =============================================================
    // ADMIN ROUTE CHECK
    // =============================================================

    private boolean isAdminRoute(
            String path) {


        return path.equals(
                "/api/admin"
        )
                ||
                path.startsWith(
                        "/api/admin/"
                );
    }


    // =============================================================
    // INVESTOR ROUTE CHECK
    // =============================================================

    private boolean isInvestorRoute(
            String path) {


        // =========================================================
        // NEW INVESTOR ROUTES
        // =========================================================

        if (path.equals(
                "/api/investor"
        )
                ||
                path.startsWith(
                        "/api/investor/"
                )) {

            return true;
        }


        // =========================================================
        // INVESTOR PROFILE
        //
        // /api/investors/INV001
        // =========================================================

        if (path.equals(
                "/api/investors"
        )
                ||
                path.startsWith(
                        "/api/investors/"
                )) {

            return true;
        }


        // =========================================================
        // LEGACY INVESTMENT ROUTES
        // =========================================================

        if (path.equals(
                "/api/investments"
        )
                ||
                path.startsWith(
                        "/api/investments/"
                )) {

            return true;
        }


        // =========================================================
        // LEGACY SIP ROUTES
        // =========================================================

        if (path.equals(
                "/api/sips"
        )
                ||
                path.startsWith(
                        "/api/sips/"
                )) {

            return true;
        }


        // =========================================================
        // PORTFOLIO ROUTES
        // =========================================================

        if (path.equals(
                "/api/portfolios"
        )
                ||
                path.startsWith(
                        "/api/portfolios/"
                )) {

            return true;
        }


        // =========================================================
        // REDEMPTION ROUTES
        // =========================================================

        if (path.equals(
                "/api/redemptions"
        )
                ||
                path.startsWith(
                        "/api/redemptions/"
                )) {

            return true;
        }


        return false;
    }


    // =============================================================
    // JSON ERROR RESPONSE
    // =============================================================

    private void sendErrorResponse(
            HttpServletResponse response,
            int status,
            String error,
            String message,
            String path)
            throws Exception {


        response.setStatus(
                status
        );


        /*
         * Keep exactly application/json.
         *
         * Your RoleInterceptorTest checks:
         *
         * assertEquals(
         *     "application/json",
         *     response.getContentType()
         * );
         *
         * Calling setCharacterEncoding("UTF-8") can make
         * MockHttpServletResponse return:
         *
         * application/json;charset=UTF-8
         *
         * which causes that test to fail.
         */
        response.setContentType(
                "application/json"
        );


        String json =
                "{"
                        +
                        "\"timestamp\":\""
                        +
                        LocalDateTime.now()
                        +
                        "\","
                        +
                        "\"status\":"
                        +
                        status
                        +
                        ","
                        +
                        "\"error\":\""
                        +
                        escapeJson(
                                error
                        )
                        +
                        "\","
                        +
                        "\"message\":\""
                        +
                        escapeJson(
                                message
                        )
                        +
                        "\","
                        +
                        "\"path\":\""
                        +
                        escapeJson(
                                path
                        )
                        +
                        "\""
                        +
                        "}";


        response
                .getWriter()
                .write(
                        json
                );


        response
                .getWriter()
                .flush();
    }


    // =============================================================
    // SIMPLE JSON ESCAPING
    // =============================================================

    private String escapeJson(
            String value) {


        if (value == null) {

            return "";
        }


        return value
                .replace(
                        "\\",
                        "\\\\"
                )
                .replace(
                        "\"",
                        "\\\""
                );
    }
}