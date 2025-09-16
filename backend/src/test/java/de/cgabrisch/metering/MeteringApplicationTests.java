package de.cgabrisch.metering;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.datasource.url=jdbc:tc:postgresql:15:///")
class MeteringApplicationTests {

  @Test
  void contextLoads() {}
}
