package de.cgabrisch.metering.domain;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MeterService {
  private final MeterRepository meterRepository;

  MeterService(MeterRepository meterRepository) {
    this.meterRepository = meterRepository;
  }

  public List<Meter> findAll() {
    return meterRepository.findAll();
  }

  public Meter createMeter(String serialNumber, String unit, String description) {
    Meter meter = new Meter();
    meter.setSerialNumber(serialNumber);
    meter.setUnit(unit);
    meter.setDescription(description);

    meter = meterRepository.save(meter);
    return meter;
  }

  public Optional<Meter> findBySerialNumber(String serialNumber) {
    return meterRepository.findBySerialNumber(serialNumber);
  }
}
