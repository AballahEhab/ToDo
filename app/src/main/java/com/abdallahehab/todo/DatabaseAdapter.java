package com.abdallahehab.todo;

      import android.content.ContentValues;
      import android.content.Context;
      import android.database.Cursor;
      import android.database.sqlite.SQLiteDatabase;
      import android.database.sqlite.SQLiteOpenHelper;
      import android.text.TextUtils;

      import androidx.annotation.Nullable;

      import java.util.ArrayList;
      import java.util.Collections;

public class DatabaseAdapter {

    static DatabaseHelper dphelper;

    //constructor
    public DatabaseAdapter(Context context){
        dphelper=new DatabaseHelper(context);

    }

    // insert intity fun
    public long insertEntity(NoteDataModel entity){
        SQLiteDatabase db=dphelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DatabaseHelper.col_title,entity.getTitle());
        contentValues.put(DatabaseHelper.cl_body,entity.getBody());
        contentValues.put(DatabaseHelper.cl_date,entity.getDate());

        long id= db.insert(DatabaseHelper.db_table_name,null,contentValues);  ;

        return id;

    }

    // updae intity fun
    public long updateEntity(NoteDataModel entity){
        SQLiteDatabase db=dphelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DatabaseHelper.col_title,entity.getTitle());
        contentValues.put(DatabaseHelper.cl_body,entity.getBody());
        contentValues.put(DatabaseHelper.cl_date,entity.getDate());

        String where = "id=?";
        String[] whereArgs = new String[] {String.valueOf(entity.getId())};
        long id=db.update(DatabaseHelper.db_table_name, contentValues, where, whereArgs);
        return id;
    }

    public long deleteEntities(String[] ids){
        SQLiteDatabase db=dphelper.getWritableDatabase();
//        String where = "id=?";
//        String[] whereArgs = ids;
//        long id=db.delete(DatabaseHelper.db_table_name, where, whereArgs);

        String whereClause = String.format(DatabaseHelper.col_id + " in (%s)", new Object[] { TextUtils.join(",", Collections.nCopies(ids.length, "?")) });
        long id= db.delete(DatabaseHelper.db_table_name, whereClause, ids);

        return id;
    }

    // get the last row of the database
    public ArrayList<NoteDataModel> getAllEntities(){
        NoteDataModel entity =null;
        Cursor c;

        //getting the database object form the the helper object
        SQLiteDatabase dp=dphelper.getReadableDatabase();

        String []columns ={DatabaseHelper.col_title,DatabaseHelper.cl_body,DatabaseHelper.cl_date,DatabaseHelper.col_id}; // get all columns

        // setting the cursor to point on a selected data
        c =dp.query(DatabaseHelper.db_table_name,columns,null,null,null,null,null);

        ArrayList<NoteDataModel> dataList = new ArrayList<NoteDataModel>();
        // looping on the data to get to the last record
        while (c.moveToNext()){
            dataList.add(new NoteDataModel(c.getString(0),c.getString(1),c.getString(2),c.getString(3)));

        }

        return dataList;
    }

    //this class will creat and drope
    static class DatabaseHelper extends SQLiteOpenHelper {
        private static final int db_version=1;
        private static final String db_name="NOTES";
        private static final String db_table_name="TABLE_NOTES";
        private static final String col_id="id";
        private static final String col_title ="TITLE";
        private static final String cl_body ="BODY";
        private static final String cl_date ="DATE";

        private static final String CREAT_NOTE_TABLE = "CREATE TABLE " + db_table_name + " (" + col_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + col_title +" TEXT, " + cl_body + " TEXT, " + cl_date + " TEXT);";


        public DatabaseHelper(@Nullable Context context) {
            super(context, db_name, null, db_version);


        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREAT_NOTE_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+db_table_name);
            //onCreate(db);
        }
    }
}
