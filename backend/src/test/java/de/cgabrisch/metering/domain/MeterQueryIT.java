package de.cgabrisch.metering.domain;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("integration-test")
class MeterQueryIT {
  @Autowired private EntityManager entityManager;

  @Autowired private MeterRepository meterRepository;

  @Test
  void testFindBySerialNumber() {
    Meter meter = new Meter();
    meter.setSerialNumber("123456");
    meter.setUnit("kWh");
    meter.setDescription("Electricity meter");

    entityManager.persist(meter);

    assertTrue(meterRepository.findBySerialNumber("123456").isPresent());
    assertEquals(
        meter.getMeterId(), meterRepository.findBySerialNumber("123456").get().getMeterId());

    assertTrue(meterRepository.findBySerialNumber("123457").isEmpty());
  }
}
