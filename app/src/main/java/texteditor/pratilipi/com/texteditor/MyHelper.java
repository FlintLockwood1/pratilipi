package texteditor.pratilipi.com.texteditor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by axysu on 9/1/2017.
 */

public class MyHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myDatabase";
    private static final int DATABASE_VERSION = 2;
    private static final String MUID = "_id";
    private static final String MCONTENT = "content";
    private static final String CREATEMASTER = "CREATE TABLE RULE ( " +
            MUID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            MCONTENT + " VARCHAR(255))" ;
    Context context;

    public MyHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATEMASTER);
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(context,""+e, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.execSQL("DROP TABLE IF EXISTS RULE");
        } catch (SQLException e) {
            Toast.makeText(context,""+e, Toast.LENGTH_SHORT).show();
        }
        onCreate(db);

    }

    public ContentObject getAllData(){

        SQLiteDatabase db = this.getWritableDatabase();
        String columns[] = {this.MUID,this.MCONTENT};
        Cursor cursor = db.query("RULE",columns,null,null,null,null,null);
        ContentObject myObject = new ContentObject();
        while(cursor.moveToNext()){

            myObject.id = cursor.getInt(cursor.getColumnIndex(this.MUID));
            myObject.content = cursor.getString(cursor.getColumnIndex(this.MCONTENT));
        }
        return myObject;
    }
    public int updateTable(int id,String string){

        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereClause = {String.valueOf(id)};
        ContentValues values1 = new ContentValues();
        values1.put(this.MCONTENT, string);
        int rowsUpdated = db.update("RULE",values1,this.MUID + " =?",whereClause);
        return rowsUpdated;
    }

    public void insertTable(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values1 = new ContentValues();
        values1.put(this.MCONTENT, s);
        long id = db.insert("RULE",null,values1);
    }
}
