package de.cgabrisch.metering.domain;

import java.lang.annotation.*;
import org.springframework.test.context.ActiveProfiles;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ActiveProfiles("postgresql-testcontainer")
public @interface PostgreSQLTestcontainer {}
