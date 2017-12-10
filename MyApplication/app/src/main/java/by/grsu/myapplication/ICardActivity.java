package by.grsu.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ICardActivity extends AppCompatActivity {

    private EditText dateBox;
    private EditText sumBox;
    private Spinner spinner;
    private Button delButton;
    private Button saveButton;
    private Button chDate;

    Calendar dateAndTime=Calendar.getInstance();
    private DatabaseAdapter adapter;
    private int incId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icard);

        dateBox = (EditText) findViewById(R.id.editText3);
        sumBox = (EditText) findViewById(R.id.editText4);
        spinner = (Spinner) findViewById(R.id.spinner1);
        delButton = (Button) findViewById(R.id.button8);
        saveButton = (Button) findViewById(R.id.button7);
        chDate = (Button) findViewById(R.id.button14);
        setInitialDateTime();
        adapter = new DatabaseAdapter(this);

        String[] queryCols = new String[]{"_id", DatabaseHelper.COLUMN_CATEGORY_NAME};
        SQLiteDatabase database = new DatabaseHelper(this).getWritableDatabase();
        Cursor cursor = database.query(
                DatabaseHelper.TABLE_CATEGORY,     // Запрашиваемая таблица
                queryCols,                                // Возвращаемый столбец
                null,                                     // Столбец для условия WHERE
                null,                                     // Значение для условия WHERE
                null,                                     // не группировать строки
                null,                                     // не фильтровать по группам строк
                null                                      // не сортировать
        );

        String[] adapterCols = new String[]{DatabaseHelper.COLUMN_CATEGORY_NAME};
        int[] adapterRowViews = new int[]{android.R.id.text1};
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this, android.R.layout.simple_spinner_item, cursor, adapterCols, adapterRowViews, 0);
        cursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(cursorAdapter);
        adapter.close();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            incId = extras.getInt("id");
        }
        // если 0, то добавление
        if (incId > 0) {
            // получаем элемент по id из бд
            adapter.open();
            Income income = adapter.getIncome(incId);
            dateBox.setText(income.getIncomeDate());


            selectSpinnerItemByValue(spinner, income.getCategoryName());


            sumBox.setText(String.valueOf(income.getIncomeSum()));


        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
        }
    }

    public static void selectSpinnerItemByValue(Spinner spnr, String value) {
        SimpleCursorAdapter adapter = (SimpleCursorAdapter) spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if(adapter.getItem(position).equals(value)) {
                spnr.setSelection(position);
                return;
            }
        }
    }

    public void saveIncome(View view){

        String incomeDate = dateBox.getText().toString();

        Cursor colCur=(Cursor)spinner.getSelectedItem();
        String categoryName =colCur.getString(colCur.getColumnIndex(DatabaseHelper.COLUMN_NAME_CATEGORY));

        double incomeSum = Double.parseDouble(sumBox.getText().toString());
        Income income = new Income(incId, incomeDate, categoryName , incomeSum);

        adapter.open();
        if (incId > 0) {
            adapter.updateInc(income);
        } else {
            adapter.insertInc(income);
        }
        adapter.close();
       goHome();

    }
    public void deleteIncome(View view){

        adapter.open();
        adapter.deleteInc(incId);
        adapter.close();
       goHome();

    }

    // отображаем диалоговое окно для выбора даты
    public void chooseDate(View view){
        new DatePickerDialog(ICardActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка начальных даты и времени
    private void setInitialDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        dateBox.setText(date);

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
            dateBox.setText(date);
           // setInitialDateTime();
        }
    };

    private void goHome(){
        // переход к главной activity
        Intent intent = new Intent(this, IncomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}
