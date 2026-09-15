package com.example.lab_5b_mobile;

import android.os.Bundle;
import android.graphics.Color;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lab_5b_mobile.databinding.ActivityMainBinding;
import com.example.lab_5b_mobile.databinding.ContentMainBinding;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_SELECTED_CLASS = "selected_class";
    private ActivityMainBinding binding;
    private ContentMainBinding contentBinding;
    private StudentAdapter studentAdapter;
    private final Map<String, Classroom> classrooms = new LinkedHashMap<>();
    private String selectedClassCode = "A01";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        contentBinding = ContentMainBinding.bind(binding.getRoot().findViewById(R.id.content_main));
        setSupportActionBar(binding.toolbar);

        seedClassrooms();
        setupDrawer();
        setupStudentList();

        if (savedInstanceState != null) {
            selectedClassCode = savedInstanceState.getString(STATE_SELECTED_CLASS, "A01");
        }
        showClassroom(selectedClassCode);
        binding.navigationView.setCheckedItem(
                "A02".equals(selectedClassCode) ? R.id.nav_class_a02 : R.id.nav_class_a01
        );
    }

    private void setupDrawer() {
        if (binding.getRoot() instanceof androidx.drawerlayout.widget.DrawerLayout) {
            androidx.drawerlayout.widget.DrawerLayout drawer = (androidx.drawerlayout.widget.DrawerLayout) binding.getRoot();
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this,
                    drawer,
                    binding.toolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close
            );
            toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
        }
        binding.navigationView.setNavigationItemSelectedListener(this::onClassSelected);
    }

    private void setupStudentList() {
        studentAdapter = new StudentAdapter();
        contentBinding.studentList.setLayoutManager(new LinearLayoutManager(this));
        contentBinding.studentList.setAdapter(studentAdapter);
        contentBinding.studentList.setHasFixedSize(true);
    }

    private boolean onClassSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_class_a01) {
            showClassroom("A01");
        } else if (item.getItemId() == R.id.nav_class_a02) {
            showClassroom("A02");
        } else {
            return false;
        }

        item.setChecked(true);
        if (binding.getRoot() instanceof androidx.drawerlayout.widget.DrawerLayout) {
            ((androidx.drawerlayout.widget.DrawerLayout) binding.getRoot()).closeDrawer(GravityCompat.START);
        }
        return true;
    }

    private void showClassroom(String classCode) {
        Classroom classroom = classrooms.get(classCode);
        if (classroom == null) {
            return;
        }

        selectedClassCode = classCode;
        binding.toolbar.setTitle(getString(R.string.class_title, classroom.getCode()));
        contentBinding.classCode.setText(classroom.getCode());
        contentBinding.homeroomTeacher.setText(classroom.getHomeroomTeacher());
        contentBinding.studentCount.setText(getResources().getQuantityString(
                R.plurals.student_count,
                classroom.getStudents().size(),
                classroom.getStudents().size()
        ));
        studentAdapter.submitList(classroom.getStudents());
    }

    private void seedClassrooms() {
        classrooms.put("A01", new Classroom(
                "A01", "Đặng Ngọc Hoàng Thành",
                Arrays.asList(
                        new Student("SV01", "Nguyễn Tấn Khiêm"),
                        new Student("SV02", "Nguyễn Phương Chinh"),
                        new Student("SV03", "Trương Thành Đạt")
                )
        ));

        classrooms.put("A02", new Classroom(
                "A02", "Nguyễn Quốc Hùng",
                Arrays.asList(
                        new Student("SV04", "Phan Khắc Anh Tuấn"),
                        new Student("SV05", "Lại Thành Đạt"),
                        new Student("SV06", "Nguyễn Thành Đạt"),
                        new Student("SV07", "Phan Nhựt Đăng Khoa")
                )
        ));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(STATE_SELECTED_CLASS, selectedClassCode);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (binding.getRoot() instanceof androidx.drawerlayout.widget.DrawerLayout && 
            ((androidx.drawerlayout.widget.DrawerLayout) binding.getRoot()).isDrawerOpen(GravityCompat.START)) {
            ((androidx.drawerlayout.widget.DrawerLayout) binding.getRoot()).closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private static final class Classroom {
        private final String code;
        private final String homeroomTeacher;
        private final List<Student> students;

        Classroom(String code, String homeroomTeacher, List<Student> students) {
            this.code = code;
            this.homeroomTeacher = homeroomTeacher;
            this.students = students;
        }

        String getCode() { return code; }
        String getHomeroomTeacher() { return homeroomTeacher; }
        List<Student> getStudents() { return students; }
    }
}
