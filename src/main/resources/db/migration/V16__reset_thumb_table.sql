alter table thumb drop type;
alter table thumb rename to thumbup;
alter table thumbup change type_id questionid bigint(20) not null;