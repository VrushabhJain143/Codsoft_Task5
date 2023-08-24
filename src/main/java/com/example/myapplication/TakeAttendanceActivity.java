package com.example.myapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TakeAttendanceActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);

        databaseHelper = new DatabaseHelper(this);

        LinearLayout layoutStudents = findViewById(R.id.layoutStudents);
        Button btnSaveAttendance = findViewById(R.id.btnSaveAttendance);

        String[] studentNames = { "Ram","Student 1", "Student 2", "Student 3", "Student 4", "Student 5", "Student 6", "Student 7", "Student 8"};
        for (String studentName : studentNames) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(studentName);
            layoutStudents.addView(checkBox);
        }

        btnSaveAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                List<String> selectedStudents = new ArrayList<>();

                LinearLayout layout = findViewById(R.id.layoutStudents);
                for (int i = 0; i < layout.getChildCount(); i++) {
                    View child = layout.getChildAt(i);
                    if (child instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) child;
                        if (checkBox.isChecked()) {
                            selectedStudents.add(checkBox.getText().toString());
                        }
                    }
                }

                for (String student : selectedStudents) {
                    ContentValues values = new ContentValues();
                    values.put(DatabaseHelper.COLUMN_STUDENT_NAME, student);
                    values.put(DatabaseHelper.COLUMN_DATE, getCurrentDate());
                    db.insert(DatabaseHelper.TABLE_ATTENDANCE, null, values);
                }

                db.close();
                Toast.makeText(TakeAttendanceActivity.this, "Attendance saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(new Date());
    }
}
