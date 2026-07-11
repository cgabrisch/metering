package de.cgabrisch.metering.tenant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Tenant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long tenantId;

  private String name;

  public long getTenantId() {
    return tenantId;
  }

  public void setTenantId(long tenantId) {
    this.tenantId = tenantId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
