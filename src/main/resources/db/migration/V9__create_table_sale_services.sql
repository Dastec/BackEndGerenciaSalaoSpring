create table sale_services(
	id_sale_service bigint primary key auto_increment,
	service_id bigint not null,
	price decimal(10,2) not null,
	customer_service_id bigint,
	salon_id bigint not null,

    foreign key(salon_id) references beauty_salons(id_salon),
	foreign key(service_id) references services(id_service),
	foreign key(customer_service_id) references customer_services(id_customer_service)
);