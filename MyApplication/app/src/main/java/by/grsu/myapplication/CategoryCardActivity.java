package by.grsu.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CategoryCardActivity extends AppCompatActivity {

    private EditText nameBox;
    private EditText commentBox;
    private Button delButton;
    private Button saveButton;

    private DatabaseAdapter adapter;
    private int categoryId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorycard);

        nameBox = (EditText) findViewById(R.id.editText);
        commentBox = (EditText) findViewById(R.id.editText2);
        delButton = (Button) findViewById(R.id.button13);
        saveButton = (Button) findViewById(R.id.button12);
        adapter = new DatabaseAdapter(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            categoryId = extras.getInt("id");
        }
        // если 0, то добавление
        if (categoryId > 0) {
            // получаем элемент по id из бд
            adapter.open();
            Category category = adapter.getCategory(categoryId);
            nameBox.setText(category.getName());
            commentBox.setText(category.getComment());
            adapter.close();
        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
        }
    }

    public void saveCategory(View view){

        String name = nameBox.getText().toString();
        String comment = commentBox.getText().toString();
        Category category = new Category(categoryId, name, comment);

        adapter.open();
        if (categoryId > 0) {
            adapter.updateCtg(category);
        } else {
            adapter.insertCtg(category);
        }
        adapter.close();
        goHome();

    }
    public void deleteCategory(View view){

        adapter.open();
        adapter.deleteCtg(categoryId);
        adapter.close();

       goHome();
    }
   private void goHome(){
        // переход к главной activity
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
