create table forms_of_payment(
	id_form_payment bigint primary key auto_increment,
	name_form_payment varchar(100) not null,
	salon_id bigint not null,

    foreign key(salon_id) references beauty_salons(id_salon)
)