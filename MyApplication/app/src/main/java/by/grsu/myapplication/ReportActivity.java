package by.grsu.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    //private ListView increportList;
   ArrayAdapter<String> arrayAdapter;
    //private ListView outreportList;
    //ArrayAdapter<Outreport> arrayAdapter;
    ListView incReport;
    ListView outReport;
    Cursor cursor;
    SimpleCursorAdapter adapter;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        incReport = (ListView)findViewById(R.id.list4);
        outReport = (ListView)findViewById(R.id.list5);

       databaseHelper = new DatabaseHelper(getApplicationContext());

    }



    //--------- DataAdapter ------



    @Override
    public void onResume() {
        super.onResume();

        db = databaseHelper.getReadableDatabase();

        //получаем данные из бд в виде курсора
       // String query = " SELECT " + DatabaseHelper.COLUMN_NAME_CATEGORY + " , "
        // " FROM " + DatabaseHelper. TABLE_INCOME +
        //+ " WHERE " + DatabaseHelper.COLUMN_INCOME_DATE

        // + " SUM("+ DatabaseHelper.COLUMN_INCOME_SUM + ")" +
        // " GROUP BY " + DatabaseHelper.COLUMN_NAME_CATEGORY +
        // " ORDER BY " + DatabaseHelper.COLUMN_NAME_CATEGORY;

      /*  String query = " SELECT " + DatabaseHelper.COLUMN_NAME_CATEGORY + " , "
                + " SUM("+ DatabaseHelper.COLUMN_INCOME_SUM + ") AS SUM"
                + " FROM " + DatabaseHelper. TABLE_INCOME
                //+ " WHERE " + DatabaseHelper.COLUMN_INCOME_DATE
                + " GROUP BY " + DatabaseHelper.COLUMN_NAME_CATEGORY
               + " ORDER BY " + DatabaseHelper.COLUMN_NAME_CATEGORY

     ;*/
        /*String query = " SELECT * " + " FROM " + DatabaseHelper. TABLE_INCOME
                //+ " WHERE " + DatabaseHelper.COLUMN_INCOME_DATE
              + " GROUP BY " + DatabaseHelper.COLUMN_NAME_CATEGORY
                + " ORDER BY " + DatabaseHelper.COLUMN_NAME_CATEGORY;*/


       /* String query = " SELECT " + DatabaseHelper.COLUMN_NAME_CATEGORY + " , "
                + DatabaseHelper.COLUMN_INCOME_SUM
                + " FROM " + DatabaseHelper. TABLE_INCOME
                + " GROUP BY " + DatabaseHelper.COLUMN_NAME_CATEGORY
                + " ORDER BY " + DatabaseHelper.COLUMN_NAME_CATEGORY
                ;


        cursor =  db.rawQuery(query, null);*/

       //String query = " SELECT * FROM income GROUP BY categoryName ORDER BY categoryName";
        String query = " SELECT * SUM(incomeSum)FROM income GROUP BY categoryName ORDER BY categoryName";
        cursor =  db.rawQuery(query, null);


     //   cursor = db.query(DatabaseHelper.TABLE_INCOME, new String[] {DatabaseHelper.COLUMN_NAME_CATEGORY, " SUM("+ DatabaseHelper.COLUMN_INCOME_SUM + ") AS sum "}, null, null, DatabaseHelper.COLUMN_NAME_CATEGORY, null, DatabaseHelper.COLUMN_NAME_CATEGORY);


      //  cursor = db.query("income", new String[] {"categoryName", " SUM(incomeSum)"}, null, null, "categoryName", null, "categoryName");
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[] {"categoryName", "incomeSum"};
        // создаем адаптер, передаем в него курсор
        adapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, cursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);

        incReport.setAdapter(adapter);

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
       // Закрываем подключение и курсор
        db.close();
        cursor.close();  }
}
