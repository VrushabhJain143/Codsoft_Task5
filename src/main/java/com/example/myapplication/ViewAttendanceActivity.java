package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

public class ViewAttendanceActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        databaseHelper = new DatabaseHelper(this);

        // Set up the ListView and populate with attendance records
        ListView listView = findViewById(R.id.listViewAttendance);

        // Retrieve attendance records from the database
        Cursor cursor = databaseHelper.getAllAttendance();

        // Define the columns to be displayed in the list view
        String[] fromColumns = {DatabaseHelper.COLUMN_STUDENT_NAME, DatabaseHelper.COLUMN_DATE};

        // Define the view elements that will display the data
        int[] toViews = {R.id.textStudentName, R.id.textAttendanceDate};

        // Create a SimpleCursorAdapter to populate the list view
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.list_item_attendance,
                cursor,
                fromColumns,
                toViews,
                0
        );

        // Set the adapter for the list view
        listView.setAdapter(adapter);

        // Clear attendance records immediately
        clearAttendance();
    }

    private void clearAttendance() {
        // Clear attendance records from the database
        databaseHelper.clearAttendance();
    }
}
