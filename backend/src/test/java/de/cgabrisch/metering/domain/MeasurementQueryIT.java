package de.cgabrisch.metering.domain;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@ActiveProfiles("integration-test")
@Transactional
class MeasurementQueryIT {
  @Autowired private EntityManager entityManager;

  @Autowired private MeasurementRepository measurementRepository;

  @Test
  void testFindMeasurementsByMeterSerialNumber() {
    Meter meterGas = new Meter();
    meterGas.setSerialNumber("987654");
    meterGas.setDescription("Gas meter");
    meterGas.setUnit("m3");
    entityManager.persist(meterGas);

    List<Measurement> measurementsGas = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      Measurement measurement = new Measurement();
      measurement.setMeasuredValue(BigDecimal.valueOf((i + 1)));
      measurement.setInstant(ZonedDateTime.now().plusDays(1));
      measurement.setMeter(meterGas);

      measurementsGas.add(measurement);
      entityManager.persist(measurement);
    }

    Meter meterElectricity = new Meter();
    meterElectricity.setSerialNumber("123456");
    meterElectricity.setDescription("Electricity meter");
    meterElectricity.setUnit("kWh");
    entityManager.persist(meterElectricity);

    List<Measurement> measurementsElectricity = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      Measurement measurement = new Measurement();
      measurement.setMeasuredValue(BigDecimal.valueOf((i + 1) * 10));
      measurement.setInstant(ZonedDateTime.now().plusHours(1));
      measurement.setMeter(meterElectricity);

      measurementsElectricity.add(measurement);
      entityManager.persist(measurement);
    }

    List<Measurement> foundMeasurementsGas =
        measurementRepository.findBySerialNumber(meterGas.getSerialNumber());
    assertEquals(measurementsGas.size(), foundMeasurementsGas.size());

    measurementsGas.forEach(meas -> assertTrue(foundMeasurementsGas.contains(meas)));

    List<Measurement> foundMeasurementsElectricity =
        measurementRepository.findBySerialNumber(meterElectricity.getSerialNumber());
    assertEquals(measurementsElectricity.size(), foundMeasurementsElectricity.size());

    measurementsElectricity.forEach(meas -> assertTrue(measurementsElectricity.contains(meas)));
  }
}
