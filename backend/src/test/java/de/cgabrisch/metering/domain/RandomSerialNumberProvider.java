package de.cgabrisch.metering.domain;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class RandomSerialNumberProvider {
  public String nextSerialNumber() {
    return RandomStringUtils.insecure().nextAlphanumeric(10);
  }
}
