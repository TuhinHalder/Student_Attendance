package com.example.asimg.sams;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentDet extends AppCompatActivity {

    DatabaseReference databaseReference,reff,reff1;
    Spinner session,roll;
    EditText ID,Name,Email,DOB,Mobile,Password;
    Button update,cancel;
    DatabaseAddStudent databaseAddStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_det);

        session = findViewById(R.id.SsnSPNR);
        roll = findViewById(R.id.RollSPNR);
        ID = findViewById(R.id.IDET);
        Name = findViewById(R.id.NameET);
        Email = findViewById(R.id.EmailET);
        DOB = findViewById(R.id.DOBET);
        Mobile = findViewById(R.id.MobET);
        Password = findViewById(R.id.PassET);
        update = findViewById(R.id.UpdtBTN);
        cancel = findViewById(R.id.CnclBTN);
        databaseAddStudent = new DatabaseAddStudent();
        databaseReference = FirebaseDatabase.getInstance().getReference("Student");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> temp = dataSnapshot.getChildren();
                List<String> list1 = new ArrayList<>();
                for (DataSnapshot lists : temp){
                    list1.add(lists.getKey());
                }
                ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(StudentDet.this, android.R.layout.simple_spinner_item, list1);
                adapter4.setDropDownViewResource(android.R.layout.simple_spinner_item);
                session.setAdapter(adapter4);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        session.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String ssn = parent.getItemAtPosition(position).toString();
                reff = FirebaseDatabase.getInstance().getReference("Student").child(ssn);

                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> temp = dataSnapshot.getChildren();
                        List<String> list2 = new ArrayList<>();
                        for (DataSnapshot lists : temp){
                            list2.add(lists.getKey());
                        }
                        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(StudentDet.this, android.R.layout.simple_spinner_item, list2);
                        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        roll.setAdapter(adapter4);

                        roll.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String roll = parent.getItemAtPosition(position).toString();
                                reff = FirebaseDatabase.getInstance().getReference("Student").child(ssn).child(roll);

                                reff.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String id = dataSnapshot.child("id").getValue().toString();
                                        String nm = dataSnapshot.child("name").getValue().toString();
                                        String mail = dataSnapshot.child("email").getValue().toString();
                                        String dob = dataSnapshot.child("dob").getValue().toString();
                                        String mob = dataSnapshot.child("mobile").getValue().toString();
                                        String pass = dataSnapshot.child("pwd").getValue().toString();
                                        ID.setText(id);
                                        Name.setText(nm);
                                        Email.setText(mail);
                                        DOB.setText(dob);
                                        Mobile.setText(mob);
                                        Password.setText(pass);
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ID.getText().toString().trim().isEmpty()){
                    Toast.makeText(StudentDet.this,"Enter ID",Toast.LENGTH_LONG).show();
                }else if (Name.getText().toString().trim().isEmpty()){
                    Toast.makeText(StudentDet.this,"Enter Name",Toast.LENGTH_LONG).show();
                }else if (Email.getText().toString().trim().isEmpty()){
                    Toast.makeText(StudentDet.this,"Enter Email",Toast.LENGTH_LONG).show();
                }else if (isEmailValid(Email.getText().toString().trim())){
                    if (DOB.getText().toString().trim().isEmpty()){
                        Toast.makeText(StudentDet.this,"Enter DOB",Toast.LENGTH_LONG).show();
                    }else if (isValidDate(DOB.getText().toString().trim())){
                        if (Mobile.getText().toString().trim().isEmpty()){
                            Toast.makeText(StudentDet.this,"Enter Mobile",Toast.LENGTH_LONG).show();
                        }else if (Mobile.getText().toString().trim().length()==10){
                            if (isMobileValid(Mobile.getText().toString().trim())){
                                if (Password.getText().toString().trim().isEmpty()){
                                    Toast.makeText(StudentDet.this,"Enter Password",Toast.LENGTH_LONG).show();
                                }else {
                                    reff1 = FirebaseDatabase.getInstance().getReference("Student").child(session.getSelectedItem().toString());
                                    databaseAddStudent.setId(ID.getText().toString().trim());
                                    databaseAddStudent.setName(Name.getText().toString().trim());
                                    databaseAddStudent.setEmail(Email.getText().toString().trim());
                                    databaseAddStudent.setDob(DOB.getText().toString().trim());
                                    Long phn = Long.parseLong(Mobile.getText().toString().trim());
                                    databaseAddStudent.setMobile(phn);
                                    databaseAddStudent.setPwd(Password.getText().toString().trim());
                                    reff1.child(roll.getSelectedItem().toString()).setValue(databaseAddStudent);
                                    Toast.makeText(StudentDet.this,"Update Successfully",Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(StudentDet.this,"Mobile number is wrong",Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(StudentDet.this,"Mobile number should be 10 digit",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(StudentDet.this,"Follow dd/mm/yyyy format",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(StudentDet.this,"Email Foemat is incorrect",Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentDet.this,Admin.class);
                startActivity(intent);
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
        Intent intent = new Intent(StudentDet.this,Admin.class);
        startActivity(intent);
    }
}