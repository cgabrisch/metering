package de.cgabrisch.metering.domain;

import java.lang.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@DataJpaTest(properties = "spring.datasource.url=jdbc:tc:postgresql:15:///")
@interface DataJpaTestOnTestcontainer {}
