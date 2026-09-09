package com.example.lab_5b_mobile;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_5b_mobile.databinding.ItemStudentBinding;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private final List<Student> students = new ArrayList<>();

    public void submitList(List<Student> newStudents) {
        students.clear();
        students.addAll(newStudents);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStudentBinding binding = ItemStudentBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new StudentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.bind(students.get(position));
    }

    @Override
    public int getItemCount() { return students.size(); }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        private final ItemStudentBinding binding;

        StudentViewHolder(ItemStudentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Student student) {
            binding.studentId.setText(student.getId());
            binding.studentName.setText(student.getFullName());
        }
    }
}
