package de.cgabrisch.metering;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
  @Query("select r from Record r where r.meter.serialNumber = :serialNumber")
  List<Record> findBySerialNumber(String serialNumber);
}
