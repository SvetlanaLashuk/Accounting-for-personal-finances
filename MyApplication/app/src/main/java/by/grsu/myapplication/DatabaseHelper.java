package by.grsu.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 030 30.11.17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
   // private static final String DATABASE_NAME = "bk.db"; // название бд
   private static final String DATABASE_NAME = "b.db";
    private static final int SCHEMA = 1; // версия базы данных

    static final String TABLE_CATEGORY = "category"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_CATEGORY_ID = "_id";
    public static final String COLUMN_CATEGORY_NAME = "categoryName";
    public static final String COLUMN_CATEGORY_COMMENT = "categoryComment";

    static final String TABLE_INCOME = "income"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_INCOME_ID = "_id";
    public static final String COLUMN_INCOME_DATE = "incomeDate";
    public static final String COLUMN_NAME_CATEGORY= "categoryName";
    public static final String COLUMN_INCOME_SUM = "incomeSum";


    static final String TABLE_OUTLAY = "outlay";
    public static final String COLUMN_OUTLAY_ID = "_id";
    public static final String COLUMN_OUTLAY_DATE = "outlayDate";
    public static final String COLUMN_TYPE_CATEGORY= "categoryName";
    public static final String COLUMN_OUTLAY_SUM = "outlaySum";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " +
                TABLE_CATEGORY + " (" +
                COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_CATEGORY_NAME + " TEXT, " +
                COLUMN_CATEGORY_COMMENT + " TEXT);");

        db.execSQL("CREATE TABLE " +
                TABLE_INCOME + " (" +
                COLUMN_INCOME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_INCOME_DATE + " TEXT, " +
                COLUMN_NAME_CATEGORY + " TEXT, " +
                COLUMN_INCOME_SUM + " REAL);");

        db.execSQL("CREATE TABLE " +
                TABLE_OUTLAY + " (" +
                COLUMN_OUTLAY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_OUTLAY_DATE + " TEXT, " +
                COLUMN_TYPE_CATEGORY + " TEXT, " +
                COLUMN_OUTLAY_SUM + " REAL);");


    }

    // Не удаляем категории
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_CATEGORY);
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_INCOME);
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_OUTLAY);
        onCreate(db);
    }

}
