create table users(
	id_user bigint primary key auto_increment,
	user_key varchar(120) not null unique,
	user_name varchar(120) not null unique,
	password varchar(120) not null,
	email varchar(255) not null,
	verified_email Bool not null,
	phone_number varchar(15) not null,
	verified_phone Bool not null,
	person_id bigint not null,
	foreign key(person_id) references persons(id_person)
)