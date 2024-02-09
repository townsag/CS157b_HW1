package src;

import java.sql.*;

public class MySQLDatabase extends Database{
    private Connection connection = null;

    public MySQLDatabase(String db_name) {
        this.connectToDbms(db_name);
    }

    public boolean connectToDbms(String db_name) {
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
            String query = "SELECT * FROM information_schema.tables WHERE table_name = '" + table_name + "'";
            ResultSet results = statement.executeQuery(query);
            if (results.next()){
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
            System.out.println("this is the create table statement: ");
            System.out.println(createTableSB.toString());
            statement.executeUpdate(createTableSB.toString());
            
            // ToDo: populate the new table with rows


            statement.close();
        } catch (Exception e) {
            System.out.println("encountered exception in MySQLDatabase.populateDB");
            System.out.println(e);
        }
    }
}
