# --- !Ups

create table location (
  id                        bigint not null,
  is_start                  boolean,
  server_date               timestamp not null,
  user_id                   varchar(100) not null,
  lat                       float not null,
  lon                       float not null,
  constraint pk_location primary key (id))
;

create sequence location_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists location;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists location_seq;

