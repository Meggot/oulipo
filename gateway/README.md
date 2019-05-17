 -LOCALLY-

 Kafka uses ZooKeeper so you need to first start a ZooKeeper server if you don't already have one. You can use the convenience script packaged with kafka to get a quick-and-dirty single-node ZooKeeper instance.

 `bin/zookeeper-server-start.sh config/zookeeper.properties`

...

Now start the Kafka server:
	
`bin/kafka-server-start.sh config/server.properties`
...

To view a consumer from the local Kafka:

`./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic account-creation`

Now you can run everything in spring boot using the spring profile: DEV

 -DOCKER-
Docker uses the spring profile DOCKER but the platform compose will do that
so no worries

 1. build all projects individually using `mvn install dockerfile:build`
 2. make sure no docker containers are running using : `docker ps`
 3. make sure all containers are removed using:  `docker rm $(docker ps -a -q)`
 4. remove all images. docker rmi $(docker images -q)
 4. run using `docker-compose up -d`

--KSQL---

To connect to the KSQL terminal once the server comes online using docker-compose, use the following command:

`docker-compose exec ksql-cli ksql http://ksql-server:8088`

Set topic history retrieval using:

`SET 'auto.offset.reset' = 'earliest';`

to view a topics content use:

`print 'account-creation' from BEGINNING`

-DEMO--

Create user stream:

CREATE STREAM account_creation (body STRUCT<idField VARCHAR, username VARCHAR, email VARCHAR>) with (KAFKA_TOPIC='account-creation', VALUE_FORMAT = 'JSON');
CREATE STREAM account_creation_col AS SELECT body->idField as USER_ID, body->username as USERNAME, body->email as USEREMAIL FROM account_creation;

create project stream:

CREATE STREAM project_creation (body STRUCT<idField INT, title VARCHAR, type VARCHAR,synopsis VARCHAR>) with (KAFKA_TOPIC='project-creation', VALUE_FORMAT='JSON');
CREATE STREAM project_creation_col_id AS SELECT ROWTIME as PROJECT_CREATION_DATE, body -> idfield as project_id, body -> title as TITLE, body -> type as TYPE, body -> synopsis as SYNOPSIS, body->originalAuthor as AUTHOR_NAME FROM project_creation

LINK STREAMS;

CREATE STREAM new_account_projects_2 AS SELECT * FROM account_creation_2 a JOIN project_creation_col_id b WITHIN 1 HOUR ON a.username = b.author_name;

CREATE STREAM system_add_tag AS SELECT nap.b_title AS entityId, body STRUCT <value='NEW_PROJECT'> from NEW_ACCOUNT_PROJECTS as nap;


CREATE STREAM SYSTEM_ADD_TAG WITH (TIMESTAMP='creation_time',VALUE_FORMAT='JSON') AS SELECT b_project_id as entityId, value = 'NEW_PROJECT', type = 'PROJECT_UPDATE' FROM NEW_ACCOUNT_PROJECTS_2;