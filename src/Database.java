package src;

import java.sql.*;
import java.util.Random;

public class Database{
    private Connection connection = null;
    private String dbName;
    private String dbms;
    private String mysql_url = "jdbc:mysql://localhost:3306/";      //variable to change if you want to switch mysql url
    private String mysql_user = "root";                             //mysql username, we used the default root but if you want to use your own mysql replace this variable
    private String mysql_pass = "LTAndr3w";                         //mysql password, switch out for your own password
    private String sqlite_url = "jdbc:sqlite:";                     //variable to change if you want to switch sqlite url


    public Database(String dbms, String db_name) {
        this.dbName = db_name;
        this.dbms = dbms;
        this.connectToDbms(dbms, db_name);
    }

    //connects to the stated dbms
    public boolean connectToDbms(String dbms, String db_name) {
        if(dbms.equalsIgnoreCase("mysql")){
            String url = mysql_url + db_name;
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                this.connection = DriverManager.getConnection(url, mysql_user, mysql_pass);
            } catch (Exception e){
                System.out.println("encountered an error connecting to MySQL server at");
                System.out.println("url: " + url);
                System.out.println("user: " + mysql_user);
                System.out.println("password: " + mysql_pass);
                System.out.println(e);
            }
        }
        else{
            String url = sqlite_url + db_name;
            try{
                Class.forName("org.sqlite.JDBC");
                this.connection = DriverManager.getConnection(url);
            } catch (Exception e){
                System.out.println("encountered an error connecting to sqlite file at");
                System.out.println("url: " + url);
                System.out.println(e);
            }
        }

        return connection == null;
    }

    //insert rows, columns, and possibly and index for the first column in this method
    public void populateDB(String table_name, String type, int num_rows, int num_columns, boolean make_index) {
        if (this.connection == null) {
            System.out.print("db connection is null");
            return;
        }
        try {
            Statement statement = this.connection.createStatement();
            // check that the table does not already exist and drop the table if it does

            statement.executeUpdate("DROP TABLE IF EXISTS " + table_name + ";");

            // add the new table
            StringBuffer createTableSB = new StringBuffer();
            createTableSB.append("CREATE TABLE " + table_name + " (");
            for (int i = 0; i < (num_columns - 1); i++) {
                createTableSB.append("column" + String.valueOf(i) + " " + type + ", ");
            }
            createTableSB.append("column" + String.valueOf(num_columns - 1) + " " + type + ")");
            statement.executeUpdate(createTableSB.toString());
            
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

            //if make_index is true, create an index on the first column
            if (make_index) {
                System.out.println("adding index on first column");
                System.out.print("CREATE INDEX column0Idx ON " + table_name + " (column0)");
                statement.executeUpdate("CREATE INDEX column0Idx ON " + table_name + " (column0)");     //creates a index called unique for the first column
            }

            // build the batch using a prepared statement
            this.connection.setAutoCommit(false);
            PreparedStatement pStatement = this.connection.prepareStatement(insertSB.toString());
            
            
            //insert rows into the columns
            Random rand = new Random();
            for(int i = 0; i < num_rows; i++){
                for(int j = 0; j< num_columns; j++){
                    if (type.equals("INTEGER")){                                                                                                       //inserts random integers with the given restriction if type is int
                        pStatement.setInt(j + 1, rand.nextInt(2 * num_rows + 1));
                    } else if (type.equals("FLOAT")){                                                                                                  //inserts random floats with the given restriction if type is float
                        pStatement.setFloat(j + 1, rand.nextFloat() * 2 - 1);
                    } else {                                                                                                                           //if type is varchar, insert 0 or 1 at random based on the size you provide for the varchar
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

    //returns random string for the varchar option
    public String getRandomString(int length) {
        StringBuffer temp = new StringBuffer();
        Random rand = new Random();
        for (int i = 0; i < length; i++){
            temp.append(String.valueOf(rand.nextInt(2)));
        }
        return temp.toString();
    }
}
