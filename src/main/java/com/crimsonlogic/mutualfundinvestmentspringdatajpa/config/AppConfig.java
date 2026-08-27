package com.crimsonlogic.mutualfundinvestmentspringdatajpa.config;



import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .repository.*;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.admin.AdminService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.holding.HoldingService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.holding.I_HoldingService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.investment.I_InvestmentService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.investment.InvestmentService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.investor.I_InvestorService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.investor.InvestorService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.mutualfund.I_MutualFundService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.mutualfund.MutualFundService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.navhistory.I_NAVHistoryService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.navhistory.NAVHistoryService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.payment.I_PaymentService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.payment.PaymentService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.portfolio.I_PortfolioService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.portfolio.PortfolioService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.redemption.I_RedemptionService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.redemption.RedemptionService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.sip.I_SIPService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.sip.SIPService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.transaction.I_TransactionService;

import com.crimsonlogic
        .mutualfundinvestmentspringdatajpa
        .services.transaction.TransactionService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import org.springframework.core.env.Environment;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import java.util.Properties;


@Configuration
@EnableTransactionManagement

@EnableJpaRepositories(
        basePackages =
                "com.crimsonlogic." +
                        "mutualfundinvestmentspringdatajpa.repository",

        entityManagerFactoryRef =
                "entityManagerFactory",

        transactionManagerRef =
                "transactionManager"
)

@PropertySource(
        "classpath:db.properties"
)

public class AppConfig {


    // =========================================================
    // DATASOURCE
    // =========================================================

    @Bean
    public DataSource dataSource(
            Environment env) {

        DriverManagerDataSource dataSource =
                new DriverManagerDataSource();


        dataSource.setDriverClassName(
                env.getRequiredProperty(
                        "db.driver"
                )
        );


        dataSource.setUrl(
                env.getRequiredProperty(
                        "db.url"
                )
        );


        dataSource.setUsername(
                env.getRequiredProperty(
                        "db.username"
                )
        );


        dataSource.setPassword(
                env.getRequiredProperty(
                        "db.password"
                )
        );


        return dataSource;
    }


    // =========================================================
    // JPA VENDOR ADAPTER
    // =========================================================

    @Bean
    public HibernateJpaVendorAdapter
    jpaVendorAdapter() {

        HibernateJpaVendorAdapter adapter =
                new HibernateJpaVendorAdapter();


        adapter.setDatabasePlatform(
                "org.hibernate.dialect.MySQL8Dialect"
        );


        adapter.setShowSql(
                true
        );


        adapter.setGenerateDdl(
                false
        );


        return adapter;
    }


    // =========================================================
    // ENTITY MANAGER FACTORY
    // =========================================================

    @Bean
    public LocalContainerEntityManagerFactoryBean
    entityManagerFactory(
            DataSource dataSource,
            HibernateJpaVendorAdapter
                    jpaVendorAdapter) {


        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();


        factory.setDataSource(
                dataSource
        );


        factory.setPackagesToScan(
                "com.crimsonlogic." +
                        "mutualfundinvestmentspringdatajpa.model"
        );


        factory.setJpaVendorAdapter(
                jpaVendorAdapter
        );


        Properties properties =
                new Properties();


        properties.setProperty(
                "hibernate.hbm2ddl.auto",
                "validate"
        );


        properties.setProperty(
                "hibernate.format_sql",
                "true"
        );


        properties.setProperty(
                "hibernate.show_sql",
                "true"
        );


        properties.setProperty(
                "hibernate.dialect",
                "org.hibernate.dialect.MySQL8Dialect"
        );


        factory.setJpaProperties(
                properties
        );


        return factory;
    }


    // =========================================================
    // TRANSACTION MANAGER
    // =========================================================

    @Bean
    public PlatformTransactionManager
    transactionManager(
            EntityManagerFactory
                    entityManagerFactory) {


        return new JpaTransactionManager(
                entityManagerFactory
        );
    }


    // =========================================================
    // ADMIN SERVICE
    // =========================================================

