 -LOCALLY-

 Kafka uses ZooKeeper so you need to first start a ZooKeeper server if you don't already have one. You can use the convenience script packaged with kafka to get a quick-and-dirty single-node ZooKeeper instance.

> bin/zookeeper-server-start.sh config/zookeeper.properties
[2013-04-22 15:01:37,495] INFO Reading configuration from: config/zookeeper.properties (org.apache.zookeeper.server.quorum.QuorumPeerConfig)
...

Now start the Kafka server:
	
> bin/kafka-server-start.sh config/server.properties
[2013-04-22 15:01:47,028] INFO Verifying properties (kafka.utils.VerifiableProperties)
[2013-04-22 15:01:47,051] INFO Property socket.send.buffer.bytes is overridden to 1048576 (kafka.utils.VerifiableProperties)
...

Now you can run everything in spring boot using the spring profile: DEV

 -DOCKER-
Docker uses the spring profile DOCKER but the platform compose will do that
so no worries

 1. build all projects individually using `mvn install dockerfile:build`
 2. make sure no docker containers are running using : docker ps
 3. make sure all containers are removed using:  `docker rm $(docker ps -a -q)`
 4. run using `docker-compose up -d`
