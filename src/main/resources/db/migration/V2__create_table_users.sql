create table users(
	id_user bigint primary key auto_increment,
	user_key varchar(120) not null unique,
	user_name varchar(120) not null,
	password varchar(120) not null,
	email varchar(255) not null,
	verified_email Bool not null,
	phone_number varchar(15) not null,
	verified_phone Bool not null,
	salon_id bigint not null,
	foreign key(salon_id) references beauty_salons(id_salon)
)