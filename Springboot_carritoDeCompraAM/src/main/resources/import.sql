insert into categoria (nombre) values ('Confitería');
insert into categoria (nombre) values ('Abarrotes');
insert into categoria (nombre) values ('Lácteos');
insert into categoria (nombre) values ('Cereales');
insert into categoria (nombre) values ('Bebidas');



insert into productos (nombre,descripcion,categoria_id,precio) values ('chocolate trencito','chocolate con un 80% de cacao y 20% de leche',1,800);
insert into productos (nombre,descripcion,categoria_id,precio) values ('Gomitas Flippy','Gomitas sabor a limón',1,700);
insert into productos (nombre,descripcion,categoria_id,precio) values ('Arroz LaFuente','Arroz con ajo',2,1300);
insert into productos (nombre,descripcion,categoria_id,precio) values ('Pasta LaRoca','Pasta de huevo',2,1200);
insert into productos (nombre,descripcion,categoria_id,precio) values ('Leche LoncoLeche','Leche semidescremada',3,990);
insert into productos (nombre,descripcion,categoria_id,precio) values ('Yogurt Los Andes','Yogurt con pedazos de fresita',3,1200);
insert into productos (nombre,descripcion,categoria_id,precio) values ('Trix','Cereal de arroz de colores',4,1400);
insert into productos (nombre,descripcion,categoria_id,precio) values ('Quacker Avena','Hojuelas de avena con trozos de chocolate',4,800);
insert into productos (nombre,descripcion,categoria_id,precio) values ('Cocacola','Bebida de cola gasificada',5,1200);
insert into productos (nombre,descripcion,categoria_id,precio) values ('Inkacola','Bebida de fantasía sabor a chicle',5,1200);

insert into users (username,password,active) values ('admin','$2a$10$5dlCL/vkJu9jQQP/uSNMweUOJhac5QafsRgiB.l6/Ar1jhuNt8siu',1);
insert into users (username,password,active) values ('pepito','$2a$10$q0ZMme3GqO.VRTRthkiJG.81xNfIFCpg142aTCla/nYNXATvDLndi',1);
insert into users (username,password,active) values ('esteban','$2a$10$pV.bDGoCM19yKmzmmIPjmerOa6nxhg2ooudTLfiil/9ehFxeDFaQq',0);
insert into users (username,password,active) values ('susana','$2a$10$JfgTZP18SG1dN8GZC0SVeOv0lNEfrjU30k07R9ySSTc3ZukalhVva',1);
insert into users (username,password,active) values ('armando','$2a$10$oXl17SIih5Lr4hzJ7QM9pO84cACmJr2/5gVpS6JrxqfxIDORRfHMO',1);
insert into users (username,password,active) values ('alan','$2a$10$gD3A45Jf0Rrux.SXAP6pmuWpU1BpPje6GziEnOjxoPUpUd6rjUt7i',0);

insert into authorities (user_id,authority) values (1,'ROLE_ADMIN');
insert into authorities (user_id,authority) values (2,'ROLE_ADMIN');
insert into authorities (user_id,authority) values (2,'ROLE_USER');
insert into authorities (user_id,authority) values (3,'ROLE_ADMIN');
insert into authorities (user_id,authority) values (4,'ROLE_USER');
insert into authorities (user_id,authority) values (5,'ROLE_USER');
