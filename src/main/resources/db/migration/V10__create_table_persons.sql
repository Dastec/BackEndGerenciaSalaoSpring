create table persons(
	id_person bigint primary key auto_increment,
	name varchar(30) not null,
	tax_identification varchar(15),
	created_at date,
	status varchar(100)
)