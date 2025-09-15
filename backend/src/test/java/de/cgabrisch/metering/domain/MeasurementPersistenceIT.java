package de.cgabrisch.metering.domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@ActiveProfiles("integration-test")
@Transactional
class MeasurementPersistenceIT {
  @Autowired private EntityManager entityManager;

  @Autowired private MeasurementRepository measurementRepository;

  private Measurement measurement;

  @BeforeEach
  void setUp() {
    Meter meter = new Meter();
    meter.setSerialNumber("987654");
    meter.setDescription("Gas meter");
    meter.setUnit("m3");

    entityManager.persist(meter);

    measurement = new Measurement();
    measurement.setMeasuredValue(BigDecimal.valueOf(1L));
    measurement.setInstant(ZonedDateTime.now());
    measurement.setMeter(meter);
  }

  @Test
  void doesNotPersistMeasurementWithoutMeter() {
    measurement.setMeter(null);

    assertThrows(
        DataIntegrityViolationException.class, () -> measurementRepository.save(measurement));
  }

  @Test
  void doesNotPersistMeasurementWithoutInstant() {
    measurement.setInstant(null);

    assertThrows(ConstraintViolationException.class, () -> measurementRepository.save(measurement));
  }

  @Test
  void doesNotPersistMeasurementWithoutMeasuredValue() {
    measurement.setMeasuredValue(null);

    assertThrows(ConstraintViolationException.class, () -> measurementRepository.save(measurement));
  }

  @Test
  void persistsValidMeasurement() {
    measurementRepository.save(measurement);

    assertNotNull(measurement.getMeasurementId());
  }
}
