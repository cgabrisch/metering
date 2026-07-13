package de.cgabrisch.metering.tenant;

import de.cgabrisch.metering.domain.Meter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TenantMeterService {
  private final de.cgabrisch.metering.domain.MeterService domainMeterService;
  private final TenantMeterRepository tenantMeterRepository;

  TenantMeterService(
      de.cgabrisch.metering.domain.MeterService domainMeterService,
      TenantMeterRepository tenantMeterRepository) {
    this.domainMeterService = domainMeterService;
    this.tenantMeterRepository = tenantMeterRepository;
  }

  @Transactional
  public Meter createMeter(Tenant tenant, String serialNumber, String unit, String description) {
    Meter meter = this.domainMeterService.createMeter(serialNumber, unit, description);
    this.tenantMeterRepository.save(new TenantMeter(meter, tenant));

    return meter;
  }
}
