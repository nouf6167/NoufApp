package com.nouf.noufapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class SQLiteDatabaseActivity extends AppCompatActivity implements StudentsAdapter.OnItemClickListener {

    private Context context;
    private Toolbar toolbar;
    private FloatingActionButton addFabBtn;
    private RecyclerView studentsRecyclerview;
    private WrapContentLinearLayoutManager linearLayoutManager;
    private ArrayList<StudentObject> studentObjectList;
    private ArrayList<StudentObject> originalStudentObjectList;
    private StudentsAdapter adapter;
    private TextInputEditText searchBox;
    private MaterialButton searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_q_lite_database);

        initViews();
        setUpToolbar();

    }

    void initViews() {
        context = this;
        studentObjectList = new ArrayList<>();
        originalStudentObjectList = new ArrayList<>();
        toolbar = findViewById(R.id.toolbar);
        addFabBtn = findViewById(R.id.add_sqlite_student_fab);
        searchBox = findViewById(R.id.search_student_id_box);
        searchBtn = findViewById(R.id.search_student_btn);
        studentsRecyclerview = findViewById(R.id.sqlite_database_students_recyclerview);
        linearLayoutManager = new WrapContentLinearLayoutManager(context);
        studentsRecyclerview.setLayoutManager(linearLayoutManager);
        studentsRecyclerview.hasFixedSize();
        adapter = new StudentsAdapter(context, studentObjectList);
        studentsRecyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!searchBox.getText().toString().trim().isEmpty()) {
                    search(searchBox.getText().toString().trim());
                }
            }
        });

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    studentObjectList.clear();
                    studentObjectList.addAll(originalStudentObjectList);
                    adapter.notifyItemRangeChanged(0, studentObjectList.size());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        addFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,AddStudentActivity.class);
                intent.putExtra("TYPE","sqlite");
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseManager.getInstance().getAllStudent(new ResponseListener() {
            @Override
            public void onSuccess(String response, ArrayList<StudentObject> records) {
                if (records.size() > 0){
                    for (int i=0;i<records.size();i++){
                        StudentObject item = records.get(i);
                        long result = SQLiteDatabaseManager.getInstance(context).addNewStudent(item);
                        Log.d("TEST199RETURN", String.valueOf(result));
                    }

                    if (SQLiteDatabaseManager.getInstance(context).getAllStudent().size() > 0){
                        studentObjectList.clear();
                        originalStudentObjectList.clear();
                    }
                    originalStudentObjectList.addAll(SQLiteDatabaseManager.getInstance(context).getAllStudent());
                    studentObjectList.addAll(originalStudentObjectList);
                    adapter.notifyItemRangeChanged(0,studentObjectList.size());
                }
                else{
                    if (SQLiteDatabaseManager.getInstance(context).getAllStudent().size() > 0){
                        studentObjectList.clear();
                        originalStudentObjectList.clear();
                    }
                    originalStudentObjectList.addAll(SQLiteDatabaseManager.getInstance(context).getAllStudent());
                    studentObjectList.addAll(originalStudentObjectList);
                    adapter.notifyItemRangeChanged(0,studentObjectList.size());
                }
            }

            @Override
            public void onFailed(String error) {
                if (SQLiteDatabaseManager.getInstance(context).getAllStudent().size() > 0){
                    studentObjectList.clear();
                    originalStudentObjectList.clear();
                }
                originalStudentObjectList.addAll(SQLiteDatabaseManager.getInstance(context).getAllStudent());
                studentObjectList.addAll(originalStudentObjectList);
                adapter.notifyItemRangeChanged(0,studentObjectList.size());
            }
        });
    }

    private void search(String query) {
        ArrayList<StudentObject> matchedStudents = new ArrayList<>();

        if (studentObjectList.size() > 0) {
            for (int i = 0; i < studentObjectList.size(); i++) {
                StudentObject item = studentObjectList.get(i);
                if (item.getStudentId().equals(query)) {
                    matchedStudents.add(item);
                }
            }
        }
        if (matchedStudents.size() == 0) {
            Toasty.error(context, "No Student found against search ID", Toast.LENGTH_SHORT).show();
        } else {
            studentObjectList.clear();
            studentObjectList.addAll(matchedStudents);
            adapter.notifyItemRangeChanged(0, studentObjectList.size());
        }

    }

    void setUpToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);
            getSupportActionBar().setTitle("SQLite Database Students");
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

    @Override
    public void onItemClick(int position) {
        StudentObject item = studentObjectList.get(position);
        Toasty.success(context,item.getStudentName()+" "+item.getStudentSurName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemEditClick(int position) {
        StudentObject item = studentObjectList.get(position);
        //Toasty.success(context,item.getStudentName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, UpdateStudentActivity.class);
        intent.putExtra("STUDENT", item);
        intent.putExtra("TYPE", "sqlite");
        startActivity(intent);
    }

    @Override
    public void onItemDeleteClick(int position) {
        StudentObject item = studentObjectList.get(position);
        //Toasty.success(context,item.getStudentName(), Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to delete?");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                long result = SQLiteDatabaseManager.getInstance(context).deleteStudent(item.getStudentId());
                if (result>0){
                    Toasty.success(context, "Student delete successfully!", Toast.LENGTH_SHORT).show();
                    studentObjectList.remove(position);
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toasty.error(context, "Something went wrong, try later!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}