package src;

import java.sql.*;

class Test {

    /*
     * Format for how this will be called:
     *  - java RecordMeasurer dbms db table_name type num_rows num_columns index_no_index
     *                     args[0]  1  args[2]    args[3]       args[4]     args[5]
     * Example call:
     *  - java RecordMeasurer sqlite "test.sqlite" FOO "VARCHAR(39)" 10000 20 false
     */
    public static void main(String[] args){
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_shop", "root", "yourPassword");

            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("select * from books");

            int book_id;
            String title;

            while (resultSet.next()){
                book_id = resultSet.getInt("book_id");
                title = resultSet.getString("title");
                System.out.println("ID: " + book_id + " , title: " + title);
            }
            
            resultSet.close();
            statement.close();
            connection.close();

        } catch(Exception e){
            System.out.println(e);
        }
        

    }
}


