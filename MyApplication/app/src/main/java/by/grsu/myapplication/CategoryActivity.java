package by.grsu.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private ListView categoryList;
    ArrayAdapter<Category> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        categoryList = (ListView)findViewById(R.id.list1);

        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category =arrayAdapter.getItem(position);
                if(category!=null) {
                    Intent intent = new Intent(getApplicationContext(), CategoryCardActivity.class);
                    intent.putExtra("id", category.getId());
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

        List<Category> categories = adapter.getCategories();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories);
        categoryList.setAdapter(arrayAdapter);
        adapter.close();
    }

    public void addCategory(View view) {
        // Создаем объект Intent для вызова новой Activity
        Intent intent = new Intent(this, CategoryCardActivity.class);
        // запуск activity
        startActivity(intent);
    }

}
