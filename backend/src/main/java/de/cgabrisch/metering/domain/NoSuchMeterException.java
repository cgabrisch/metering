package de.cgabrisch.metering.domain;

public class NoSuchMeterException extends IllegalArgumentException {
  NoSuchMeterException(String serialNumber) {
    String message = String.format("No such meter with serial number %s", serialNumber);
    super(message);
  }
}
