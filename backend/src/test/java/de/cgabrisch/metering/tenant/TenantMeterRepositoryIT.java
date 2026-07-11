package de.cgabrisch.metering.tenant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.cgabrisch.metering.domain.Meter;
import de.cgabrisch.metering.rest.MockOAuth2Config;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(properties = "spring.datasource.url=jdbc:tc:postgresql:15:///")
@ContextConfiguration(classes = MockOAuth2Config.class)
@Transactional
class TenantMeterRepositoryIT {

  @Autowired private EntityManager entityManager;

  @Autowired private TenantMeterRepository tenantMeterRepository;

  @Test
  void testFindMetersByTenant() {
    Tenant tenant1 = new Tenant();
    tenant1.setName("Tenant One");
    entityManager.persist(tenant1);

    Tenant tenant2 = new Tenant();
    tenant2.setName("Tenant Two");
    entityManager.persist(tenant2);

    Meter meter1 = new Meter();
    meter1.setSerialNumber("SN1");
    meter1.setUnit("kWh");
    entityManager.persist(meter1);

    Meter meter2 = new Meter();
    meter2.setSerialNumber("SN2");
    meter2.setUnit("kWh");
    entityManager.persist(meter2);

    Meter meter3 = new Meter();
    meter3.setSerialNumber("SN3");
    meter3.setUnit("kWh");
    entityManager.persist(meter3);

    // Associate tenant1 -> meter1, meter2
    TenantMeter association1 = new TenantMeter();
    association1.setTenant(tenant1);
    association1.setMeter(meter1);
    entityManager.persist(association1);

    TenantMeter association2 = new TenantMeter();
    association2.setTenant(tenant1);
    association2.setMeter(meter2);
    entityManager.persist(association2);

    // Associate tenant2 -> meter3
    TenantMeter association3 = new TenantMeter();
    association3.setTenant(tenant2);
    association3.setMeter(meter3);
    entityManager.persist(association3);

    entityManager.flush();

    // Query tenant1 meters
    List<Meter> meters1 = tenantMeterRepository.findMetersByTenant(tenant1);
    assertEquals(2, meters1.size());
    assertTrue(meters1.stream().anyMatch(m -> m.getMeterId().equals(meter1.getMeterId())));
    assertTrue(meters1.stream().anyMatch(m -> m.getMeterId().equals(meter2.getMeterId())));

    // Query tenant2 meters by ID
    List<Meter> meters2 = tenantMeterRepository.findMetersByTenantId(tenant2.getTenantId());
    assertEquals(1, meters2.size());
    assertEquals(meter3.getMeterId(), meters2.get(0).getMeterId());
  }

  @Test
  void testMeterUniquenessConstraint() {
    Tenant tenant1 = new Tenant();
    tenant1.setName("Tenant One");
    entityManager.persist(tenant1);

    Tenant tenant2 = new Tenant();
    tenant2.setName("Tenant Two");
    entityManager.persist(tenant2);

    Meter meter = new Meter();
    meter.setSerialNumber("SN1");
    meter.setUnit("kWh");
    entityManager.persist(meter);

    // Associate tenant1 -> meter
    TenantMeter association1 = new TenantMeter();
    association1.setTenant(tenant1);
    association1.setMeter(meter);
    entityManager.persist(association1);
    entityManager.flush();

    // Trying to associate tenant2 -> meter should fail due to unique constraint on meter_id
    assertThrows(
        Exception.class,
        () -> {
          TenantMeter association2 = new TenantMeter();
          association2.setTenant(tenant2);
          association2.setMeter(meter);
          entityManager.persist(association2);
          entityManager.flush();
        });
  }
}
