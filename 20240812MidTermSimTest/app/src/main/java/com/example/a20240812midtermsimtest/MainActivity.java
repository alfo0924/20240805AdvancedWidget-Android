package com.example.a20240812midtermsimtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_TODO_REQUEST = 1;
    private ArrayList<String> todoList;
    private ArrayAdapter<String> adapter;
    private Set<Integer> selectedItems;

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

        todoList = new ArrayList<>();
        selectedItems = new HashSet<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, todoList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = view.findViewById(android.R.id.text1);
                TextView text2 = view.findViewById(android.R.id.text2);

                String[] parts = todoList.get(position).split(":", 2);
                text1.setText(parts[0].trim());
                text2.setText(parts.length > 1 ? parts[1].trim() : "");

                if (selectedItems.contains(position)) {
                    view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                } else {
                    view.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }

                return view;
            }
        };

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectedItems.contains(position)) {
                    selectedItems.remove(position);
                } else {
                    selectedItems.add(position);
                }
                adapter.notifyDataSetChanged();
            }
        });

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, toDoList.class);
                startActivityForResult(intent, ADD_TODO_REQUEST);
            }
        });

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedItems();
            }
        });
    }

    private void deleteSelectedItems() {
        ArrayList<String> newTodoList = new ArrayList<>();
        for (int i = 0; i < todoList.size(); i++) {
            if (!selectedItems.contains(i)) {
                newTodoList.add(todoList.get(i));
            }
        }
        todoList.clear();
        todoList.addAll(newTodoList);
        selectedItems.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TODO_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String content = data.getStringExtra("content");
            todoList.add(title + ": " + content);
            adapter.notifyDataSetChanged();
        }
    }
}