
--insert into `client`(`username`) values ('client0');
--insert into `client`(`username`) values ('client1');
insert into `client`(`id`, `username`) values ('0', 'client0');
insert into `client`(`id`, `username`) values ('1', 'client1');

insert into `breed`(`id`, `name`) values ( '0', 'breed0');
insert into `breed`(`id`, `name`) values ( '1', 'breed1');

insert into `dog`(`id`, `name`, `breed_id`) values ('0', 'dog0', '0');
insert into `dog`(`id`, `name`, `breed_id`) values ('1', 'dog1', '0');
insert into `dog`(`id`, `name`, `breed_id`) values ('2', 'dog2', '1');

insert into `picture`(`id`, `uri`, `dog_id`) values ('0', 'uri01', '1');
insert into `picture`(`id`, `uri`, `dog_id`) values ('2', 'uri22', '2');
insert into `picture`(`id`, `uri`, `dog_id`) values ('1', 'uri11', '1');

insert into vote(`client_id`, `picture_id`, `mark`) values ('0', '1', '-1');
insert into vote(`client_id`, `picture_id`, `mark`) values ('0', '2', '1');
insert into vote(`client_id`, `picture_id`, `mark`) values ('0', '0', '1');
insert into vote(`client_id`, `picture_id`, `mark`) values ('1', '1', '1');
