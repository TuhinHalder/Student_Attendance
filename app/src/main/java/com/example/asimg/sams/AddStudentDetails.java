package com.example.asimg.sams;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddStudentDetails extends AppCompatActivity {
    EditText id,roll,dob,name,email,mobile;
    Button nxtcntn,svsbmt;
    private DatePickerDialog.OnDateSetListener DSListener;
    DatabaseReference reff,reff1;
    DatabaseAddStudent databaseAddStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_details);

        id = findViewById(R.id.IDET);
        roll = findViewById(R.id.RollET);
        name = findViewById(R.id.nmET);
        dob = findViewById(R.id.DOBET);
        email = findViewById(R.id.emailET);
        mobile = findViewById(R.id.mobET);
        nxtcntn = findViewById(R.id.NXTCntnBTN);
        svsbmt = findViewById(R.id.SvSbmtBTN);
        databaseAddStudent = new DatabaseAddStudent();
        Intent intent = getIntent();
        final String ssn = intent.getStringExtra("session");
        reff = FirebaseDatabase.getInstance().getReference("Student");
        reff1 = FirebaseDatabase.getInstance().getReference("Student").child(ssn);

        nxtcntn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.getText().toString().trim().isEmpty()){
                    Toast.makeText(AddStudentDetails.this,"Enter ID",Toast.LENGTH_LONG).show();
                }else if (roll.getText().toString().trim().isEmpty()){
                    Toast.makeText(AddStudentDetails.this,"Enter Roll Number",Toast.LENGTH_LONG).show();
                }else if (name.getText().toString().trim().isEmpty()){
                    Toast.makeText(AddStudentDetails.this,"Enter Name",Toast.LENGTH_LONG).show();
                }else if (dob.getText().toString().trim().isEmpty()){
                    Toast.makeText(AddStudentDetails.this,"Enter Date of Birth",Toast.LENGTH_LONG).show();
                }else if (isValidDate(dob.getText().toString().trim())){
                    if (email.getText().toString().trim().isEmpty()){
                        Toast.makeText(AddStudentDetails.this,"Enter Email",Toast.LENGTH_LONG).show();
                    }else if (isEmailValid(email.getText().toString().trim())){
                        if (mobile.getText().toString().trim().isEmpty()){
                            Toast.makeText(AddStudentDetails.this,"Enter Mobile",Toast.LENGTH_LONG).show();
                        }else if (isMobileValid(mobile.getText().toString().trim())){
                            if (mobile.length()>10 || mobile.length()<10){
                                Toast.makeText(AddStudentDetails.this,"Phone number should be 10 digit",Toast.LENGTH_LONG).show();
                            }else {

                                reff1.addValueEventListener(new ValueEventListener() {
                                    private int i = 0;
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Iterable<DataSnapshot> temp = dataSnapshot.getChildren();
                                        for (DataSnapshot lists : temp){
                                            if (lists.getKey().equals(roll.getText().toString().trim())){
                                                i=1;
                                                break;
                                            }
                                        }
                                        if (i==1){
                                            Toast.makeText(AddStudentDetails.this,roll.getText().toString().trim()+" already exist",Toast.LENGTH_LONG).show();
                                        }else {
                                            databaseAddStudent.setId(id.getText().toString().trim());
                                            Long r = Long.parseLong(id.getText().toString().trim());
                                            databaseAddStudent.setRoll(r);
                                            databaseAddStudent.setName(name.getText().toString().trim());
                                            databaseAddStudent.setDob(dob.getText().toString().trim());
                                            databaseAddStudent.setEmail(email.getText().toString().trim());
                                            Long ph = Long.parseLong(mobile.getText().toString().trim());
                                            databaseAddStudent.setMobile(ph);
                                            databaseAddStudent.setPwd(mobile.getText().toString().trim());
                                            reff1.child(roll.getText().toString().trim()).setValue(databaseAddStudent);
                                            Toast.makeText(AddStudentDetails.this,name.getText().toString().trim()+" added",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                            }
                        }else {
                            Toast.makeText(AddStudentDetails.this,"This mobile is incorrect",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(AddStudentDetails.this,"This email format is incorrect",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(AddStudentDetails.this,"Follow mm/dd/yyyy",Toast.LENGTH_LONG).show();
                }
            }
        });

        svsbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.getText().toString().trim().isEmpty()){
                    Toast.makeText(AddStudentDetails.this,"Enter ID",Toast.LENGTH_LONG).show();
                }else if (roll.getText().toString().trim().isEmpty()){
                    Toast.makeText(AddStudentDetails.this,"Enter Roll Number",Toast.LENGTH_LONG).show();
                }else if (name.getText().toString().trim().isEmpty()){
                    Toast.makeText(AddStudentDetails.this,"Enter Name",Toast.LENGTH_LONG).show();
                }else if (dob.getText().toString().trim().isEmpty()){
                    Toast.makeText(AddStudentDetails.this,"Enter Date of Birth",Toast.LENGTH_LONG).show();
                }else if (isValidDate(dob.getText().toString().trim())){
                    if (email.getText().toString().trim().isEmpty()){
                        Toast.makeText(AddStudentDetails.this,"Enter Email",Toast.LENGTH_LONG).show();
                    }else if (isEmailValid(email.getText().toString().trim())){
                        if (mobile.getText().toString().trim().isEmpty()){
                            Toast.makeText(AddStudentDetails.this,"Enter Mobile",Toast.LENGTH_LONG).show();
                        }else if (isMobileValid(mobile.getText().toString().trim())){
                            if (mobile.length()>10 || mobile.length()<10){
                                Toast.makeText(AddStudentDetails.this,"Phone number should be 10 digit",Toast.LENGTH_LONG).show();
                            }else {

                                reff1.addValueEventListener(new ValueEventListener() {
                                    private int i = 0;
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Iterable<DataSnapshot> temp = dataSnapshot.getChildren();
                                        for (DataSnapshot lists : temp){
                                            if (lists.getKey().equals(roll.getText().toString().trim())){
                                                i=1;
                                                break;
                                            }
                                        }
                                        if (i==1){
                                            Toast.makeText(AddStudentDetails.this,roll.getText().toString().trim()+" already exist",Toast.LENGTH_LONG).show();
                                        }else {
                                            databaseAddStudent.setId(id.getText().toString().trim());
                                            Long r = Long.parseLong(roll.getText().toString().trim());
                                            databaseAddStudent.setRoll(r);
                                            databaseAddStudent.setName(name.getText().toString().trim());
                                            databaseAddStudent.setDob(dob.getText().toString().trim());
                                            databaseAddStudent.setEmail(email.getText().toString().trim());
                                            Long ph = Long.parseLong(mobile.getText().toString().trim());
                                            databaseAddStudent.setMobile(ph);
                                            databaseAddStudent.setPwd(mobile.getText().toString().trim());
                                            reff1.child(roll.getText().toString().trim()).setValue(databaseAddStudent);
                                            Toast.makeText(AddStudentDetails.this,name.getText().toString().trim()+" added",Toast.LENGTH_LONG).show();
                                            Intent intent1 = new Intent(AddStudentDetails.this,Admin.class);
                                            startActivity(intent1);
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                            }
                        }else {
                            Toast.makeText(AddStudentDetails.this,"This mobile is incorrect",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(AddStudentDetails.this,"This email format is incorrect",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(AddStudentDetails.this,"Follow mm/dd/yyyy",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    public static boolean isMobileValid(String s) {
        Pattern p = Pattern.compile("(0/91)?[6-9][0-9]{9}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }
    public static boolean isValidDate(String d) {
        String regex = "^(1[0-2]|0[1-9])/(3[01]"
                + "|[12][0-9]|0[1-9])/[0-9]{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher((CharSequence)d);
        return matcher.matches();
    }

    @Override
    public void onBackPressed(){
        Intent intent2 = new Intent(AddStudentDetails.this,AddStudent.class);
        startActivity(intent2);
    }
}