alter table meter add constraint uc_meter_serial_number_unit unique (serial_number, unit);
