create table tenant_meter(
	tenant_id bigint references tenant(tenant_id),
	meter_id bigint references meter(meter_id) unique,
	primary key (tenant_id, meter_id)
);
