create table meter(
	meter_id bigserial primary key,
	serial_number varchar(100) not null,
	unit varchar(20) not null,
	description varchar(1000)
);

create table measurement(
	measurement_id bigserial primary key,
	meter_id bigint references meter(meter_id),
	instant timestamp with time zone not null,
	measured_value numeric(19,5) not null
);