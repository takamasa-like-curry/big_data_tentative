--categoriesテーブル作成
drop table if exists categories cascade;
create table categories (
	id serial primary key
 	,name character varying(255) not null
	,description text not null 
    ,name_all text not null
    ,level integer not null
    ,registered_date_time timestamp not null
    ,registered_name text not null
    ,updated_date_time timestamp not null
    ,updated_name text not null
);

--テストデータ登録
INSERT INTO categories (name,description,name_all,level,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES ('a','a is parent category','p',0,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO categories (name,description,name_all,level,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES ('b','b is child category','p/c',1,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO categories (name,description,name_all,level,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES ('c','c is grand-child category','p/c/gc',2,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO categories (name,description,name_all,level,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES ('d','d is grand-child category','p/c/gc',2,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO categories (name,description,name_all,level,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES ('e','e is grand-child category','p/c/gc',2,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO categories (name,description,name_all,level,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES ('f','f is grand-child category','p/c/gc',2,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO categories (name,description,name_all,level,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES ('g','g is grand-child category','p/c/gc',2,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO categories (name,description,name_all,level,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES ('h','h is grand-child category','p/c/gc',2,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO categories (name,description,name_all,level,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES ('i','i is grand-child category','p/c/gc',2,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO categories (name,description,name_all,level,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES ('j','j is grand-child category','p/c/gc',2,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO categories (name,description,name_all,level,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES ('k','k is grand-child category','p/c/gc',2,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO categories (name,description,name_all,level,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES ('l','l is grand-child category','p/c/gc',2,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');

--category_tree_pathsテーブル作成
drop table if exists category_tree_paths cascade;
create table category_tree_paths (
    ancestor_id integer not null
    ,descendant_id integer not null 
    ,registered_date_time timestamp not null
    ,registered_name text not null
    ,updated_date_time timestamp not null
    ,updated_name text not null
);
--テストデータ挿入
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (1,1,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (1,2,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (2,2,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (1,3,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (2,3,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (3,3,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (1,4,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (2,4,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (4,4,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (1,5,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (2,5,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (5,5,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (1,6,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (2,6,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (6,6,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (1,7,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (2,7,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (7,7,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (1,8,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (2,8,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (8,8,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (1,9,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (2,9,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (9,9,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (1,10,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (2,10,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (10,10,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (1,11,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (2,11,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (11,11,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (1,12,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (2,12,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');
INSERT INTO category_tree_paths (ancestor_id,descendant_id,registered_date_time,registered_name,updated_date_time,updated_name)
VALUES (12,12,'2000-01-01 12:00:00','tester','2000-01-01 12:00:00','tester');

