package de.cgabrisch.metering.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MeterRepository extends JpaRepository<Meter, Long> {
  Optional<Meter> findBySerialNumber(String serialNumber);
}
