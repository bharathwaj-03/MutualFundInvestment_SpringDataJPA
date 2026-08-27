package com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .config;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .interceptors.RoleInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc

@ComponentScan(
        basePackages =
                "com.crimsonlogic." +
                        "mutualfundinvestmentspringdatajpa"
)

public class WebConfig
        implements WebMvcConfigurer {


    // =========================================================
    // ROLE INTERCEPTOR BEAN
    // =========================================================

    @Bean
    public RoleInterceptor
    roleInterceptor() {

        return new RoleInterceptor();
    }


    // =========================================================
    // REGISTER INTERCEPTOR
    // =========================================================

    @Override
    public void addInterceptors(
            InterceptorRegistry registry) {


        registry.addInterceptor(
                        roleInterceptor()
                )

                // ADMIN ROUTES
                .addPathPatterns(
                        "/api/admin/**"
                )

                // NEW INVESTOR ROUTES
                .addPathPatterns(
                        "/api/investor/**"
                )

                // EXISTING INVESTOR PROFILE ROUTES
                .addPathPatterns(
                        "/api/investors/**"
                )

                // LEGACY INVESTMENT ROUTES
                .addPathPatterns(
                        "/api/investments/**"
                )

                // LEGACY SIP ROUTES
                .addPathPatterns(
                        "/api/sips/**"
                )

                // PORTFOLIO ROUTES
                .addPathPatterns(
                        "/api/portfolios/**"
                )

                // REDEMPTION ROUTES
                .addPathPatterns(
                        "/api/redemptions/**"
                );
    }
}