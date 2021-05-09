alter table users drop foreign key FK4qu1gr772nnf6ve5af002rwya;
drop table if exists article;
drop table if exists categorie;
drop table if exists fournisseurs;
drop table if exists hibernate_sequence;
drop table if exists role;
drop table if exists Transaction;
drop table if exists users;
alter table users drop foreign key FK4qu1gr772nnf6ve5af002rwya;
drop table if exists article;
drop table if exists categorie;
drop table if exists fournisseurs;
drop table if exists hibernate_sequence;
drop table if exists role;
drop table if exists Transaction;
drop table if exists users;
alter table article drop foreign key FKqnmbf0yfa804hxcw8c9gneb0v;
alter table article drop foreign key FKkf1ru7ubg78f8j73hxklippqk;
alter table Transaction drop foreign key FK5cljv16jsek4jqip0cbhebkp3;
alter table users drop foreign key FK4qu1gr772nnf6ve5af002rwya;
drop table if exists article;
drop table if exists categorie;
drop table if exists fournisseurs;
drop table if exists hibernate_sequence;
drop table if exists role;
drop table if exists Transaction;
drop table if exists users;
create table article (id integer not null, label varchar(255), stock integer, categorie_id integer, fournisseur_id integer, primary key (id));
create table categorie (id integer not null, label varchar(255), primary key (id));
create table fournisseurs (id integer not null, label varchar(255), primary key (id));
create table hibernate_sequence (next_val bigint);
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
create table role (id integer not null, label varchar(255), primary key (id));
create table Transaction (DTYPE varchar(31) not null, id integer not null, date datetime, quantite integer, article_id integer, primary key (id));
create table users (id integer not null, mail varchar(255), nom varchar(255), password varchar(255), prenom varchar(255), username varchar(255), role_id integer, primary key (id));
alter table article add constraint FKqnmbf0yfa804hxcw8c9gneb0v foreign key (categorie_id) references categorie (id);
alter table article add constraint FKkf1ru7ubg78f8j73hxklippqk foreign key (fournisseur_id) references fournisseurs (id);
alter table Transaction add constraint FK5cljv16jsek4jqip0cbhebkp3 foreign key (article_id) references article (id);
alter table users add constraint FK4qu1gr772nnf6ve5af002rwya foreign key (role_id) references role (id);
alter table article drop foreign key FKqnmbf0yfa804hxcw8c9gneb0v;
alter table article drop foreign key FKkf1ru7ubg78f8j73hxklippqk;
alter table Transaction drop foreign key FK5cljv16jsek4jqip0cbhebkp3;
alter table Transaction drop foreign key FKlwh9mb43vsm4m6k2tubh2jsig;
alter table users drop foreign key FK4qu1gr772nnf6ve5af002rwya;
drop table if exists article;
drop table if exists categorie;
drop table if exists fournisseurs;
drop table if exists hibernate_sequence;
drop table if exists role;
drop table if exists Transaction;
drop table if exists users;
create table article (id integer not null, label varchar(255), stock integer, categorie_id integer, fournisseur_id integer, primary key (id));
create table categorie (id integer not null, label varchar(255), primary key (id));
create table fournisseurs (id integer not null, label varchar(255), primary key (id));
create table hibernate_sequence (next_val bigint);
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
create table role (id integer not null, label varchar(255), primary key (id));
create table Transaction (DTYPE varchar(31) not null, id integer not null, date datetime, quantite integer, article_id integer, user_id integer, primary key (id));
create table users (id integer not null, mail varchar(255), nom varchar(255), password varchar(255), prenom varchar(255), username varchar(255), role_id integer, primary key (id));
alter table article add constraint FKqnmbf0yfa804hxcw8c9gneb0v foreign key (categorie_id) references categorie (id);
alter table article add constraint FKkf1ru7ubg78f8j73hxklippqk foreign key (fournisseur_id) references fournisseurs (id);
alter table Transaction add constraint FK5cljv16jsek4jqip0cbhebkp3 foreign key (article_id) references article (id);
alter table Transaction add constraint FKlwh9mb43vsm4m6k2tubh2jsig foreign key (user_id) references users (id);
alter table users add constraint FK4qu1gr772nnf6ve5af002rwya foreign key (role_id) references role (id);