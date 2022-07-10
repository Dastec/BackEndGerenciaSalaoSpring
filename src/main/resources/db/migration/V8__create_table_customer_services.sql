create table customer_services(
	id_customer_service bigint primary key auto_increment,
	date_customer_service date not null,
	start_time time,
	end_time time,
	total_value decimal(10,2),
	paid_value decimal(10,2),
	status_customer_service varchar(100) not null,
	customer_id bigint,
	observation  varchar(500),
	salon_id bigint not null,

    foreign key(salon_id) references beauty_salons(id_salon),
	foreign key (customer_id) references customers(id_customer)
);
