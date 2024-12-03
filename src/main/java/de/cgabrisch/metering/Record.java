package de.cgabrisch.metering;

import java.time.LocalDateTime;

public class Record {
  private Long id;
  private Meter meter;
  private LocalDateTime timestamp;
  private Double read;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Meter getMeter() {
    return meter;
  }

  public void setMeter(Meter meter) {
    this.meter = meter;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public Double getRead() {
    return read;
  }

  public void setRead(Double read) {
    this.read = read;
  }
}
