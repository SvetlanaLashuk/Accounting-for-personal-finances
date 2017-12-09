package by.grsu.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import java.util.Calendar;

public class OCardActivity extends AppCompatActivity {

    private EditText dateoBox;
    private EditText sumoBox;
    private Spinner spinner;
    private Button deleteO;
    private Button saveO;

    Calendar dateAndTime=Calendar.getInstance();
    private DatabaseAdapter adapter;
    private int outId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocard);

        dateoBox = (EditText) findViewById(R.id.editText5);
        sumoBox = (EditText) findViewById(R.id.editText6);
        spinner = (Spinner) findViewById(R.id.spinner2);
        deleteO = (Button) findViewById(R.id.button11);
        saveO = (Button) findViewById(R.id.button10);
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
            outId = extras.getInt("id");
        }
        // если 0, то добавление
        if (outId > 0) {
            // получаем элемент по id из бд
            adapter.open();
            Outlay outlay = adapter.getOutlay(outId);
            dateoBox.setText(outlay.getOutlayDate());
            selectSpinnerItemByValue(spinner, outId);
            sumoBox.setText(String.valueOf(outlay.getOutlaySum()));

            //spinner.setAdapter(income.getCategoryName());
            //incomesourceBox.setText(income.getIncsname());
            // spinner.getSelectedItem().toString();
            //spinner.setSelection(income.getCategoryName());

        } else {
            // скрываем кнопку удаления
            deleteO.setVisibility(View.GONE);
        }
    }

    public static void selectSpinnerItemByValue(Spinner spnr, long value) {
        SimpleCursorAdapter adapter = (SimpleCursorAdapter) spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if(adapter.getItemId(position) == value) {
                spnr.setSelection(position);
                return;
            }
        }
    }

    public void saveOutlay(View view){

        String outlayDate = dateoBox.getText().toString();

        Cursor colCur=(Cursor)spinner.getSelectedItem();
        String categoryName = colCur.getString(colCur.getColumnIndex(DatabaseHelper.COLUMN_TYPE_CATEGORY));

        double outlaySum = Double.parseDouble(sumoBox.getText().toString());
        Outlay outlay = new Outlay(outId, outlayDate, categoryName , outlaySum);

        adapter.open();
        if (outId > 0) {
            adapter.updateOut(outlay);
        } else {
            adapter.insertOut(outlay);
        }
        adapter.close();
        goHome();

    }
    public void deleteOutlay(View view){

        adapter.open();
        adapter.deleteOut(outId);
        adapter.close();
        goHome();

    }

    // отображаем диалоговое окно для выбора даты
    public void chooseOutlayDate(View view){
        new DatePickerDialog(OCardActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка начальных даты и времени
    private void setInitialDateTime() {

        dateoBox.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_NUMERIC_DATE ));
        //| DateUtils.FORMAT_SHOW_TIME));


    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String textDateParam = year + "." + (monthOfYear + 1) + "." + dayOfMonth;
            dateoBox.setText(textDateParam);
            // setInitialDateTime();
        }
    };

    private void goHome(){
        // переход к главной activity
        Intent intent = new Intent(this, OutlayActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
