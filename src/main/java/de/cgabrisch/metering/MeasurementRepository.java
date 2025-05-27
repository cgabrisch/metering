package de.cgabrisch.metering;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
  @Query("select m from Measurement m where m.meter.serialNumber = :serialNumber")
  List<Measurement> findBySerialNumber(String serialNumber);
}
