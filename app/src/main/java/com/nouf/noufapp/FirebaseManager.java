package com.nouf.noufapp;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class FirebaseManager {

    private static FirebaseManager instance;
    private final DatabaseReference databaseReference;

    private FirebaseManager(){
     databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseManager getInstance(){
        if (instance == null){
            instance = new FirebaseManager();
        }

        return instance;
    }

    public void getSpecificStudent(String id,ResponseListener listener){
        databaseReference.child("Students").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<StudentObject> list = new ArrayList<>();
                if (snapshot.hasChildren()){
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        StudentObject item = postSnapshot.getValue(StudentObject.class);
                        if (item.getStudentId().equals(id)){
                            list.add(item);
                            break;
                        }

                    }
                    listener.onSuccess("success",list);
                }
                else{
                    listener.onSuccess("success",list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailed("failed");
            }
        });
    }

    public void getAllStudent(ResponseListener listener){
        databaseReference.child("Students").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<StudentObject> list = new ArrayList<>();
                if (snapshot.hasChildren()){
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                      list.add(postSnapshot.getValue(StudentObject.class));
                    }
                    listener.onSuccess("success",list);
                }
                else{
                    listener.onSuccess("success",list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
              listener.onFailed("failed");
            }
        });
    }

    public void addNewStudent(StudentObject studentObject,ResponseListener listener){
        String randomId = databaseReference.push().getKey();
        studentObject.setId(randomId);
        databaseReference.child("Students").child(studentObject.getId()).setValue(studentObject)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        listener.onSuccess("success",null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        listener.onFailed("failed");
                    }
                });
    }

    public void updateStudent(StudentObject studentObject,ResponseListener listener){
        Map<String, Object> postValues = studentObject.toMap();
        databaseReference.child("Students").child(studentObject.getId()).updateChildren(postValues)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        listener.onSuccess("success",null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        listener.onFailed("failed");
                    }
                });
    }

    public void deleteStudent(StudentObject studentObject,ResponseListener listener){
        databaseReference.child("Students").child(studentObject.getId()).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        listener.onSuccess("success",null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        listener.onFailed("failed");
                    }
                });
    }


}
