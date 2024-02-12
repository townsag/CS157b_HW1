Group Members: Peter Guilhamet and Andrew Townsend

Download these two jar files and put them here (HW1/lib):
https://github.com/xerial/sqlite-jdbc?tab=readme-ov-file#download
https://search.maven.org/remotecontent?filepath=org/slf4j/slf4j-api/1.7.36/slf4j-api-1.7.36.jar


There are a few global variable set at the top of the Database class to change for personal use:
    mysql_url,
    mysql_user, &       
    mysql_pass.                       
    
mysql_url is currently set to the standard jdbc path: jdbc:mysql://localhost:3306/. However, it is set as a variable at the top in case you want to use your own port number.
The value of mysql_user can be replaced with the user's username if they want to use their own private mysql account instead of the default root.
Same concept applies for mysql_pass if the user wants to use their own mysql account, but for the time being we have LTAndr3w set as the password for our root account. 


Compile and run the project from the CS157a_HW1/ dirrectory using these commands:
javac -classpath lib/mysql-connector-j-8.3.0.jar:lib/sqlite-jdbc-3.45.1.0.jar:lib/slf4j-api-1.7.36.jar:. src/RecordMeasurer.java
java -classpath lib/mysql-connector-j-8.3.0.jar:lib/sqlite-jdbc-3.45.1.0.jar:lib/slf4j-api-1.7.36.jar:. src.RecordMeasurer sqlite "test.db" table2 'VARCHAR(11)' 3 6 true
(second line is an example of what we used to make a db in our sqlite file).



