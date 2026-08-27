plugins {
    id("java")
    id("war")
}

group = "com.crimsonlogic.mutualfundinvestmentspringdatajpa"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {

    // =========================================================
    // SPRING MVC
    // =========================================================

    implementation("org.springframework:spring-webmvc:5.3.39")


    // =========================================================
    // SPRING DATA JPA
    // =========================================================

    implementation("org.springframework.data:spring-data-jpa:2.7.18")


    // =========================================================
    // HIBERNATE
    // =========================================================

    implementation("org.hibernate:hibernate-core:5.6.15.Final")

    // =========================================================
    // JPA API
    // Using javax because we are using Spring 5 + Tomcat 9
    // =========================================================

    implementation("javax.persistence:javax.persistence-api:2.2")


    // =========================================================
    // SPRING ORM
    // Required for EntityManagerFactory and JPA transactions
    // =========================================================

    implementation("org.springframework:spring-orm:5.3.39")


    // =========================================================
    // SPRING JDBC
    // Required for DataSource
    // =========================================================

    implementation("org.springframework:spring-jdbc:5.3.39")


    // =========================================================
    // MYSQL
    // =========================================================

    implementation("com.mysql:mysql-connector-j:8.4.0")


    // =========================================================
    // JACKSON
    // Converts Java objects into JSON for @RestController
    // =========================================================

    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.2")


    // =========================================================
    // SERVLET API
    // Tomcat provides this at runtime
    // =========================================================

    compileOnly("javax.servlet:javax.servlet-api:4.0.1")


    // =========================================================
    // ANNOTATIONS
    // =========================================================

    implementation("javax.annotation:javax.annotation-api:1.3.2")


    // =========================================================
    // PASSWORD HASHING
    // Keep from your existing project
    // =========================================================

    implementation("org.mindrot:jbcrypt:0.4")


    // =========================================================
    // TESTING
    // =========================================================

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("org.mockito:mockito-core:5.12.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.12.0")

    testImplementation("org.springframework:spring-test:5.3.39")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    compileOnly("javax.servlet:javax.servlet-api:4.0.1")
    testImplementation("javax.servlet:javax.servlet-api:4.0.1")

    testImplementation(
            "org.hamcrest:hamcrest:2.2"
    )

    testImplementation(
            "com.jayway.jsonpath:json-path:2.9.0"
    )

    testImplementation(
            "com.h2database:h2:2.2.224"
    )

    implementation ("javax.validation:validation-api:2.0.1.Final")
    implementation ("org.hibernate.validator:hibernate-validator:6.2.5.Final")

    implementation ("org.glassfish:javax.el:3.0.1-b12")
}

tasks.test {

    useJUnitPlatform()

    testLogging {
        events(
                "passed",
                "failed",
                "skipped"
        )

        showStandardStreams = true
    }

    afterSuite(
            KotlinClosure2<TestDescriptor, TestResult, Unit>(
                    { descriptor, result ->

                        if (descriptor.parent == null) {

                            println()
                            println("========================================")
                            println("TEST SUMMARY")
                            println("========================================")
                            println("Total Tests  : ${result.testCount}")
                            println("Passed Tests : ${result.successfulTestCount}")
                            println("Failed Tests : ${result.failedTestCount}")

                            println("========================================")
                        }
                    }
            )
    )
}

