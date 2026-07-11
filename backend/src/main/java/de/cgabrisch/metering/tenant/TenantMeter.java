package de.cgabrisch.metering.tenant;

import de.cgabrisch.metering.domain.Meter;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "tenant_meter")
public class TenantMeter {

  @Id
  @ManyToOne
  @JoinColumn(name = "meter_id")
  private Meter meter;

  @ManyToOne
  @JoinColumn(name = "tenant_id")
  private Tenant tenant;

  public Tenant getTenant() {
    return tenant;
  }

  public void setTenant(Tenant tenant) {
    this.tenant = tenant;
  }

  public Meter getMeter() {
    return meter;
  }

  public void setMeter(Meter meter) {
    this.meter = meter;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TenantMeter that = (TenantMeter) o;
    return Objects.equals(meter, that.meter);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(meter);
  }
}
