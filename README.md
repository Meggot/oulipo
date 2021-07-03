# Oulipo

Create stories and collaborate with strangers or friends.

Microservices:

#Audit

Listens to all the JMS queues and stores Audit events of every noteworthy interaction to help tracing
#Gateway

Routes requests to all microservices, also provides authentication and CORS
#MetaData

Stores meta data about any 'entity' such as upvotes, shares, favourites ETC
#Project

Stores all project information, and copy information intended for users to interact with

#User

Stores all account information, and authenticates logins
