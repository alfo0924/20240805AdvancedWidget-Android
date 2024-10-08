package com.example.a20240814midtermandroidcoursetest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddList_Interface extends AppCompatActivity {

    private EditText nameEditText, quantityEditText, imageNameEditText, noteEditText;
    private Button saveButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_list_interface);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameEditText = findViewById(R.id.nameEditText);
        quantityEditText = findViewById(R.id.quantityEditText);
        imageNameEditText = findViewById(R.id.imageNameEditText);
        noteEditText = findViewById(R.id.noteEditText);
        saveButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasInputData()) {
                    showBackConfirmDialog();
                } else {
                    finish();
                }
            }
        });
    }

    private boolean hasInputData() {
        return !TextUtils.isEmpty(nameEditText.getText()) ||
                !TextUtils.isEmpty(quantityEditText.getText()) ||
                !TextUtils.isEmpty(imageNameEditText.getText()) ||
                !TextUtils.isEmpty(noteEditText.getText());
    }

    private void showConfirmDialog() {
        String name = nameEditText.getText().toString();
        String quantity = quantityEditText.getText().toString();
        String imageName = imageNameEditText.getText().toString();
        String note = noteEditText.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("確定新增?");
        builder.setMessage("確定內容並新增?\n名稱: " + name + "\n數量: " + quantity + "\n圖片: " + imageName + "\n備註: " + note);
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("name", name);
                resultIntent.putExtra("quantity", quantity);
                resultIntent.putExtra("imageName", imageName);
                resultIntent.putExtra("note", note);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private void showBackConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("確定要返回?");
        builder.setMessage("確定返回後所有資料會消失並返回主頁面");
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }
}