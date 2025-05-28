package de.cgabrisch.metering.rest;

import de.cgabrisch.metering.domain.Measurement;
import de.cgabrisch.metering.domain.MeasurementService;
import de.cgabrisch.metering.domain.Meter;
import de.cgabrisch.metering.domain.MeterService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/meter")
class MeterRestController {
  private final MeterService meterService;
  private final MeasurementService measurementService;

  MeterRestController(MeterService meterService, MeasurementService measurementService) {
    this.meterService = meterService;
    this.measurementService = measurementService;
  }

  @GetMapping
  List<Meter> meters() {
    return meterService.findAll();
  }

  @PostMapping("{serialNumber}")
  Meter addMeter(
          @PathVariable String serialNumber, @RequestParam String unit, @RequestParam String description) {
    return meterService.createMeter(serialNumber, unit, description);
  }

  @GetMapping("{serialNumber}/measurement")
  List<Measurement> measurements(@PathVariable String serialNumber) {
    return measurementService.getMeasurementsForMeter(serialNumber);
  }

  @PostMapping("{serialNumber}/measurement")
  Measurement addMeasurement(
      @PathVariable String serialNumber,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime instant,
      @RequestParam BigDecimal measuredValue) {
    return measurementService.addMeasurementToMeter(serialNumber, instant, measuredValue);
  }
}
