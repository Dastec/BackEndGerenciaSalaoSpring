create table services(
	id_service bigint primary key auto_increment,
	name_service varchar(120) not null,
	price decimal(10,2),
	id_category bigint,
	salon_id bigint not null,

    foreign key(salon_id) references beauty_salons(id_salon),
	foreign key (id_category) references categories(id_category)
)