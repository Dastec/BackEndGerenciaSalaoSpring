create table pendency(
	id_sale_service bigint primary key auto_increment,
	service_id bigint not null,
	price decimal(10,2) not null,

	foreign key(service_id) references services(id_service),
)