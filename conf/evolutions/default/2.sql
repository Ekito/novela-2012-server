# --- !Ups

create table admin_user (
  id                        bigint not null,
  login                     varchar(255) not null,
  password                  varchar(255) not null,
  constraint pk_admin_user primary key (id))
;


create sequence admin_user_seq;


# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists admin_user;


SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists admin_user_seq;


