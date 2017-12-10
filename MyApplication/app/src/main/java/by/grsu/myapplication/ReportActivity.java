package by.grsu.myapplication;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    ListView incReport;
    ListView outReport;
    TextView toTextView;
    TextView fromTextView;
    Cursor incCursor;
    Cursor outCursor;
    SimpleCursorAdapter incAdapter;
    SimpleCursorAdapter outAdapter;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Calendar dateAndTime=Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        incReport = (ListView)findViewById(R.id.list4);
        outReport = (ListView)findViewById(R.id.list5);
        fromTextView = (TextView)findViewById(R.id.textView1);
        toTextView = (TextView)findViewById(R.id.textView2);
        setInitialDateTime();
       databaseHelper = new DatabaseHelper(getApplicationContext());

    }

    public void displayReport(View view) {
        String d1 = fromTextView.getText().toString();
        String d2 = toTextView.getText().toString();
        db = databaseHelper.getReadableDatabase();
        String incQuery = " SELECT *, SUM(incomeSum) FROM income WHERE incomeDate BETWEEN '"+ d1 +"' AND '" + d2 + "' GROUP BY categoryName ORDER BY categoryName";
        incCursor =  db.rawQuery(incQuery, null);
        String[] headers = new String[] {"categoryName", "SUM(incomeSum)"};
        // создаем адаптер, передаем в него курсор
        incAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, incCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        incReport.setAdapter(incAdapter);

        String outQuery = " SELECT *, SUM(outlaySum) FROM outlay WHERE outlayDate BETWEEN '"+ d1 +"' AND '" + d2 + "' GROUP BY categoryName ORDER BY categoryName";
        outCursor =  db.rawQuery(outQuery, null);
        String[] header = new String[] {"categoryName", "SUM(outlaySum)"};
        // создаем адаптер, передаем в него курсор
        outAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, outCursor, header, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        outReport.setAdapter(outAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();


      /*  String query = " SELECT " + DatabaseHelper.COLUMN_NAME_CATEGORY + " , "
                + " SUM("+ DatabaseHelper.COLUMN_INCOME_SUM + ") AS SUM"
                + " FROM " + DatabaseHelper. TABLE_INCOME
                //+ " WHERE " + DatabaseHelper.COLUMN_INCOME_DATE
                + " GROUP BY " + DatabaseHelper.COLUMN_NAME_CATEGORY
               + " ORDER BY " + DatabaseHelper.COLUMN_NAME_CATEGORY

     ;*/


    }

    @Override
    public void onDestroy(){
        super.onDestroy();
       // Закрываем подключение и курсор
        db.close();
        incCursor.close();
        outCursor.close();}


    public void DPicker(){
        new DatePickerDialog(ReportActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void DatPicker(){
        new DatePickerDialog(ReportActivity.this, dp,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // отображаем диалоговое окно для выбора даты
    public void dateFrom(View view){
        DPicker();
        /*new DatePickerDialog(ReportActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();*/

    }

    public void dateTo(View view){

       /* new DatePickerDialog(ReportActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();*/
        DatPicker();

    }

    // установка начальных даты и времени
    private void setInitialDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        fromTextView.setText(date);
        toTextView.setText(date);
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //dateAndTime.set(Calendar.YEAR, year);
            // dateAndTime.set(Calendar.MONTH, monthOfYear);
            // dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            // String textDateParam = year + "." + (monthOfYear + 1) + "." + dayOfMonth;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date(year-1900, monthOfYear,  dayOfMonth));
            fromTextView.setText(date);
           // toTextView.setText(date);
            // setInitialDateTime();
        }
    };

    DatePickerDialog.OnDateSetListener dp=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //dateAndTime.set(Calendar.YEAR, year);
            // dateAndTime.set(Calendar.MONTH, monthOfYear);
            // dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            // String textDateParam = year + "." + (monthOfYear + 1) + "." + dayOfMonth;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date(year-1900, monthOfYear,  dayOfMonth));
           // fromTextView.setText(date);
            toTextView.setText(date);
            // setInitialDateTime();
        }
    };
}
