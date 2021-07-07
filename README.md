_Oulipo!_

Try it yourself! [Website Link](http://www.oulipo.co.uk)


This is an application that lets people write text with each other in realtime across the internet. Finish that book with peer sourcing and read 'serial' novels created by other people. Remeber that game you played as a kid where you'd write one paragraph/line/word and then someone else would add to it? This is that across the internet! So get playing..

'Oulipo' was a french guild of writers that employed techniques such as this into their writing, they are responsible for most of the mid-19th century french literarture. Such writers such as Georges Perec and Italo Calvino, poets Oskar Pastior, Jean Lescure and poet/mathematician Jacques Roubaud. 

Technologies used in this application:

Springboot, HATEOS, Kafka Streams, Dockerized services, GitHub Actions, Terraform, Angular7 with NGrx.

The service is designed with CI in mind, so any push to the master branch will deploy onto my personal AWS environment, and the website is currently deployed on a so called 'staging' environment.

Stores meta data about any 'entity' such as upvotes, shares, favourites ETC
#Project

Stores all project information, and copy information intended for users to interact with

#User

Stores all account information, and authenticates logins
=======
Current Live Version 0.42-ALPHA
