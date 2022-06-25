create table user_roles(
    user_id bigint not null,
	role varchar(50) not null,
	foreign key(user_id) references users(id_user)
)