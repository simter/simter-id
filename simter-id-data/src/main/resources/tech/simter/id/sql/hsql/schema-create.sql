create table st_id (
  t varchar(100) primary key,
  v bigint not null
);
comment on table st_id is 'ID Holder';
comment on column st_id.t is 'ID Type';
comment on column st_id.v is 'Current value';