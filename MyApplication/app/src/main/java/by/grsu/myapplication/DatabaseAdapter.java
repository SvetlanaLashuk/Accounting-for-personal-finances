package by.grsu.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 030 30.11.17.
 */

public class DatabaseAdapter {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context){
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    // Category
    private Cursor getAllEntries(){
        String[] columns = new String[] {DatabaseHelper.COLUMN_CATEGORY_ID, DatabaseHelper.COLUMN_CATEGORY_NAME, DatabaseHelper.COLUMN_CATEGORY_COMMENT};
        return  database.query(DatabaseHelper.TABLE_CATEGORY, columns, null, null, null, null, DatabaseHelper.COLUMN_CATEGORY_NAME);
    }

    public List<Category> getCategories(){
        ArrayList<Category> categories = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_ID));
                String categoryName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_NAME));
                String categoryComment = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_COMMENT));
                categories.add(new Category(id, categoryName, categoryComment));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  categories;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DatabaseHelper.TABLE_CATEGORY);
    }

    public Category getCategory(int id){
        Category category = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.TABLE_CATEGORY, DatabaseHelper.COLUMN_CATEGORY_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String categoryName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_NAME));
            String categoryComment = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_COMMENT));
            category = new Category(id, categoryName, categoryComment);
        }
        cursor.close();
        return  category;
    }

    public long insertCtg(Category category){

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_CATEGORY_NAME, category.getName());
        cv.put(DatabaseHelper.COLUMN_CATEGORY_COMMENT, category.getComment());

        return  database.insert(DatabaseHelper.TABLE_CATEGORY, null, cv);
    }

    public long deleteCtg(int categoryId){

        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(categoryId)};
        return database.delete(DatabaseHelper.TABLE_CATEGORY, whereClause, whereArgs);
    }

    public long updateCtg(Category category){

        String whereClause = DatabaseHelper.COLUMN_CATEGORY_ID + "=" + String.valueOf(category.getId());
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_CATEGORY_NAME, category.getName());
        cv.put(DatabaseHelper.COLUMN_CATEGORY_COMMENT, category.getComment());
        return database.update(DatabaseHelper.TABLE_CATEGORY, cv, whereClause, null);
    }


    // Income
    private Cursor getAllIncomes(){
        String[] columns = new String[] {DatabaseHelper.COLUMN_INCOME_ID, DatabaseHelper.COLUMN_INCOME_DATE, DatabaseHelper.COLUMN_NAME_CATEGORY, DatabaseHelper.COLUMN_INCOME_SUM};
        return  database.query(DatabaseHelper.TABLE_INCOME, columns, null, null, null, null, DatabaseHelper.COLUMN_INCOME_DATE);
    }

    public List<Income> getIncomes(){
        ArrayList<Income> incomes = new ArrayList<>();
        Cursor cursor = getAllIncomes();
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_INCOME_ID));
                String incomeDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_INCOME_DATE));
                String categoryName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME_CATEGORY));
                double incomeSum = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_INCOME_SUM));
                incomes.add(new Income(id, incomeDate, categoryName, incomeSum));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  incomes;
    }

    public long getCountI(){
        return DatabaseUtils.queryNumEntries(database, DatabaseHelper.TABLE_INCOME);
    }

    public Income getIncome(int id){
        Income income = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.TABLE_INCOME, DatabaseHelper.COLUMN_INCOME_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String incomeDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_INCOME_DATE));
            String categoryName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME_CATEGORY));
            double incomeSum = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_INCOME_SUM));
            income = new Income(id, incomeDate, categoryName, incomeSum);
        }
        cursor.close();
        return  income;
    }

    public long insertInc(Income income){

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_INCOME_DATE, income.getIncomeDate());
        cv.put(DatabaseHelper.COLUMN_NAME_CATEGORY, income.getCategoryName());
        cv.put(DatabaseHelper.COLUMN_INCOME_SUM, income.getIncomeSum());

        return  database.insert(DatabaseHelper.TABLE_INCOME, null, cv);
    }

    public long deleteInc(int incId){

        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(incId)};
        return database.delete(DatabaseHelper.TABLE_INCOME, whereClause, whereArgs);
    }

    public long updateInc(Income income){

        String whereClause = DatabaseHelper.COLUMN_INCOME_ID + "=" + String.valueOf(income.getId());
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_INCOME_DATE, income.getIncomeDate());
        cv.put(DatabaseHelper.COLUMN_NAME_CATEGORY, income.getCategoryName());
        cv.put(DatabaseHelper.COLUMN_INCOME_SUM, income.getIncomeSum());
        return database.update(DatabaseHelper.TABLE_INCOME, cv, whereClause, null);
    }

    // Outlay
    private Cursor getAllOutlays(){
        String[] columns = new String[] {DatabaseHelper.COLUMN_OUTLAY_ID, DatabaseHelper.COLUMN_OUTLAY_DATE, DatabaseHelper.COLUMN_TYPE_CATEGORY, DatabaseHelper.COLUMN_OUTLAY_SUM};
        return  database.query(DatabaseHelper.TABLE_OUTLAY, columns, null, null, null, null, DatabaseHelper.COLUMN_OUTLAY_DATE);
    }

    public List<Outlay> getOutlays(){
        ArrayList<Outlay> outlays = new ArrayList<>();
        Cursor cursor = getAllOutlays();
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_OUTLAY_ID));
                    String outlayDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_OUTLAY_DATE));
                    String categoryName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TYPE_CATEGORY));
                    double outlaySum = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_OUTLAY_SUM));
                    outlays.add(new Outlay(id, outlayDate, categoryName, outlaySum));
                }
                while (cursor.moveToNext());
            }
        cursor.close();
        return  outlays;
    }

    public long getCountO(){
        return DatabaseUtils.queryNumEntries(database, DatabaseHelper.TABLE_OUTLAY);
    }

    public Outlay getOutlay(int id){
        Outlay outlay = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.TABLE_OUTLAY, DatabaseHelper.COLUMN_OUTLAY_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String outlayDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_OUTLAY_DATE));
            String categoryName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TYPE_CATEGORY));
            double outlaySum = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_OUTLAY_SUM));
            outlay = new Outlay(id, outlayDate, categoryName, outlaySum);
        }
        cursor.close();
        return  outlay;
    }

    public long insertOut(Outlay outlay){

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_OUTLAY_DATE, outlay.getOutlayDate());
        cv.put(DatabaseHelper.COLUMN_TYPE_CATEGORY, outlay.getcategoryName());
        cv.put(DatabaseHelper.COLUMN_OUTLAY_SUM, outlay.getOutlaySum());

        return  database.insert(DatabaseHelper.TABLE_OUTLAY, null, cv);
    }

    public long deleteOut(int outId){

        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(outId)};
        return database.delete(DatabaseHelper.TABLE_OUTLAY, whereClause, whereArgs);
    }

    public long updateOut(Outlay outlay){

        String whereClause = DatabaseHelper.COLUMN_OUTLAY_ID + "=" + String.valueOf(outlay.getId());
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_OUTLAY_DATE, outlay.getOutlayDate());
        cv.put(DatabaseHelper.COLUMN_TYPE_CATEGORY, outlay.getcategoryName());
        cv.put(DatabaseHelper.COLUMN_OUTLAY_SUM, outlay.getOutlaySum());
        return database.update(DatabaseHelper.TABLE_OUTLAY, cv, whereClause, null);
    }

    // Report
    /*public List<String> getReport() {
        ArrayList<String> outlays = new ArrayList<>();
    String query = " SELECT " + DatabaseHelper.COLUMN _NAME_CATEGORY + " AS CATEGORY, "
            + " SUM("+ DatabaseHelper.COLUMN _INCOME_SUM + ") AS SUM "
            + " FROM " + DatabaseHelper. TABLE_INCOME
            //+ " WHERE " + DatabaseHelper.COLUMN_INCOME_DATE
	        + " GROUP BY " + DatabaseHelper.COLUMN _NAME_CATEGORY
            + " ORDER BY " + DatabaseHelper.COLUMN _NAME _ CATEGORY
            ;
    Cursor cursor = database.rawQuery(query, null);
        outlays.add(cursor);

    }*/


}
