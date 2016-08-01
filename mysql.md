# Mysql notes
- **看一看有哪几个数据库**
`show databases;`
- **创建一个数据库**
`create database 数据库名称;`
- **删除数据库**
`drop database 数据库名称;`
- **使用数据库**
`use 数据库;`
- **查询使用的数据库有多少张表**
`show tables;`
- **创建表**
`create table 表名(s_id int auto_increment primary key,s_name varchar(6) not null,s_age int not null,s_sex varchar(6) default '男',s_score int not null,s_classid int)`
- **查询表结构**
`desc 表名`
- **增加数据**
`insert into 表名(s_name,s_age,s_sex,s_score,s_classid)values("Mike",20,100,8);`
-**删除数据**
`delete from 表名 where 条件`
- **改数据**
`update 表名 set field=? where条件;`
- **查数据**
`select field,...field from 表名 where 条件(between ... and,in)[order by field (desc降序,asc升序)][field like
XXX(_代表一个，%代表0个或多个)][limit 前面几条不要n,显示几条m];--------->(*)代表全部字段`