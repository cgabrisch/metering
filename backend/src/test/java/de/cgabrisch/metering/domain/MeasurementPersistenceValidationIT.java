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

@DataJpaTest
@ActiveProfiles("integration-test")
class MeasurementPersistenceValidationIT {
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

    measurement = meter.createMeasurement(BigDecimal.valueOf(1L), ZonedDateTime.now());
  }

  @Test
  void doesNotPersistMeasurementWithoutMeter() {
    measurement.setMeter(null);

    assertThrows(DataIntegrityViolationException.class, this::saveMeasurement);
  }

  @Test
  void doesNotPersistMeasurementWithoutInstant() {
    measurement.setInstant(null);

    assertThrows(ConstraintViolationException.class, this::saveMeasurement);
  }

  @Test
  void doesNotPersistMeasurementWithoutMeasuredValue() {
    measurement.setMeasuredValue(null);

    assertThrows(ConstraintViolationException.class, this::saveMeasurement);
  }

  @Test
  void persistsValidMeasurement() {
    saveMeasurement();

    assertNotNull(measurement.getMeasurementId());
  }

  private void saveMeasurement() {
    measurementRepository.saveAndFlush(measurement);
  }
}
