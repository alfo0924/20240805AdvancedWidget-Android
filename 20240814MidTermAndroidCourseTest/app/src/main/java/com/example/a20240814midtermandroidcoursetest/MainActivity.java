package com.example.a20240814midtermandroidcoursetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> itemList;
    private ArrayAdapter<String> adapter;
    private Button addButton;
    private Button deleteButton;
    private ArrayList<Integer> selectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.listView);
        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);

        itemList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, itemList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        selectedItems = new ArrayList<>();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddList_Interface.class);
                startActivityForResult(intent, 1);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedItems();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectedItems.contains(position)) {
                    selectedItems.remove(Integer.valueOf(position));
                } else {
                    selectedItems.add(position);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String name = data.getStringExtra("name");
                String quantity = data.getStringExtra("quantity");
                String imageName = data.getStringExtra("imageName");
                String note = data.getStringExtra("note");

                String item = "名稱: " + name + ", 數量: " + quantity + ", 圖片: " + imageName + ", 備註: " + note;
                itemList.add(item);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "儲存成功", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void deleteSelectedItems() {
        if (selectedItems.isEmpty()) {
            Toast.makeText(this, "請選擇要刪除的項目", Toast.LENGTH_SHORT).show();
            return;
        }

        // 從大到小排序,以避免刪除時索引變化
        selectedItems.sort((a, b) -> b.compareTo(a));

        for (int position : selectedItems) {
            if (position < itemList.size()) {
                itemList.remove(position);
            }
        }

        adapter.notifyDataSetChanged();
        listView.clearChoices(); // 清除所有選擇
        selectedItems.clear();
        Toast.makeText(this, "已刪除選中項目", Toast.LENGTH_SHORT).show();
    }
}