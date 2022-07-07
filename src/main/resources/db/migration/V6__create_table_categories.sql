create table categories(
	id_category bigint primary key auto_increment,
	name_category varchar(120) not null,
	salon_id bigint not null,

    foreign key(salon_id) references beauty_salons(id_salon)
)