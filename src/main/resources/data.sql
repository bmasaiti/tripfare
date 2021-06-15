docker run --name postgres-db-server -e POSTGRES_PASSWORD=mysecretpassword -d -p 5432:5432 postgres
psql -h localhost -U postgres -d postgres
create database faredb ;
\c faredb
CREATE TABLE IF NOT EXISTS fare (
      id INT PRIMARY KEY NOT NULL,
      fromStop VARCHAR(20) NOT NULL,
      toStop VARCHAR(20) NOT NULL,
      fareValue FLOAT NOT NULL
) ;

insert into fare(id,fromstop,tostop,farevalue) values (1,'stop1','stop2',3.25);
insert into fare values (2,'stop1','stop3',7.30);
insert into fare values (3,'stop2','stop3',5.50);


Select f from Fare f where f.fromStop ='stop1'  and f.toStop = 'stop2';


select
    f.farevalue,
    f.fromstop,
    f."id",
    f.tostop
from
    fare f;

select
    f.farevalue,
    f.fromstop,
    f."id",
    f.tostop

from
    fare f;select
    f.farevalue,
    f.fromstop,
    f."id",
    f.tostop
from
    fare f;