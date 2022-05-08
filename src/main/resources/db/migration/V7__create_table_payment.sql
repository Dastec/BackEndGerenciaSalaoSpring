create table payment(
	id_payment bigint primary key auto_increment,
	form_payment_id bigint not null,
	customer_service_id bigint not null,
	valuePayment decimal(10,2) not null,
	datePayment date not null,

	foreign key(form_payment_id) references form_of_payment(id_form_payment),
	foreign key(customer_service_id) references customer_service(id_customer_service)
)