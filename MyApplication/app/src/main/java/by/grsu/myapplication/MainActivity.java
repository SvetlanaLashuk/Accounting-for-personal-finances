package by.grsu.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openCategories(View view) {
        // Создаем объект Intent для вызова новой Activity
        Intent intent = new Intent(this, CategoryActivity.class);
        // запуск activity
        startActivity(intent);
    }

    public void openIncomes(View view) {
        // Создаем объект Intent для вызова новой Activity
        Intent intent = new Intent(this, IncomeActivity.class);
        // запуск activity
        startActivity(intent);
    }

    public void openOutlays(View view) {
        // Создаем объект Intent для вызова новой Activity
        Intent intent = new Intent(this, OutlayActivity.class);
        // запуск activity
        startActivity(intent);
    }

    public void openReport(View view){
        // Создаем объект Intent для вызова новой Activity
        Intent intent = new Intent(this, ReportActivity.class);
        // запуск activity
        startActivity(intent);
    }


}
