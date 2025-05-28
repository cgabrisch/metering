package de.cgabrisch.metering.graphql;

import de.cgabrisch.metering.Measurement;
import de.cgabrisch.metering.MeasurementService;
import de.cgabrisch.metering.Meter;
import de.cgabrisch.metering.MeterService;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
class MeterGraphQLController {
  private final MeterService meterService;
  private final MeasurementService measurementService;

  MeterGraphQLController(MeterService meterService, MeasurementService measurementService) {
    this.meterService = meterService;
    this.measurementService = measurementService;
  }

  @QueryMapping
  List<Meter> meters() {
    return meterService.findAll();
  }

  @MutationMapping
  Meter createMeter(
      @Argument String serialNumber, @Argument String unit, @Argument String description) {
    return meterService.createMeter(serialNumber, unit, description);
  }

  @QueryMapping
  List<Measurement> measurements(@Argument String serialNumber) {
    return measurementService.getMeasurementsForMeter(serialNumber);
  }

  @MutationMapping
  Measurement addMeasurement(
      @Argument String serialNumber,
      @Argument ZonedDateTime instant,
      @Argument BigDecimal measuredValue) {
    return measurementService.addMeasurementToMeter(serialNumber, instant, measuredValue);
  }
}
