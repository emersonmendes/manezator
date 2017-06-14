# Manezator


-- [Install mongodb](https://docs.mongodb.com/manual/installation/)

-- create dev database:

- db.createUser({user:"manezator", pwd:"manezator", roles:[{role:"dbOwner", db: "manezatordb"}]})

-- run app
- mvn clean install 
- mvn spring-boot:run

