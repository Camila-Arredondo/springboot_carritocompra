insert into productos (nombre,descripcion,categoria,precio) values ('chocolate trencito','chocolate con un 80% de cacao y 20% de leche','Confitería',800);
insert into productos (nombre,descripcion,categoria,precio) values ('Gomitas Flippy','Gomitas sabor a limón','Confitería',700);
insert into productos (nombre,descripcion,categoria,precio) values ('Arroz LaFuente','Arroz con ajo','Abarrotes',1300);
insert into productos (nombre,descripcion,categoria,precio) values ('Pasta LaRoca','Pasta de huevo','Abarrotes',1200);
insert into productos (nombre,descripcion,categoria,precio) values ('Leche LoncoLeche','Leche semidescremada','Lácteos',990);
insert into productos (nombre,descripcion,categoria,precio) values ('Yogurt Los Andes','Yogurt con pedazos de fresita','Lácteos',1200);
insert into productos (nombre,descripcion,categoria,precio) values ('Trix','Cereal de arroz de colores','Cereales',1400);
insert into productos (nombre,descripcion,categoria,precio) values ('Quacker Avena','Hojuelas de avena con trozos de chocolate','Cereales',800);
insert into productos (nombre,descripcion,categoria,precio) values ('Cocacola','Bebida de cola gasificada','Bebidas',1200);
insert into productos (nombre,descripcion,categoria,precio) values ('Inkacola','Bebida de fantasía sabor a chicle','Bebidas',1200);

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
