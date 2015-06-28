package net.dragora.papinomoviedb.common.data;

/**
 * Created by nietzsche on 25/06/15.
 */
public class SQLDropTableBuilder {

    public static String dropIfExist(String tableName){
        return "DROP TABLE IF EXISTS " + tableName;
    }
}
