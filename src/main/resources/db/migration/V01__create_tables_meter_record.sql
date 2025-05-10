drop table if exists meter cascade;

create table meter(
	meter_id bigserial primary key,
	serial_number varchar(100) not null,
	unit varchar(20) not null,
	description varchar(1000)
);

drop table if exists record cascade;

create table record(
	record_id bigserial primary key,
	meter_id bigint references meter(meter_id),
	ts timestamp with time zone not null,
	read numeric(19,5) not null
);