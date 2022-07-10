create table payments(
	id_payment bigint primary key auto_increment,
	form_payment_id bigint not null,
	customer_service_id bigint not null,
	value_payment decimal(10,2) not null,
	date_payment date not null,
	payment_status varchar(30),
	salon_id bigint not null,

    foreign key(salon_id) references beauty_salons(id_salon),
	foreign key(form_payment_id) references forms_of_payment(id_form_payment),
	foreign key(customer_service_id) references customer_services(id_customer_service)
)