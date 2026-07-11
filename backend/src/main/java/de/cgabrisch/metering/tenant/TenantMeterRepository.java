package de.cgabrisch.metering.tenant;

import de.cgabrisch.metering.domain.Meter;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface TenantMeterRepository extends Repository<Meter, Long> {

  @Query("SELECT tm.meter FROM TenantMeter tm WHERE tm.tenant.tenantId = :tenantId")
  List<Meter> findMetersByTenantId(@Param("tenantId") long tenantId);

  @Query("SELECT tm.meter FROM TenantMeter tm WHERE tm.tenant = :tenant")
  List<Meter> findMetersByTenant(@Param("tenant") Tenant tenant);
}
