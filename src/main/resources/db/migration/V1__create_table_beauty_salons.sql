create table beauty_salons(
	id_salon bigint primary key auto_increment,
	name varchar(30) not null,
	fiscal_identification varchar(15),
	created_at date,
	status varchar(100)
)