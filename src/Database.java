package src;

abstract class Database {
    // this takes the name of the database to connect to as an argument
    // in the case of a mySQL database this is as it sounds
    // in the case of a sqlite database this is the name of the .sqlite file 
    // with the database to connect to
    public abstract boolean connectToDbms(String db_name);
    // this method assumes that the connection to the database already exists
    public abstract void createTable(String table_name);
    // This method assumes that the connection to the databse already exists
    public abstract void populateDB(String table_name, String type, int num_rows, int num_columns, boolean make_index);
}
