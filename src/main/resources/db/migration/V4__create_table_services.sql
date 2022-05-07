create table services(
	id_service bigint primary key auto_increment,
	name_service varchar(120) not null unique,
	price decimal(10,2),
	id_category bigint,
	foreign key (id_category) references categories(id_category)
)