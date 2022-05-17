create table pendency(
	id_pendency bigint primary key auto_increment,
	customer_service_id bigint not null unique,
	value_pendency decimal(10,2) not null,
	status varchar(50) not null,

	foreign key(customer_service_id) references customer_service(id_customer_service)
)