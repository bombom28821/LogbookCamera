package fpt.grw.cameraapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CameraApp";
    private static final String TABLE_IMAGES = "Images";

    public static final String ID = "id";
    public static final String IMG_URL = "img_url";

    private SQLiteDatabase sqLiteDatabase;

    private static final String DATABASE_CREATE_TABLE_IMAGES = String.format(
            "CREATE TABLE %s (" +
                    "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   %s TEXT)",
            TABLE_IMAGES, ID, IMG_URL);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 4);
        sqLiteDatabase = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_TABLE_IMAGES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);

        Log.v(this.getClass().getName(), DATABASE_NAME + " database upgrade to version " +
                newVersion + " - old data lost");
        onCreate(db);
    }

    public long createImg(Image img) {
        ContentValues rowValues = new ContentValues();
        rowValues.put(IMG_URL , img.getImg_url());

        return sqLiteDatabase.insertOrThrow(TABLE_IMAGES, null, rowValues);
    }

    public ArrayList<Image> getImgs() {
        Cursor cursor = sqLiteDatabase.query(TABLE_IMAGES, new String[] {ID, IMG_URL},
                null, null, null, null, ID);

        ArrayList<Image> results = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                Integer id = cursor.getInt(0);
                String img_url = cursor.getString(1);

                Image image = new Image(id, img_url);
                results.add(image);
                cursor.moveToNext();
            }while (cursor.moveToNext());
        }
        cursor.close();
        return results;
    }
    public Image getImg(int idImg) {
    String sql = "select * from " + TABLE_IMAGES + " where " + ID + " = " + idImg;
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        Integer id = cursor.getInt(0);
        String img_url = cursor.getString(1);
        return new Image(
                id,
                img_url
        );
    }
}
