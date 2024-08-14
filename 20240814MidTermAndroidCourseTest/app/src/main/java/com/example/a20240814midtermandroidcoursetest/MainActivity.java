package com.example.a20240814midtermandroidcoursetest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<ListItem> itemList;
    private CustomAdapter adapter;
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
        adapter = new CustomAdapter(this, itemList);
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
                adapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteConfirmDialog(position);
                return true;
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
                itemList.add(new ListItem(item));
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

        selectedItems.sort((a, b) -> b.compareTo(a));

        for (int position : selectedItems) {
            if (position < itemList.size()) {
                itemList.remove(position);
            }
        }

        adapter.notifyDataSetChanged();
        selectedItems.clear();
        Toast.makeText(this, "已刪除選中項目", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteConfirmDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("確定刪除?");
        builder.setMessage("確定要刪除這個清單?");
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemList.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "已刪除清單", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private class ListItem {
        String text;
        int imageResource;

        ListItem(String text) {
            this.text = text;
            this.imageResource = getRandomImageResource();
        }

        private int getRandomImageResource() {
            int[] imageResources = {
                    R.drawable.image1, R.drawable.image2, R.drawable.image3,R.drawable.image4, R.drawable.image5, R.drawable.image6,R.drawable.image7, R.drawable.image8, R.drawable.image9
                    // 添加更多圖片資源ID
            };
            return imageResources[new Random().nextInt(imageResources.length)];
        }
    }

    private class CustomAdapter extends ArrayAdapter<ListItem> {
        public CustomAdapter(Context context, ArrayList<ListItem> items) {
            super(context, R.layout.list_item, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }

            ImageView imageView = convertView.findViewById(R.id.itemImage);
            TextView textView = convertView.findViewById(R.id.itemText);

            ListItem item = getItem(position);
            textView.setText(item.text);
            imageView.setImageResource(item.imageResource);

            // 設置選中狀態
            convertView.setBackgroundColor(selectedItems.contains(position) ?
                    getContext().getResources().getColor(android.R.color.holo_blue_light) :
                    getContext().getResources().getColor(android.R.color.transparent));

            return convertView;
        }
    }
}