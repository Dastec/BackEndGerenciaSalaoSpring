create table customer_service(
	id_customer_service bigint primary key auto_increment,
	date_customer_service date not null,
	start_time time,
	end_time time,
	total_value decimal(10,2),
	paid_value decimal(10,2),
	status_customer_service varchar(100) not null,
	customer_id bigint not null,
	observation  varchar(500),

	foreign key (customer_id) references customers(id_customer)
);

create table customer_service_service(
	customer_service_id bigint not null,
	service_id bigint not null,

	foreign key(customer_service_id) references customer_service(id_customer_service),
	foreign key(service_id) references services(id_service),
	primary key(customer_service_id, service_id)
);