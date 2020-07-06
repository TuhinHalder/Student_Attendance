package com.example.asimg.sams;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddStudent extends AppCompatActivity {
    Spinner ssnspnr;
    Button next,cancel;
    DatabaseReference reff,reff1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        ssnspnr = findViewById(R.id.SsnSPNR);
        next = findViewById(R.id.AtnBTN);
        cancel = findViewById(R.id.CnclBTN);
        reff = FirebaseDatabase.getInstance().getReference("Newsession");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> temp = dataSnapshot.getChildren();
                if (dataSnapshot.getChildrenCount()==0){
                    Toast.makeText(AddStudent.this,"Please create new session",Toast.LENGTH_LONG).show();
                }else {
                    List<String> list = new ArrayList<>();
                    for (DataSnapshot lists : temp) {
                        list.add(lists.getKey());
                    }
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(AddStudent.this, android.R.layout.simple_spinner_item, list);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    ssnspnr.setAdapter(adapter1);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check();
                final String itemvalue = ssnspnr.getSelectedItem().toString();
                Intent intent = new Intent(AddStudent.this,AddStudentDetails.class);
                intent.putExtra("session",itemvalue);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddStudent.this,Admin.class);
                startActivity(intent);
            }
        });
    }
    public void check(){
        final String itemvalue = ssnspnr.getSelectedItem().toString();
        reff1 = FirebaseDatabase.getInstance().getReference("Student").child(itemvalue);
        reff1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long s=0;
                s = dataSnapshot.getChildrenCount();
                if (s==0){
                    Intent intent = new Intent(AddStudent.this,AddStudentDetails.class);
                    intent.putExtra("session",itemvalue);
                    startActivity(intent);
                }else {
                    Toast.makeText(AddStudent.this,"In this session student has already added",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(AddStudent.this,Admin.class);
        startActivity(intent);
    }
}