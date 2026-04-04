package de.cgabrisch.metering.rest;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import de.cgabrisch.metering.domain.MeasurementService;
import de.cgabrisch.metering.domain.Meter;
import de.cgabrisch.metering.domain.MeterService;
import de.cgabrisch.metering.domain.RandomSerialNumberProvider;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(properties = "spring.datasource.url=jdbc:tc:postgresql:15:///")
@ContextConfiguration(classes = MockOAuth2Config.class)
class MeterControllerIT {
  @Autowired private RandomSerialNumberProvider randomSerialNumberProvider;

  @Autowired private WebApplicationContext context;

  @Autowired private MeterService meterService;

  @Autowired private MeasurementService measurementService;

  private MockMvc mvc;

  @BeforeEach
  void setup() {
    mvc = webAppContextSetup(context).apply(springSecurity()).build();
  }

  @Test
  void listsMeters() throws Exception {
    String serialNumberPowerMeter = randomSerialNumberProvider.nextSerialNumber();
    meterService.createMeter(serialNumberPowerMeter, "kWh", "Power Meter");
    String serialNumberGasMeter = randomSerialNumberProvider.nextSerialNumber();
    meterService.createMeter(serialNumberGasMeter, "m3", "Gas Meter");

    mvc.perform(get("/api/v1/meter").with(oauth2Login()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].serialNumber", hasItem(serialNumberPowerMeter)))
        .andExpect(jsonPath("$[*].serialNumber", hasItem(serialNumberGasMeter)));
  }

  @Test
  void addsMeter() throws Exception {
    String serialNumber = randomSerialNumberProvider.nextSerialNumber();
    mvc.perform(
            post("/api/v1/meter/{serialNumber}", serialNumber)
                .param("unit", "kW")
                .param("description", "New Meter")
                .with(oauth2Login())
                .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.serialNumber", is(serialNumber)))
        .andExpect(jsonPath("$.unit", is("kW")))
        .andExpect(jsonPath("$.description", is("New Meter")));
  }

  @Test
  void listsMeasurements() throws Exception {
    String serialNumber = randomSerialNumberProvider.nextSerialNumber();
    Meter meter = meterService.createMeter(serialNumber, "kWh", "Meter 1");
    ZonedDateTime now = ZonedDateTime.now();
    measurementService.addMeasurementToMeter(meter, now, new BigDecimal("10.5"));

    mvc.perform(get("/api/v1/meter/{serialNumber}/measurement", serialNumber).with(oauth2Login()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].measuredValue", is(10.5)));
  }

  @Test
  void addsMeasurement() throws Exception {
    String serialNumber = randomSerialNumberProvider.nextSerialNumber();
    meterService.createMeter(serialNumber, "kWh", "Meter 2");
    String instant = "2026-03-15T10:00:00+01:00";

    mvc.perform(
            post("/api/v1/meter/{serialNumber}/measurement", serialNumber)
                .param("instant", instant)
                .param("measuredValue", "123.45")
                .with(oauth2Login())
                .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.measuredValue", is(123.45)));
  }
}
