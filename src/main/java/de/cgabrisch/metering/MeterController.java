package de.cgabrisch.metering;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MeterController {
  private final MeterService meterService;
  private final MeasurementService measurementService;

  MeterController(MeterService meterService, MeasurementService measurementService) {
    this.meterService = meterService;
    this.measurementService = measurementService;
  }

  @QueryMapping
  public List<Meter> meters() {
    return meterService.findAll();
  }

  @MutationMapping
  public Meter createMeter(
      @Argument String serialNumber, @Argument String unit, @Argument String description) {
    return meterService.createMeter(serialNumber, unit, description);
  }

  @QueryMapping
  public List<Measurement> measurements(@Argument String serialNumber) {
    return measurementService.getMeasurementsForMeter(serialNumber);
  }

  @MutationMapping
  public Measurement addMeasurement(
      @Argument String serialNumber,
      @Argument ZonedDateTime instant,
      @Argument BigDecimal measuredValue) {
    return measurementService.addMeasurementToMeter(serialNumber, instant, measuredValue);
  }
}
