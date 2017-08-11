create table id_holder (
  type  varchar(100) primary key,
  value bigint not null
);
comment on table id_holder is 'ID Holder';
comment on column id_holder.type is 'The type';
comment on column id_holder.value is 'The current value';