    @Bean
    public AdminService adminService(
            AdminRepository adminRepository) {

        return new AdminService(
                adminRepository
        );
    }


    // =========================================================
    // PORTFOLIO SERVICE
    // =========================================================

    @Bean
    public I_PortfolioService
    portfolioService(
            PortfolioRepository
                    portfolioRepository,
            InvestorRepository
                    investorRepository) {


        return new PortfolioService(
                portfolioRepository,
                investorRepository
        );
    }


    // =========================================================
    // INVESTOR SERVICE
    // =========================================================

    @Bean
    public I_InvestorService
    investorService(
            InvestorRepository
                    investorRepository,
            NomineeRepository
                    nomineeRepository,
            I_PortfolioService
                    portfolioService) {


        return new InvestorService(
                investorRepository,
                nomineeRepository,
                portfolioService
        );
    }


    // =========================================================
    // MUTUAL FUND SERVICE
    // =========================================================

    @Bean
    public I_MutualFundService
    mutualFundService(
            MutualFundRepository
                    mutualFundRepository,
            NAVHistoryRepository
                    navHistoryRepository) {


        return new MutualFundService(
                mutualFundRepository,
                navHistoryRepository
        );
    }


    // =========================================================
    // HOLDING SERVICE
    // =========================================================

    @Bean
    public I_HoldingService
    holdingService(
            HoldingRepository
                    holdingRepository) {


        return new HoldingService(
                holdingRepository
        );
    }


    // =========================================================
    // PAYMENT SERVICE
    // =========================================================

    @Bean
    public I_PaymentService
    paymentService(
            PaymentRepository
                    paymentRepository,
            InvestorRepository
                    investorRepository) {


        return new PaymentService(
                paymentRepository,
                investorRepository
        );
    }


    // =========================================================
    // TRANSACTION SERVICE
    // =========================================================

    @Bean
    public I_TransactionService
    transactionService(
            TransactionRepository
                    transactionRepository) {


        return new TransactionService(
                transactionRepository
        );
    }


    // =========================================================
    // INVESTMENT SERVICE
    // =========================================================

    @Bean
    public I_InvestmentService
    investmentService(
            InvestmentRepository
                    investmentRepository,

            I_InvestorService
                    investorService,

            I_MutualFundService
                    mutualFundService,

            I_PaymentService
                    paymentService,

            I_TransactionService
                    transactionService,

            I_HoldingService
                    holdingService,

            I_PortfolioService
                    portfolioService) {


        return new InvestmentService(
                investmentRepository,
                investorService,
                mutualFundService,
                paymentService,
                transactionService,
                holdingService,
                portfolioService
        );
    }


    // =========================================================
    // SIP SERVICE
    // =========================================================

    @Bean
    public I_SIPService
    sipService(
            SIPRepository
                    sipRepository,

            I_InvestorService
                    investorService,

            I_MutualFundService
                    mutualFundService,

            I_HoldingService
                    holdingService,

            I_PortfolioService
                    portfolioService,

            I_TransactionService
                    transactionService,

            I_PaymentService
                    paymentService) {


        return new SIPService(
                sipRepository,
                investorService,
                mutualFundService,
                holdingService,
                portfolioService,
                transactionService,
                paymentService
        );
    }


    // =========================================================
    // REDEMPTION SERVICE
    // =========================================================

    @Bean
    public I_RedemptionService
    redemptionService(
            RedemptionRepository
                    redemptionRepository,

            I_HoldingService
                    holdingService,

            I_MutualFundService
                    mutualFundService,

            I_PortfolioService
                    portfolioService,

            I_TransactionService
                    transactionService) {


        return new RedemptionService(
                redemptionRepository,
                holdingService,
                mutualFundService,
                portfolioService,
                transactionService
        );
    }


    // =========================================================
    // NAV HISTORY SERVICE
    // =========================================================

    @Bean
    public I_NAVHistoryService
    navHistoryService(
            NAVHistoryRepository
                    navHistoryRepository) {


        return new NAVHistoryService(
                navHistoryRepository
        );
    }
}
