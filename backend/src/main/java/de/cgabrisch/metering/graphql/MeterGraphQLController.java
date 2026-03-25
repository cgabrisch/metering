package de.cgabrisch.metering.graphql;

import de.cgabrisch.metering.domain.Measurement;
import de.cgabrisch.metering.domain.MeasurementService;
import de.cgabrisch.metering.domain.Meter;
import de.cgabrisch.metering.domain.MeterService;
import java.util.List;
import org.springframework.graphql.data.method.annotation.Argument;
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

  @QueryMapping
  List<Measurement> measurements(@Argument String serialNumber) {
    return measurementService.getMeasurementsForMeter(serialNumber);
  }
}
