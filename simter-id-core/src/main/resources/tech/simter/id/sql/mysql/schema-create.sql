create table st_id (
  t varchar(100) comment 'ID Type',
  v bigint not null comment 'Current value',
  primary key (t)
) comment = 'ID Holder';