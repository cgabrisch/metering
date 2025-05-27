package de.cgabrisch.metering;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Meter {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long meterId;

  @Size(min = 1, max = 100)
  @NotNull
  private String serialNumber;

  @Size(min = 1, max = 20)
  @NotNull
  private String unit;

  @Size(max = 1000)
  private String description;

  public Long getMeterId() {
    return meterId;
  }

  public void setMeterId(Long meterId) {
    this.meterId = meterId;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
