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
)