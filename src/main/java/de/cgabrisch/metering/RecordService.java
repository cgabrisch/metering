package de.cgabrisch.metering;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@Transactional
public class RecordService {
    private final MeterService meterService;
    private final RecordRepository recordRepository;

    public RecordService(MeterService meterService, RecordRepository recordRepository) {
        this.meterService = meterService;
        this.recordRepository = recordRepository;
    }

    public List<Record> getRecordsForMeter(String meterSerialNumber) {
        return recordRepository.findBySerialNumber(meterSerialNumber);
    }

    public Record addRecordToMeter(String meterSerialNumber, ZonedDateTime timestamp, BigDecimal read) {
        Meter meter = meterService.findBySerialNumber(meterSerialNumber)
                        .orElseThrow(
                                () ->
                                        new IllegalArgumentException(
                                                String.format("No meter with serial number '%s'", meterSerialNumber)));
        Record r = new Record();
        r.setMeter(meter);
        r.setTimestamp(timestamp);
        r.setRead(read);

        r = recordRepository.save(r);

        return r;
    }
}
