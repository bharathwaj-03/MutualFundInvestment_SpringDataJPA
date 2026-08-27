package com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .config;

import org.springframework.web.servlet
        .support
        .AbstractAnnotationConfigDispatcherServletInitializer;


public class WebAppInitializer
        extends
        AbstractAnnotationConfigDispatcherServletInitializer {


    // =========================================================
    // ROOT APPLICATION CONTEXT
    //
    // Database
    // JPA
    // Repositories
    // Services
    // Transactions
    // =========================================================

    @Override
    protected Class<?>[]
    getRootConfigClasses() {

        return new Class[]{
                AppConfig.class
        };
    }


    // =========================================================
    // DISPATCHER SERVLET CONTEXT
    //
    // Controllers
    // REST
    // MVC
    // Interceptors
    // =========================================================

    @Override
    protected Class<?>[]
    getServletConfigClasses() {

        return new Class[]{
                WebConfig.class
        };
    }


    // =========================================================
    // DISPATCHER SERVLET MAPPING
    //
    // Same as:
    //
    // <url-pattern>/</url-pattern>
    // =========================================================

    @Override
    protected String[]
    getServletMappings() {

        return new String[]{
                "/"
        };
    }
}