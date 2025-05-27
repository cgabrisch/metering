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
public class Measurement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long measurementId;

  @ManyToOne
  @JoinColumn(name = "meter_id")
  private Meter meter;

  @Column(name = "instant")
  private ZonedDateTime instant;

  private BigDecimal measuredValue;

  public Long getMeasurementId() {
    return measurementId;
  }

  public void setMeasurementId(Long measurementId) {
    this.measurementId = measurementId;
  }

  public Meter getMeter() {
    return meter;
  }

  public void setMeter(Meter meter) {
    this.meter = meter;
  }

  public ZonedDateTime getInstant() {
    return instant;
  }

  public void setInstant(ZonedDateTime instant) {
    this.instant = instant;
  }

  public BigDecimal getMeasuredValue() {
    return measuredValue;
  }

  public void setMeasuredValue(BigDecimal measuredValue) {
    this.measuredValue = measuredValue;
  }
}
