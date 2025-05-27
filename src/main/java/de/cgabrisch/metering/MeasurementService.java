package de.cgabrisch.metering;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MeasurementService {
  private final MeterService meterService;
  private final MeasurementRepository measurementRepository;

  public MeasurementService(
      MeterService meterService, MeasurementRepository measurementRepository) {
    this.meterService = meterService;
    this.measurementRepository = measurementRepository;
  }

  public List<Measurement> getMeasurementsForMeter(String meterSerialNumber) {
    return measurementRepository.findBySerialNumber(meterSerialNumber);
  }

  public Measurement addMeasurementToMeter(
      String meterSerialNumber, ZonedDateTime instant, BigDecimal measuredValue) {
    Meter meter =
        meterService
            .findBySerialNumber(meterSerialNumber)
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        String.format("No meter with serial number '%s'", meterSerialNumber)));
    Measurement m = new Measurement();
    m.setMeter(meter);
    m.setInstant(instant);
    m.setMeasuredValue(measuredValue);

    m = measurementRepository.save(m);

    return m;
  }
}
