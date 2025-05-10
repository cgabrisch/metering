package de.cgabrisch.metering;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

@Controller
public class MeterController {
  private long lastMeterId = 0L;
  private final List<Meter> meters = new LinkedList<>();

  private long lastRecordId = 0L;
  private final List<Record> records = new LinkedList<>();

  MeterController() {
    var meter1 = new Meter();
    meter1.setMeterId(++lastMeterId);
    meter1.setDescription("My description");
    meter1.setSerialNumber("SN-4711");
    meter1.setUnit("kWh");
    meters.add(meter1);
    var meter2 = new Meter();
    meter2.setMeterId(++lastMeterId);
    meter2.setDescription("Description 4712");
    meter2.setSerialNumber("SN-4712");
    meter2.setUnit("m3");
    meters.add(meter2);

    var record1 = new Record();
    record1.setRecordId(++lastRecordId);
    record1.setMeter(meter1);
    record1.setRead(BigDecimal.valueOf(12.34));
    record1.setTimestamp(ZonedDateTime.now().minusHours(2));
    records.add(record1);

    var record2 = new Record();
    record2.setRecordId(++lastRecordId);
    record2.setMeter(meter1);
    record2.setRead(BigDecimal.valueOf(13.45));
    record2.setTimestamp(ZonedDateTime.now().minusHours(1));
    records.add(record2);
  }

  @QueryMapping
  public List<Meter> meters() {
    return new LinkedList<>(meters);
  }

  @MutationMapping
  public Meter createMeter(
      @Argument String serialNumber, @Argument String unit, @Argument String description) {
    Meter meter = new Meter();
    meter.setMeterId(++lastMeterId);
    meter.setSerialNumber(serialNumber);
    meter.setUnit(unit);
    meter.setDescription(description);

    meters.add(meter);
    return meter;
  }

  @QueryMapping
  public List<Record> records(@Argument String serialNumber) {
    return records.stream()
        .filter(r -> r.getMeter().getSerialNumber().equals(serialNumber))
        .toList();
  }

  @MutationMapping
  public Record addRecord(
      @Argument String serialNumber, @Argument ZonedDateTime timestamp, @Argument BigDecimal read) {
    Meter meter =
        meters.stream()
            .filter(m -> m.getSerialNumber().equals(serialNumber))
            .findAny()
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        String.format("No meter with serial number '%s'", serialNumber)));
    Record r = new Record();
    r.setRecordId(++lastRecordId);
    r.setMeter(meter);
    r.setTimestamp(timestamp);
    r.setRead(read);

    records.add(r);

    return r;
  }
}
