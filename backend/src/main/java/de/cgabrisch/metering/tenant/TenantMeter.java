package de.cgabrisch.metering.tenant;

import de.cgabrisch.metering.domain.Meter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tenant_meter")
class TenantMeter {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "meter_id")
  private Meter meter;

  @ManyToOne
  @JoinColumn(name = "tenant_id")
  private Tenant tenant;

  TenantMeter() {}

  TenantMeter(Meter meter, Tenant tenant) {
    this.meter = meter;
    this.tenant = tenant;
  }

  Tenant getTenant() {
    return tenant;
  }

  void setTenant(Tenant tenant) {
    this.tenant = tenant;
  }

  Meter getMeter() {
    return meter;
  }

  void setMeter(Meter meter) {
    this.meter = meter;
  }
}
