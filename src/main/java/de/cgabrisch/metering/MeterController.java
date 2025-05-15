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
  private final RecordService recordService;

  MeterController(MeterService meterService, RecordService recordService) {
    this.meterService = meterService;
    this.recordService = recordService;
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
  public List<Record> records(@Argument String serialNumber) {
    return recordService.getRecordsForMeter(serialNumber);
  }

  @MutationMapping
  public Record addRecord(
      @Argument String serialNumber, @Argument ZonedDateTime timestamp, @Argument BigDecimal read) {
    return recordService.addRecordToMeter(serialNumber, timestamp, read);
  }
}
