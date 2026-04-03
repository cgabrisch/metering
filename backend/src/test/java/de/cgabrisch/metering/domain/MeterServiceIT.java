package de.cgabrisch.metering.domain;

import static org.assertj.core.api.Assertions.*;

import de.cgabrisch.metering.rest.MockOAuth2Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(properties = "spring.datasource.url=jdbc:tc:postgresql:15:///")
@ContextConfiguration(classes = MockOAuth2Config.class)
class MeterServiceIT {
  @Autowired private RandomSerialNumberProvider randomSerialNumberProvider;
  @Autowired private MeterService meterService;

  private String serialNumber;

  @BeforeEach
  void setUp() {
    serialNumber = randomSerialNumberProvider.nextSerialNumber();
  }

  @Test
  void convertFindsMeterWhenSerialNumberIsKnown() {
    meterService.createMeter(serialNumber, "kWh", "sdafag");
    Meter meter = meterService.convert(serialNumber);
    assertThat(meter.getSerialNumber()).isEqualTo(serialNumber);
  }

  @Test
  void convertThrowsWhenSerialNumberUnknown() {
    assertThatThrownBy(() -> meterService.convert(serialNumber))
        .isInstanceOf(NoSuchMeterException.class)
        .message()
        .contains(serialNumber);
  }
}
