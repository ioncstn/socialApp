# socialApp

This is a social app that lets the users send friend requests, decline or accept them and send messages between friends.

In order to be able to use this app you must set up the data base for it. I suggest using PostgreSQL.

In order to use PostgreSQL you will have to download a JDBC driver for PostgreSQL, I recommend downloading the 42.5.0 version (that is the one I used).
After downloading the JDBC, go in IntelliJ IDEA and go to: File > Project Structure > Libraries and add the JDBC.

You must create a new database and make sure the connection between the project and database is working.
Create a database with whatever name you want and go in IntelliJ Idea and go to: Database > New > Data Source > PostgreSQL. There you must fill in the user and 
the password for your PostgreSQL server and change the Database field to the name of the database you created for this project. Now you can check if the connection is working with the help of the IDE.

The final step to make the database work for this project is to add the tables. This is the code for them:
(you can open a query console inside the IDE at: Database > Select the database you just added > New > Query Console)

```
CREATE TABLE users(
  id INT PRIMARY KEY,
  username VARCHAR(50),
  password VARCHAR(20),
  email VARCHAR(50)
);

CREATE TABLE messages(
  userid1 INT,
  userid2 INT,
  text VARCHAR(500),
  date TIMESTAMP,
  FOREIGN KEY (userid1) REFERENCES users(id),
  FOREIGN KEY (userid2) REFERENCES users(id)
);

CREATE TABLE friendships(
  user1 INT,
  user2 INT,
  PRIMARY KEY (user1, user2),
  id INT,
  date TIMESTAMP,
  pending BOOLEAN
);
```
