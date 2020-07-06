package com.example.asimg.sams;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Admin extends AppCompatActivity {

    private Button Logout;
    private Button Addfaculty,Addstudent;
    private Button Updatefaculty;
    private Button Studentdetails;
    private Button Newseason,Addadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Logout = (Button)findViewById(R.id.LgOutBTN);
        Addfaculty = (Button)findViewById(R.id.AddFacBTN);
        Updatefaculty = (Button)findViewById(R.id.UpdtFacBTN);
        Studentdetails = (Button)findViewById(R.id.StuDetBTN);
        Newseason = (Button)findViewById(R.id.NSBTN);
        Addstudent=findViewById(R.id.AddStuBTN);
        Addadmin = findViewById(R.id.AddAdminBTN);

        Updatefaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, UpdateFaculty.class);
                startActivity(intent);
            }
        });

        Addstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this,AddStudent.class);
                startActivity(intent);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this,LogIn.class);
                startActivity(intent);
            }
        });

        Addfaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this,AddFaculty.class);
                startActivity(intent);
            }
        });

        Newseason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, NewSession.class);
                startActivity(intent);
            }
        });

        Studentdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this,StudentDet.class);
                startActivity(intent);
            }
        });
        Addadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this,AddAdmin.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Admin.this,LogIn.class);
        startActivity(intent);
    }
}