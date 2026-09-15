package com.example.lab_5b_mobile;

import android.os.Bundle;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_SELECTED_CLASS = "selected_class";
    private final Map<String, Classroom> classrooms = new LinkedHashMap<>();
    private String selectedClassCode = "A01";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;
    
    private TextView classCodeText;
    private TextView homeroomTeacherText;
    private TextView studentCountText;
    private LinearLayout studentListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
        
        classCodeText = findViewById(R.id.class_code);
        homeroomTeacherText = findViewById(R.id.homeroom_teacher);
        studentCountText = findViewById(R.id.student_count);
        studentListContainer = findViewById(R.id.student_list);

        setSupportActionBar(toolbar);

        seedClassrooms();
        setupDrawer();

        if (savedInstanceState != null) {
            selectedClassCode = savedInstanceState.getString(STATE_SELECTED_CLASS, "A01");
        }
        showClassroom(selectedClassCode);
        
        if (navigationView != null) {
            navigationView.setCheckedItem(
                    "A02".equals(selectedClassCode) ? R.id.nav_class_a02 : R.id.nav_class_a01
            );
        }
    }

    private void setupDrawer() {
        if (drawerLayout != null) {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this,
                    drawerLayout,
                    toolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close
            );
            toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
        }
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this::onClassSelected);
        }
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
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    private void showClassroom(String classCode) {
        Classroom classroom = classrooms.get(classCode);
        if (classroom == null) {
            return;
        }

        selectedClassCode = classCode;
        if (toolbar != null) {
            toolbar.setTitle(getString(R.string.class_title, classroom.getCode()));
        }
        
        if (classCodeText != null) classCodeText.setText(classroom.getCode());
        if (homeroomTeacherText != null) homeroomTeacherText.setText(classroom.getHomeroomTeacher());
        if (studentCountText != null) {
            studentCountText.setText(getResources().getQuantityString(
                    R.plurals.student_count,
                    classroom.getStudents().size(),
                    classroom.getStudents().size()
            ));
        }

        populateStudentList(classroom.getStudents());
    }

    private void populateStudentList(List<Student> students) {
        if (studentListContainer == null) return;
        
        studentListContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        
        for (Student student : students) {
            View studentView = inflater.inflate(R.layout.item_student, studentListContainer, false);
            TextView idText = studentView.findViewById(R.id.student_id);
            TextView nameText = studentView.findViewById(R.id.student_name);
            
            if (idText != null) idText.setText(student.getId());
            if (nameText != null) nameText.setText(student.getFullName());
            
            studentListContainer.addView(studentView);
        }
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
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
