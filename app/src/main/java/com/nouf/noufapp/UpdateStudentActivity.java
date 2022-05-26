package com.nouf.noufapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class UpdateStudentActivity extends AppCompatActivity {

    private Context context;
    private Toolbar toolbar;
    private MaterialButton updateBtn;
    private TextInputEditText studentIdBox, studentNameBox, studentSurnameBox, studentFatherNameBox, studentNationalIdBox, studentDateBirthBox, studentGenderBox;
    private StudentObject studentObject;
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        initViews();
        setUpToolbar();

    }

    private void initViews() {
        context = this;
        toolbar = findViewById(R.id.toolbar);
        updateBtn = findViewById(R.id.update_btn);
        studentIdBox = findViewById(R.id.up_student_id_input_box);
        studentNameBox = findViewById(R.id.up_student_name_input_box);
        studentSurnameBox = findViewById(R.id.up_student_surname_input_box);
        studentFatherNameBox = findViewById(R.id.up_student_father_name_input_box);
        studentNationalIdBox = findViewById(R.id.up_student_national_id_input_box);
        studentDateBirthBox = findViewById(R.id.up_student_date_birth_input_box);
        studentGenderBox = findViewById(R.id.up_student_gender_input_box);

        if (getIntent() != null && getIntent().hasExtra("STUDENT")){
            studentObject = (StudentObject) getIntent().getSerializableExtra("STUDENT");
        }

        if (getIntent() != null && getIntent().hasExtra("TYPE")){
            type = getIntent().getStringExtra("TYPE");
        }

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()) {

                    studentObject.setStudentId(studentIdBox.getText().toString().trim());
                    studentObject.setStudentName(studentNameBox.getText().toString().trim());
                    studentObject.setStudentSurName(studentSurnameBox.getText().toString().trim());
                    studentObject.setStudentFatherName(studentFatherNameBox.getText().toString().trim());
                    studentObject.setStudentNationalId(studentNationalIdBox.getText().toString().trim());
                    studentObject.setStudentDateOfBirth(studentDateBirthBox.getText().toString().trim());
                    studentObject.setStudentGender(studentGenderBox.getText().toString().trim());
                    if (type.equals("firebase")) {
                        updateBtn.setText("Please wait...");
                        FirebaseManager.getInstance().updateStudent(studentObject, new ResponseListener() {
                            @Override
                            public void onSuccess(String response, ArrayList<StudentObject> list) {
                                updateBtn.setText("Update");
                                Toasty.success(context, "Student details has been updated!", Toast.LENGTH_SHORT, true).show();
                            }

                            @Override
                            public void onFailed(String error) {
                                updateBtn.setText("Update");
                                Toasty.error(context, "Something went wrong, try later!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        updateBtn.setText("Please wait...");
                        long result = SQLiteDatabaseManager.getInstance(context).updateStudent(studentObject);
                        if (result > 0){
                            updateBtn.setText("Update");
                            Toasty.success(context, "Student details has been updated!", Toast.LENGTH_SHORT, true).show();
                        }
                        else{
                            updateBtn.setText("Update");
                            Toasty.error(context, "Something went wrong, try later!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        studentIdBox.setText(studentObject.getStudentId());
        studentNameBox.setText(studentObject.getStudentName());
        studentSurnameBox.setText(studentObject.getStudentSurName());
        studentFatherNameBox.setText(studentObject.getStudentFatherName());
        studentNationalIdBox.setText(studentObject.getStudentNationalId());
        studentDateBirthBox.setText(studentObject.getStudentDateOfBirth());
        studentGenderBox.setText(studentObject.getStudentGender());
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);
            getSupportActionBar().setTitle("Update Student Details");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    private boolean validation() {

        if (TextUtils.isEmpty(studentIdBox.getText().toString())) {
            Toasty.error(context, "Student ID is required!", Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (TextUtils.isEmpty(studentNameBox.getText().toString())) {
            Toasty.error(context, "Student Name is required!", Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (TextUtils.isEmpty(studentSurnameBox.getText().toString())) {
            Toasty.error(context, "Student Surname is required!", Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (TextUtils.isEmpty(studentFatherNameBox.getText().toString())) {
            Toasty.error(context, "Student Father's Name is required!", Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (TextUtils.isEmpty(studentNationalIdBox.getText().toString())) {
            Toasty.error(context, "Student National ID is required!", Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (TextUtils.isEmpty(studentDateBirthBox.getText().toString())) {
            Toasty.error(context, "Student Date of Birth is required!", Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (TextUtils.isEmpty(studentGenderBox.getText().toString())) {
            Toasty.error(context, "Student Gender is required!", Toast.LENGTH_SHORT, true).show();
            return false;
        }

        return true;
    }
}