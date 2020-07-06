package com.example.asimg.sams;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Student extends AppCompatActivity {
    Button gattn,logout,showattn,profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        final Intent intent = getIntent();

        gattn = findViewById(R.id.GAttnBTN);
        logout = findViewById(R.id.LgOutBTN);
        showattn = findViewById(R.id.ShwAtndncBTN);
        profile = findViewById(R.id.PrflBTN);

        gattn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new  Intent(Student.this,GiveAttendance.class);
                intent1.putExtra("roll",intent.getStringExtra("roll"));
                intent1.putExtra("session",intent.getStringExtra("session"));
                startActivity(intent1);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Student.this,StudentLog.class);
                startActivity(intent1);
            }
        });

        showattn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Student.this,ShowAttendance.class);
                intent1.putExtra("roll",intent.getStringExtra("roll"));
                intent1.putExtra("session",intent.getStringExtra("session"));
                startActivity(intent1);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Student.this,StudentProfile.class);
                intent1.putExtra("roll",intent.getStringExtra("roll"));
                intent1.putExtra("session",intent.getStringExtra("session"));
                startActivity(intent1);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent1 = new Intent(Student.this,StudentLog.class);
        startActivity(intent1);
    }
}