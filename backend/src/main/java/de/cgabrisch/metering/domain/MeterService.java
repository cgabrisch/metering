package de.cgabrisch.metering.domain;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MeterService implements Converter<String, Meter> {
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

  @Override
  public Meter convert(@NonNull String serialNumber) throws NoSuchMeterException {
    return meterRepository
        .findBySerialNumber(serialNumber)
        .orElseThrow(() -> new NoSuchMeterException(serialNumber));
  }
}
