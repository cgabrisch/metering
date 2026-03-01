package de.cgabrisch.metering;

import de.cgabrisch.metering.rest.MockOAuth2Config;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(properties = "spring.datasource.url=jdbc:tc:postgresql:15:///")
@ContextConfiguration(classes = MockOAuth2Config.class)
class MeteringApplicationTests {

  @Test
  void contextLoads() {}
}
