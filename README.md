# Manezator


-- Run db dev:

    - $ docker-compose up

-- Create dev database:

	- $ mongo
	- $ use manezatordb
	- $ db.createUser({user:"manezator", pwd:"manezator", roles:[{role:"dbOwner", db: "manezatordb"}]})
	

-- Run app:

	- $ mvn clean install 
	- $ mvn spring-boot:run

