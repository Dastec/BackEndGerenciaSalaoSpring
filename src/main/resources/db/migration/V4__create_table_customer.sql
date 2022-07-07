create table customers(
	id_customer bigint primary key auto_increment,
	alias varchar(30) not null,
	full_name varchar(120) not null,
	cpf varchar(15),
	birth_date date not null,
	photo text,
	client_key varchar(100),
	created_at date,
	status varchar(100),
	salon_id bigint not null,

	foreign key(salon_id) references beauty_salons(id_salon)
)