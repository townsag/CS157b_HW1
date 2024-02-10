package src;

import java.sql.*;
import java.util.Random;

public class MySQLDatabase extends Database{
    private Connection connection = null;
    private String dbName;

    public MySQLDatabase(String db_name) {
        this.dbName = db_name;
        this.connectToDbms(db_name);
    }

    public boolean connectToDbms(String db_name) {
        String url = "jdbc:mysql://localhost:3306/" + db_name;
        String username = "root";
        String password = "yourPassowrd";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e){
            System.out.println("encountered an error connecting to MySQL server at");
            System.out.println("url: " + url);
            System.out.println("user: " + username);
            System.out.println("password: " + password);
            System.out.println(e);
        }
        return connection == null;
    }

    public void populateDB(String table_name, String type, int num_rows, int num_columns, boolean make_index) {
        if (this.connection == null) {
            System.out.print("MySQL connection is null");
            return;
        }
        try {
            Statement statement = this.connection.createStatement();
            // check that the table does not already exist and drop the table if it does
            String query = "SELECT * FROM information_schema.tables WHERE table_name = '" + 
                            table_name + "' AND table_schema = '" + this.dbName + "'";
            ResultSet results = statement.executeQuery(query);
            if (results.next()){
                // System.out.println("dropping existing table");
                String dropStatement = "DROP TABLE " + table_name;
                statement.executeUpdate(dropStatement);
            }

            // add the new table
            StringBuffer createTableSB = new StringBuffer();
            createTableSB.append("CREATE TABLE " + table_name + " (");
            for (int i = 0; i < (num_columns - 1); i++) {
                createTableSB.append("column" + String.valueOf(i) + " " + type + ", ");
            }
            createTableSB.append("column" + String.valueOf(num_columns - 1) + " " + type + ")");
            // System.out.println("this is the create table statement: ");
            // System.out.println(createTableSB.toString());
            statement.executeUpdate(createTableSB.toString());
            
            // ToDo: populate the new table with rows
            // format: INSERT INTO table_name (column1, column2, ...) VALUES (value1, value2, ...)
            // build the insert string using a string buffer
            StringBuffer insertSB = new StringBuffer();
            insertSB.append("INSERT INTO " + table_name + " (");
            for (int i = 0; i < (num_columns - 1); i++){
                insertSB.append("column" + String.valueOf(i) + ", ");
            }
            insertSB.append("column" + String.valueOf(num_columns - 1) + ") VALUES (");
            for (int i = 0; i < (num_columns - 1); i++) {
                insertSB.append("?, ");
            }
            insertSB.append("?)");
            // System.out.println("this is the insert statement");
            // System.out.println(insertSB.toString());

            // build the batch using a prepared statement
            this.connection.setAutoCommit(false);   // I think this makes it so that all of out data is committed
                                                    // all at once, so we dont have a half executed query if there
                                                    // is some sort of error
            PreparedStatement pStatement = this.connection.prepareStatement(insertSB.toString());
            
            
            
            Random rand = new Random();
            for(int i = 0; i < num_rows; i++){
                for(int j = 0; j< num_columns; j++){
                    if (type.equals("INTEGER")){
                        pStatement.setInt(j + 1, rand.nextInt(2 * num_rows + 1));
                    } else if (type.equals("FLOAT")){
                        pStatement.setFloat(j + 1, rand.nextFloat() * 2 - 1);
                    } else {
                        pStatement.setString(j + 1, this.getRandomString(Integer.parseInt(type.substring(8, type.length() - 1))));
                    }
                }
                pStatement.addBatch();
            }
            pStatement.executeBatch();
            this.connection.commit();


            statement.close();
        } catch (Exception e) {
            System.out.println("encountered exception in MySQLDatabase.populateDB");
            System.out.println(e);
        }
    }
}
