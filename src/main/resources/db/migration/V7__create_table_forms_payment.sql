create table form_of_payment(
	id_form_payment bigint primary key auto_increment,
	name_form_payment varchar(100) not null unique
)