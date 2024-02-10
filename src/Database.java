package src;

import java.util.Random;

abstract class Database {
    
    public String getRandomString(int length) {
        StringBuffer temp = new StringBuffer();
        Random rand = new Random();
        for (int i = 0; i < length; i++){
            temp.append(String.valueOf(rand.nextInt(2)));
        }
        return temp.toString();
    }

    // this takes the name of the database to connect to as an argument
    // in the case of a mySQL database this is as it sounds
    // in the case of a sqlite database this is the name of the .sqlite file 
    // with the database to connect to
    public abstract boolean connectToDbms(String db_name);
    
    /*
     * This method assumes that the connection to the databse already exists
     * args:
     *      tablename:  Table names can use any character that is allowed in a file name except for a period 
     *                  or a forward slash. Table names must be 32 characters or less
     *      type:       either of  INTEGER, FLOAT, or VARCHAR(n)
     *      num rows:   integer greater than zero
     *      num columns:inteher greater than zero
     *      make_index: boolean
     */
    public abstract void populateDB(String table_name, String type, int num_rows, int num_columns, boolean make_index);

    // VARCHAR(39) (a random row means make a string of 39 chars out of characters "0" and "1") into this table. 
    // Similar ideas should be used for random INTEGER where you pick a random number between 0 and twice the 
    // number of rows, and random FLOAT where you pick a random float between -1.0 and 1
}
