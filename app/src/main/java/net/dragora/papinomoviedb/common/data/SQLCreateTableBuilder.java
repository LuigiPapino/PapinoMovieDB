package net.dragora.papinomoviedb.common.data;

/**
 * Created by nietzsche on 25/06/15.
 */
public class SQLCreateTableBuilder {
    protected StringBuffer buffer = new StringBuffer();
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public SQLCreateTableBuilder(String tableName, String idColumn){
        buffer.append("CREATE TABLE ")
                .append(tableName)
                .append(" (")
                .append(idColumn)
                .append( " INTEGER PRIMARY KEY")
        .append(COMMA_SEP);

    }

    public static SQLCreateTableBuilder init(String tableName, String idColumnName){
        return  new SQLCreateTableBuilder(tableName,idColumnName);
    }

    public SQLCreateTableBuilder appendTextColumn(String ColumnName){
        buffer.append(ColumnName)
                .append(TEXT_TYPE)
                .append(COMMA_SEP);
        return  this;
    }



    public String build(){
        buffer.deleteCharAt(buffer.length() - 1); // remove last coma
        buffer.append(" );");
        return buffer.toString();
    }


}
