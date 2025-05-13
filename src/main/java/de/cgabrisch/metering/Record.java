package de.cgabrisch.metering;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
public class Record {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long recordId;

  @ManyToOne
  @JoinColumn(name = "meter_id")
  private Meter meter;

  @Column(name = "ts")
  private ZonedDateTime timestamp;

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

  public ZonedDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(ZonedDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public BigDecimal getRead() {
    return read;
  }

  public void setRead(BigDecimal read) {
    this.read = read;
  }
}
