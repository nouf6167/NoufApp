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

public class AddStudentActivity extends AppCompatActivity {

    private Context context;
    private Toolbar toolbar;
    private MaterialButton saveBtn;
    private TextInputEditText studentIdBox, studentNameBox, studentSurnameBox, studentFatherNameBox, studentNationalIdBox, studentDateBirthBox, studentGenderBox;
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        initViews();
        setUpToolbar();

    }


    private void initViews() {
        context = this;
        toolbar = findViewById(R.id.toolbar);
        saveBtn = findViewById(R.id.save_btn);
        studentIdBox = findViewById(R.id.student_id_input_box);
        studentNameBox = findViewById(R.id.student_name_input_box);
        studentSurnameBox = findViewById(R.id.student_surname_input_box);
        studentFatherNameBox = findViewById(R.id.student_father_name_input_box);
        studentNationalIdBox = findViewById(R.id.student_national_id_input_box);
        studentDateBirthBox = findViewById(R.id.student_date_birth_input_box);
        studentGenderBox = findViewById(R.id.student_gender_input_box);

        if (getIntent() != null && getIntent().hasExtra("TYPE")){
            type = getIntent().getStringExtra("TYPE");
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()) {
                    StudentObject studentObject = new StudentObject();
                    studentObject.setStudentId(studentIdBox.getText().toString().trim());
                    studentObject.setStudentName(studentNameBox.getText().toString().trim());
                    studentObject.setStudentSurName(studentSurnameBox.getText().toString().trim());
                    studentObject.setStudentFatherName(studentFatherNameBox.getText().toString().trim());
                    studentObject.setStudentNationalId(studentNationalIdBox.getText().toString().trim());
                    studentObject.setStudentDateOfBirth(studentDateBirthBox.getText().toString().trim());
                    studentObject.setStudentGender(studentGenderBox.getText().toString().trim());

                    if (type.equals("firebase")) {
                        saveBtn.setText("Please wait...");
                        FirebaseManager.getInstance().addNewStudent(studentObject, new ResponseListener() {
                            @Override
                            public void onSuccess(String response, ArrayList<StudentObject> list) {
                                saveBtn.setText("Save");
                                Toasty.success(context, "New Student has been added!", Toast.LENGTH_SHORT, true).show();
                                resetInputBoxes();
                            }

                            @Override
                            public void onFailed(String error) {
                                saveBtn.setText("Save");
                            }
                        });
                    }
                    else{
                        saveBtn.setText("Please wait...");
                        long result = SQLiteDatabaseManager.getInstance(context).addNewStudent(studentObject);
                        if (result > 0){
                            saveBtn.setText("Save");
                            Toasty.success(context, "New Student has been added!", Toast.LENGTH_SHORT, true).show();
                            resetInputBoxes();
                        }
                        else{
                            saveBtn.setText("Save");
                            Toasty.error(context, "Same Student ID record already exist in database!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);
            getSupportActionBar().setTitle("Add new Student");
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

    private void resetInputBoxes() {
        studentIdBox.setText("");
        studentNameBox.setText("");
        studentSurnameBox.setText("");
        studentFatherNameBox.setText("");
        studentNationalIdBox.setText("");
        studentDateBirthBox.setText("");
        studentGenderBox.setText("");
        studentIdBox.requestFocus();
        studentIdBox.setSelection(0);
    }
}