package by.grsu.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class OutlayActivity extends AppCompatActivity {

    private ListView outlayList;
    ArrayAdapter<Outlay> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlay);

        outlayList = (ListView)findViewById(R.id.list3);

        outlayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Outlay outlay =arrayAdapter.getItem(position);
                if(outlay!=null) {
                    Intent intent = new Intent(getApplicationContext(), OutlayCardActivity.class);
                    intent.putExtra("id", outlay.getId());
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

        List<Outlay> outlays = adapter.getOutlays();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, outlays);
        outlayList.setAdapter(arrayAdapter);
        adapter.close();
    }

    public void addOutlay(View view) {
        // Создаем объект Intent для вызова новой Activity
        Intent intent = new Intent(this, OutlayCardActivity.class);
        // запуск activity
        startActivity(intent);
    }
}
