package src;

class RecordMeasurer {

    /*
     * Format for how this will be called:
     *  - java RecordMeasurer dbms db table_name type num_rows num_columns index_no_index
     *                     args[0]  1  args[2]  args[3] args[4]   args[5]     args[6]
     * Example call:
     *  - java RecordMeasurer sqlite "test.sqlite" FOO "VARCHAR(39)" 10000 20 false
     */
    public static void main(String[] args){
        if (args.length != 7) {
            System.out.println("please use exactly this format:");
            System.out.println("java RecordMeasurer dbms db table_name type num_rows num_columns index_no_index");
            System.exit(1);
        }

        System.out.println("dbms: " + args[0]);
        System.out.println("db: " + args[1]);
        System.out.println("table name: " + args[2]);
        System.out.println("type: " + args[3]);
        System.out.println("num rows: " + args[4]);
        System.out.println("num cols: " + args[5]);
        System.out.println("index: " + args[6]);


        Database db = new Database(args[0], args[1]);
        db.populateDB(args[2], args[3], Integer.parseInt(args[4]), Integer.parseInt(args[5]), Boolean.parseBoolean(args[6]));
    }
}


 