package de.cgabrisch.metering;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Record {
  private Long recordId;
  private Meter meter;
  private LocalDateTime timestamp;
  private BigDecimal read;

  public Long getRecordId() {
    return recordId;
  }

  public void setRecordId(Long recordId) {
    this.recordId = recordId;
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

  public BigDecimal getRead() {
    return read;
  }

  public void setRead(BigDecimal read) {
    this.read = read;
  }
}
