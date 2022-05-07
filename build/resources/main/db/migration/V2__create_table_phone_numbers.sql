create table phone_numbers(
	id_phone bigint primary key auto_increment,
	type varchar(50) not null,
	ddd varchar(3) not null,
	number varchar(15) not null,
	customer_id bigint not null,
	foreign key(customer_id) references customers(id_customer)
)