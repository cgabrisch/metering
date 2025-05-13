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
  private final MeterRepository meterRepository;
  private final RecordRepository recordRepository;

  MeterController(MeterRepository meterRepository, RecordRepository recordRepository) {
    this.meterRepository = meterRepository;
    this.recordRepository = recordRepository;
  }

  @QueryMapping
  public List<Meter> meters() {
    return meterRepository.findAll();
  }

  @MutationMapping
  public Meter createMeter(
      @Argument String serialNumber, @Argument String unit, @Argument String description) {
    Meter meter = new Meter();
    meter.setSerialNumber(serialNumber);
    meter.setUnit(unit);
    meter.setDescription(description);

    meter = meterRepository.save(meter);
    return meter;
  }

  @QueryMapping
  public List<Record> records(@Argument String serialNumber) {
    return recordRepository.findBySerialNumber(serialNumber);
  }

  @MutationMapping
  public Record addRecord(
      @Argument String serialNumber, @Argument ZonedDateTime timestamp, @Argument BigDecimal read) {
    Meter meter =
        meterRepository
            .findBySerialNumber(serialNumber)
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        String.format("No meter with serial number '%s'", serialNumber)));
    Record r = new Record();
    r.setMeter(meter);
    r.setTimestamp(timestamp);
    r.setRead(read);

    r = recordRepository.save(r);

    return r;
  }
}
