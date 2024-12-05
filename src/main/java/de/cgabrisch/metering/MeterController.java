package de.cgabrisch.metering;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MeterController {
  private long lastMeterId = 0L;
  private final List<Meter> meters = new LinkedList<>();

  private long lastRecordId = 0L;
  private final List<Record> records = new LinkedList<>();

  MeterController() {
    var meter1 = new Meter();
    meter1.setId(++lastMeterId);
    meter1.setDescription("My description");
    meter1.setSerialNumber("SN-4711");
    meter1.setUnit("kWh");
    meters.add(meter1);
    var meter2 = new Meter();
    meter2.setId(++lastMeterId);
    meter2.setDescription("Description 4712");
    meter2.setSerialNumber("SN-4712");
    meter2.setUnit("m3");
    meters.add(meter2);

    var record1 = new Record();
    record1.setId(++lastRecordId);
    record1.setMeter(meter1);
    record1.setRead(12.34);
    record1.setTimestamp(LocalDateTime.now().minusHours(2));
    records.add(record1);

    var record2 = new Record();
    record2.setId(++lastRecordId);
    record2.setMeter(meter1);
    record2.setRead(13.45);
    record2.setTimestamp(LocalDateTime.now().minusHours(1));
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
    meter.setId(++lastMeterId);
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
      @Argument String serialNumber, @Argument LocalDateTime timestamp, @Argument Double read) {
    Meter meter =
        meters.stream()
            .filter(m -> m.getSerialNumber().equals(serialNumber))
            .findAny()
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        String.format("No meter with serial number '%s'", serialNumber)));
    Record r = new Record();
    r.setId(++lastRecordId);
    r.setMeter(meter);
    r.setTimestamp(timestamp);
    r.setRead(read);

    records.add(r);

    return r;
  }
}
