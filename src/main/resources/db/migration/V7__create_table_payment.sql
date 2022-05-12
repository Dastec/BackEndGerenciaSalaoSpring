create table payment(
	id_payment bigint primary key auto_increment,
	form_payment_id bigint not null,
	customer_service_id bigint not null,
	value_payment decimal(10,2) not null,
	date_payment date not null,
	status varchar(30),

	foreign key(form_payment_id) references form_of_payment(id_form_payment),
	foreign key(customer_service_id) references customer_service(id_customer_service)
)