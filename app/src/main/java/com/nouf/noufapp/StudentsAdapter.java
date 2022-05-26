package com.nouf.noufapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.ItemViewHolder> {


    private Context context;
    private ArrayList<StudentObject> studentObjectList;
    private OnItemClickListener listener;


    interface OnItemClickListener{
        void onItemClick(int position);
        void onItemEditClick(int position);
        void onItemDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        listener = onItemClickListener;
    }

    public StudentsAdapter(Context context, ArrayList<StudentObject> studentObjectList) {
        this.context = context;
        this.studentObjectList = studentObjectList;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder{

        MaterialTextView studentIdView,studentNameView,studentSurnameView,studentFatherNameView,studentNationalIdView,studentDateOfBirthView,studentGenderView;
        AppCompatImageView editImageView,deleteImageView;
        public ItemViewHolder(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);
            studentIdView = itemView.findViewById(R.id.stu_item_id_view);
            studentNameView = itemView.findViewById(R.id.stu_item_name_view);
            studentSurnameView = itemView.findViewById(R.id.stu_item_surname_view);
            studentFatherNameView = itemView.findViewById(R.id.stu_item_father_name_view);
            studentNationalIdView = itemView.findViewById(R.id.stu_item_national_id_view);
            studentDateOfBirthView = itemView.findViewById(R.id.stu_item_date_birth_view);
            studentGenderView = itemView.findViewById(R.id.stu_item_gender_view);
            editImageView = itemView.findViewById(R.id.stu_item_edit_view);
            deleteImageView = itemView.findViewById(R.id.stu_item_delete_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(getLayoutPosition());
                }
            });

            editImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemEditClick(getLayoutPosition());
                }
            });

            deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemDeleteClick(getLayoutPosition());
                }
            });
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item_row_design,parent,false);
        return new ItemViewHolder(layout,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
         StudentObject item = studentObjectList.get(position);
         holder.studentIdView.setText(item.getStudentId());
        holder.studentNameView.setText(item.getStudentName());
        holder.studentSurnameView.setText(item.getStudentSurName());
        holder.studentFatherNameView.setText(item.getStudentFatherName());
        holder.studentNationalIdView.setText(item.getStudentNationalId());
        holder.studentDateOfBirthView.setText(item.getStudentDateOfBirth());
        holder.studentGenderView.setText(item.getStudentGender());
    }

    @Override
    public int getItemCount() {
        return studentObjectList.size();
    }



}
