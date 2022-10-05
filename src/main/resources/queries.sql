--delete from dictionary_entity;


drop table santec_task;
create table santec_task (
id           bigint  primary key,
address      character varying(255),
from_quarter character varying(255),
name         character varying(255),
to_quarter   character varying(255),
status       character varying(255) default 'waiting',
lat          numeric(19,10),
lng          numeric(19,10),
from_year    integer,
to_year      integer,
is_removed   boolean,
contractor   bigint references dictionary_entity(id),
customer     bigint references dictionary_entity(id),
project      bigint references dictionary_entity(id),
director     bigint references santec_person(id),
other        bigint references santec_person(id),
supplier     bigint references santec_person(id),
owner        bigint references users(id)
);

insert into dictionary_entity(id, key, code, name) values(nextval('dic_seq'), 'developers', 'bazis', 'Базис-А');
insert into dictionary_entity(id, key, code, name) values(nextval('dic_seq'), 'developers', 'bigroup', 'BiGroup');

alter table santec_task drop column contractor;
alter table santec_task drop column customer;
alter table santec_task drop column project;

alter table santec_task add column contractor text;
alter table santec_task add column customer text;
alter table santec_task add column project text;