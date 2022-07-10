create table pendency(
	id_pendency bigint primary key auto_increment,
	customer_service_id bigint not null,
	value_pendency decimal(10,2) not null,
	status varchar(50) not null,
	salon_id bigint not null,

    foreign key(salon_id) references beauty_salons(id_salon),
	foreign key(customer_service_id) references customer_services(id_customer_service)
)