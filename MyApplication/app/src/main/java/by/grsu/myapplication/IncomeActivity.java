package by.grsu.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class IncomeActivity extends AppCompatActivity {

    private ListView incomeList;
    ArrayAdapter<Income> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        incomeList = (ListView)findViewById(R.id.list2);

        incomeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Income income =arrayAdapter.getItem(position);
                if(income!=null) {
                    Intent intent = new Intent(getApplicationContext(), IncomeCardActivity.class);
                    intent.putExtra("id", income.getId());
                    intent.putExtra("click", 25);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();

        List<Income> incomes = adapter.getIncomes();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, incomes);
        incomeList.setAdapter(arrayAdapter);
        adapter.close();
    }

    public void addIncome(View view) {
        // Создаем объект Intent для вызова новой Activity
        Intent intent = new Intent(this, IncomeCardActivity.class);
        // запуск activity
        startActivity(intent);
    }
}
