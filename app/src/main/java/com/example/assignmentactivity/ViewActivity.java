package com.example.assignmentactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ViewActivity extends AppCompatActivity {


    private EditText mEtStudentName;
    private EditText mEtStudentEmail;
    private EditText mEtStudentContact;
    private EditText mEtStudentYear;
    private EditText mEtStudentCourse;

    private DBHelper dbHelper;

    private boolean isUpdate;
    private int studentID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);


        mEtStudentName = findViewById(R.id.et_student_name);
        mEtStudentContact = findViewById(R.id.et_student_phone);
        mEtStudentEmail = findViewById(R.id.et_student_email);
        mEtStudentYear = findViewById(R.id.et_student_year);
        mEtStudentCourse = findViewById(R.id.et_student_course);

        Button mBtnEnterData = findViewById(R.id.btn_enter_student);

        Bundle data = getIntent().getExtras();

        if (data != null) {
            isUpdate = data.getBoolean("is_update");

            StudentDetail studentDetail = (StudentDetail) data.getSerializable("Student");

            mEtStudentName.setText(studentDetail.getStudentName());
            mEtStudentCourse.setText(studentDetail.getStudentCourse());
            mEtStudentYear.setText(studentDetail.getStudentYear());
            mEtStudentEmail.setText(studentDetail.getStudentEmail());
            mEtStudentContact.setText(studentDetail.getStudentContact());

            studentID = studentDetail.getStudentID();

            mBtnEnterData.setText("Update Student");

        }

        dbHelper = new DBHelper(ViewActivity.this);
    }

    public void onEnterClicked(View view) {

        String studentName = mEtStudentName.getText().toString();
        String studentContact = mEtStudentContact.getText().toString();
        String studentEmail = mEtStudentEmail.getText().toString();
        String studentYear = mEtStudentYear.getText().toString();
        String studentCourse = mEtStudentCourse.getText().toString();

        StudentDetail newStudent = new StudentDetail();
//        newStudent.studentName = studentName;
        newStudent.setStudentName(studentName);
        newStudent.setStudentEmail(studentEmail);
        newStudent.setStudentContact(studentContact);
        newStudent.setStudentYear(studentYear);
        newStudent.setStudentCourse(studentCourse);


        mEtStudentName.setText("");
        mEtStudentCourse.setText("");
        mEtStudentEmail.setText("");
        mEtStudentYear.setText("");
        mEtStudentContact.setText("");

        if (isUpdate) {
            newStudent.setStudentID(studentID);
            dbHelper.updateDataToDatabase(dbHelper.getWritableDatabase(), newStudent);

            setResult(Activity.RESULT_OK);
            finish();
        } else {
            dbHelper.insertDataToDatabase(dbHelper.getWritableDatabase(), newStudent);
        }

    }

    public void onViewStudentClicked(View view) {
        setResult(Activity.RESULT_OK,new Intent(ViewActivity.this,MainActivity.class));
        finish();

    }

}