package com.example.a20240807listviewtest;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener  {

    private ListView cityListView;
    private List<String> cities =new ArrayList<String>();
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
        cityListView = (ListView) findViewById(R.id.cityListView);
        setupCities();
        cityListView.setOnItemClickListener(this);

    }

    public void setupCities() {
        String[] citiesArray = getResources().getStringArray(R.array.cities);
        cities.add("Hualien");
        cities.add("Yilan");
        cities.add("TaiTong");
        cities.add("ChangHua");
        cities.add("NanTou");
        cities.add("MiaoLi");
        //new ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cities);
        cityListView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView tv =(TextView)findViewById(R.id.textView);
//        String[]citiesArray = getResources().getStringArray(R.array.cities);

        AlertDialog.Builder dialog =new AlertDialog.Builder(this);
        dialog.setTitle("城市");
        dialog.setMessage("你點選的是"+cities.get(i)).setCancelable(true);
        dialog.setCancelable(true);
        dialog.setPositiveButton("確定",null);
        dialog.setNeutralButton("取消",null);
        dialog.setNegativeButton("離開",null);
        dialog.show();



//        Toast.makeText(this,"你點選的是 " + citiesArray[i], Toast.LENGTH_SHORT).show();
//        tv.setText("你點選的是"+citiesArray[i]);

    }
}