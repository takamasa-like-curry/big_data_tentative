--itemsテーブル作成
drop table if exists items;
create table items (
    id serial primary KEY
    ,item_id integer not null 
    ,name character varying(255) not null
    ,condition integer not null
    ,category_id integer not null
    ,brand character varying(255) 
    ,price numeric not null
    ,shipping integer not null
    ,description text not null 
    ,registered_date_time timestamp not null
    ,registered_name text not null
    ,updated_date_time timestamp not null
    ,updated_name text not null
);

--テストデータ挿入
INSERT INTO items (item_id,name,condition,category_id,brand,price,shipping,description,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (1,'item1',1,100,'test company',10,1,'item1 is good','2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO items (item_id,name,condition,category_id,brand,price,shipping,description,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (2,'item2',2,200,'test company',20,1,'item2 is good','2000-01-02 12:00:00','tester','2000-01-02 12:00:00','tester');
INSERT INTO items (item_id,name,condition,category_id,brand,price,shipping,description,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (3,'item3',3,300,'test company',30,1,'item3 is good','2000-01-03 12:00:00','tester','2000-01-03 12:00:00','tester');
INSERT INTO items (item_id,name,condition,category_id,brand,price,shipping,description,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (4,'item4',1,400,'test company',40,1,'item4 is good','2000-01-04 12:00:00','tester','2000-01-04 12:00:00','tester');
INSERT INTO items (item_id,name,condition,category_id,brand,price,shipping,description,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (5,'item5',2,500,'test company',50,1,'item5 is good','2000-01-05 12:00:00','tester','2000-01-05 12:00:00','tester');
INSERT INTO items (item_id,name,condition,category_id,brand,price,shipping,description,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (6,'item6',3,600,'test company',60,1,'item6 is good','2000-01-06 12:00:00','tester','2000-01-06 12:00:00','tester');
INSERT INTO items (item_id,name,condition,category_id,brand,price,shipping,description,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (7,'item7',1,700,'test company',70,1,'item7 is good','2000-01-07 12:00:00','tester','2000-01-07 12:00:00','tester');
INSERT INTO items (item_id,name,condition,category_id,brand,price,shipping,description,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (8,'item8',2,800,'test company',80,1,'item8 is good','2000-01-08 12:00:00','tester','2000-01-08 12:00:00','tester');
INSERT INTO items (item_id,name,condition,category_id,brand,price,shipping,description,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (9,'item9',3,900,'test company',90,1,'item9 is good','2000-01-09 12:00:00','tester','2000-01-09 12:00:00','tester');
INSERT INTO items (item_id,name,condition,category_id,brand,price,shipping,description,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (10,'item10',1,1000,'test company',100,1,'item10 is good','2000-01-10 12:00:00','tester','2000-01-10 12:00:00','tester');

--itemsテーブルにindexを設定
create unique index idx_item_id on items(item_id);

