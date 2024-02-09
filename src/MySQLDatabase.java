package src;

import java.sql.*;

public class MySQLDatabase extends Database{
    private Connection connection = null;

    public boolean connectToDbms(String db_name){
        String url = "jdbc:mysql://localhost:3306/" + db_name;
        String username = "root";
        String password = "yourPassword";
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

    public void populateDB(String table_name, String type, int num_rows, int num_columns, boolean make_index){
        if (this.connection == null) {
            System.out.print("MySQL connection is null");
            return;
        }
        try {
            Statement statement = this.connection.createStatement();
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
