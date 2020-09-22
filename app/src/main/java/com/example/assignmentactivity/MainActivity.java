package com.example.assignmentactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements StudentDetailAdapter.StudentClickListener {

    private DBHelper dbHelper;
    private RecyclerView mRcStudentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRcStudentList = findViewById(R.id.rc_student_data);

        mRcStudentList.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));

        dbHelper = new DBHelper(MainActivity.this);

        loadDataToDatabase();
    }

    private void loadDataToDatabase() {
        ArrayList<StudentDetail> details = dbHelper.getDataFromDatabase(dbHelper.getWritableDatabase());


        StudentDetailAdapter adapter = new StudentDetailAdapter(MainActivity.this, details);
        adapter.setListener(this);
        mRcStudentList.setAdapter(adapter);
    }


    public void onEnterListClicked(View view){
        startActivityForResult(new Intent(MainActivity.this, ViewActivity.class),1000);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            loadDataToDatabase();
        }
    }

    @Override
    public void onUpdateClicked(StudentDetail studentDetail) {
        Intent updateIntent = new Intent(MainActivity.this, ViewActivity.class);
        updateIntent.putExtra("Student", studentDetail);
        updateIntent.putExtra("is_update", true);
        startActivityForResult(updateIntent, 1000);
    }

    @Override
    public void onDeleteClicked(StudentDetail studentDetail) {
        dbHelper.deleteDataFromDatabase(dbHelper.getWritableDatabase(), studentDetail);
        loadDataToDatabase();
    }
}