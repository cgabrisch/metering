package de.cgabrisch.metering;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MeterController {
  private final List<Meter> meters = new LinkedList<>();
  private final List<Record> records = new LinkedList<>();

  MeterController() {
    var meter1 = new Meter();
    meter1.setId(4711L);
    meter1.setDescription("My description");
    meter1.setSerialNumber("SN-4711");
    meter1.setUnit("kWh");
    meters.add(meter1);
    var meter2 = new Meter();
    meter2.setId(4712L);
    meter2.setDescription("Description 4712");
    meter2.setSerialNumber("SN-4712");
    meter2.setUnit("m3");
    meters.add(meter2);

    var record1 = new Record();
    record1.setId(815L);
    record1.setMeter(meter1);
    record1.setRead(12.34);
    record1.setTimestamp(LocalDateTime.now().minusHours(2));
    records.add(record1);

    var record2 = new Record();
    record2.setId(816L);
    record2.setMeter(meter1);
    record2.setRead(13.45);
    record2.setTimestamp(LocalDateTime.now().minusHours(1));
    records.add(record2);
  }

  @QueryMapping
  public List<Meter> meters() {
    return new LinkedList<>(meters);
  }

  @QueryMapping
  public List<Record> records(@Argument String serialNumber) {
    return records.stream()
        .filter(r -> r.getMeter().getSerialNumber().equals(serialNumber))
        .toList();
  }
}
