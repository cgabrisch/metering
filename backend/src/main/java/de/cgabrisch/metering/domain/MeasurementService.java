package de.cgabrisch.metering.domain;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MeasurementService {
  private final MeasurementRepository measurementRepository;

  MeasurementService(MeasurementRepository measurementRepository) {
    this.measurementRepository = measurementRepository;
  }

  public List<Measurement> getMeasurementsForMeter(Meter meter) {
    return getMeasurementsForMeter(meter.getSerialNumber());
  }

  public List<Measurement> getMeasurementsForMeter(String meterSerialNumber) {
    return measurementRepository.findBySerialNumber(meterSerialNumber);
  }

  public Measurement addMeasurementToMeter(
      Meter meter, ZonedDateTime instant, BigDecimal measuredValue) {
    Measurement m = meter.createMeasurement(measuredValue, instant);

    m = measurementRepository.save(m);

    return m;
  }
}
