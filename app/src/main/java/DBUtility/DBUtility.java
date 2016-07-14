package DBUtility;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import Common.Common;

/**
 * Created by 11059 on 2016/7/13 0013.
 */
public class DBUtility extends SQLiteOpenHelper {
    private SQLiteDatabase db = null;

    public DBUtility(Context context) {
        super(context, Common.DB_TABLE_NAME, null, Common.DB_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Common.DB_TABLE_NAME + "(" +
                Common.DB_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Common.DB_COLUMN_TITLE + " TEXT," +
                Common.DB_COLUMN_AUTHOR + " TEXT," +
                Common.DB_COLUMN_CTIME + " TEXT," +
                Common.DB_COLUMN_CONTENT + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS NOTE");
        onCreate(db);
    }

    public void Insert(String table, String title, String author, String ctime, String content) {
        db.execSQL("insert into " + table + "(TITLE,AUTHOR,CTIME,CONTENT) values(?,?,?,?)", new Object[]{title, author, ctime, content});
    }

    public void Delete(String table, int id) {
        db.execSQL("delete from " + table + " where ID=?", new Object[]{id});
    }

    public Cursor Query(String table, String columns, int id) {
        Cursor cursor = null;
        if (id == -1)
            cursor = db.rawQuery("select " + columns + " from " + table, null);
        else
            cursor = db.rawQuery("select " + columns + " from " + table + " where ID=" + id, null);

        cursor.moveToFirst();

        return cursor;
    }

    @Override
    public synchronized void close() {
        super.close();
        db.close();
    }
}
