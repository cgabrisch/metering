package de.cgabrisch.metering.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTestOnTestcontainer
class MeterRepositoryIT {
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

  @Test
  void testSerialNumberUniqueness() {
    Meter meter = new Meter();
    meter.setSerialNumber("123456");
    meter.setUnit("kWh");
    meter.setDescription("Electricity meter");
    meterRepository.saveAndFlush(meter);

    Meter meterCopy = new Meter();
    meterCopy.setSerialNumber("123456");
    meterCopy.setUnit("kWh");
    meterCopy.setDescription("Another electricity meter");

    assertThrows(
        DataIntegrityViolationException.class, () -> meterRepository.saveAndFlush(meterCopy));
  }
}
