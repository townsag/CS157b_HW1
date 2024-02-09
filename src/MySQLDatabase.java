package src;

import java.sql.*;

public class MySQLDatabase {
    private Connection connection = null;

    public MySQLDatabase(String dbms, String db_name) {
        this.connectToDbms(dbms, db_name);
    }

    public boolean connectToDbms(String dbms, String db_name) {
        if(dbms.equals("mysql")){
            String url = "jdbc:mysql://localhost:3306/" + db_name;
            String username = "root";
            String password = "LTAndr3w";
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
        }
        else{
            String url = "jdbc:sqlite:" + db_name;
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

    public void populateDB(String table_name, String type, int num_rows, int num_columns, boolean make_index) {
        if (this.connection == null) {
            System.out.print("MySQL connection is null");
            return;
        }
        try {
            // ToDo: check that the table does not already exist and drop the table if it does
            Statement statement = this.connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS " + table_name + ";");
            StringBuffer createTableSB = new StringBuffer();
            createTableSB.append("CREATE TABLE " + table_name + " (");
            for (int i = 0; i < (num_columns - 1); i++) {
                createTableSB.append("column" + String.valueOf(i) + " " + type + ", ");
            }
            createTableSB.append("column" + String.valueOf(num_columns - 1) + " " + type + ")");
            System.out.println("this is the create table statement: ");
            System.out.println(createTableSB.toString());
            statement.executeUpdate(createTableSB.toString());

            if (make_index == true) {
                statement.executeUpdate("CREATE UNIQUE INDEX unique ON " + table_name + " (column0);");     //creates a index called unique for the first column
            }

            //need to differentiate between VARCHAR, INTEGER, and FLOAT for row insertion of random data next, something along those lines


            statement.close();
        } catch (Exception e) {
            System.out.println("encountered exception in MySQLDatabase.populateDB");
            System.out.println(e);
        }
    }
}
