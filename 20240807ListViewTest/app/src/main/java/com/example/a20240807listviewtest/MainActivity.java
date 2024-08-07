package com.example.a20240807listviewtest;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView cityListView;
    private ArrayList<City> cities = new ArrayList<>();
    private TextView selectedCityTextView;

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
        cityListView = findViewById(R.id.cityListView);
        selectedCityTextView = findViewById(R.id.textView);
        setupCities();
        cityListView.setOnItemClickListener(this);
    }

    public void setupCities() {
        cities.add(new City("Taipei", 100));
        cities.add(new City("New Taipei", 220));
        cities.add(new City("Taoyuan", 330));
        cities.add(new City("Hsinchu", 300));
        cities.add(new City("Taichung", 400));
        cities.add(new City("Kaohsiung", 800));
        cities.add(new City("Hualien", 970));
        cities.add(new City("Yilan", 260));

        CityAdapter adapter = new CityAdapter(this, cities);
        cityListView.setAdapter(adapter);
    }

    private class CityAdapter extends ArrayAdapter<City> {
        public CityAdapter(Context context, ArrayList<City> cities) {
            super(context, 0, cities);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            City city = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.city_information, parent, false);
            }
            TextView tvName = convertView.findViewById(R.id.cityName);
            TextView tvZipCode = convertView.findViewById(R.id.cityZipCode);
            if (city != null) {
                tvName.setText(city.name);
                tvZipCode.setText(String.valueOf(city.zipCode));
            }
            return convertView;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        City selectedCity = cities.get(position);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("城市");
        dialog.setMessage("你點選的是" + selectedCity.name + " (郵遞區號: " + selectedCity.zipCode + ")");
        dialog.setCancelable(true);
        dialog.setPositiveButton("確定", null);
        dialog.setNeutralButton("取消", null);
        dialog.setNegativeButton("離開", null);
        dialog.show();

        Toast.makeText(this, "你點選的是 " + selectedCity.name, Toast.LENGTH_SHORT).show();

        selectedCityTextView.setText("Selected: " + selectedCity.name + " (" + selectedCity.zipCode + ")");
    }

    private static class City {
        String name;
        int zipCode;

        City(String name, int zipCode) {
            this.name = name;
            this.zipCode = zipCode;
        }
    }
}
