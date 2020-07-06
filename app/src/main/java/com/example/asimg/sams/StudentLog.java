package com.example.asimg.sams;

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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StudentLog extends AppCompatActivity {

    private Button cancel,submit;
    private EditText Pass;
    private Spinner course,batch,semester,roll;
    DatabaseReference databaseReference,reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_log);

        submit = (Button)findViewById(R.id.SbmtBTN);
        cancel = (Button)findViewById(R.id.CnclBTN);
        roll = findViewById(R.id.RollSPNR);
        Pass = findViewById(R.id.PassET);
        course = findViewById(R.id.CrsSPNR);
        batch = findViewById(R.id.BatchSPNR);
        semester = findViewById(R.id.SemSPNR);

        final List<String> list1= new ArrayList<>();
        list1.add("Select course");
        list1.add("BCA");
        list1.add("MCA");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
        course.setAdapter(adapter1);

        course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String itemvalue1 = parent.getItemAtPosition(position).toString();

                if (itemvalue1.equals("MCA")){
                    List<String> list2 = new ArrayList<>();
                    list2.add("Saltlake");
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(StudentLog.this,android.R.layout.simple_spinner_item,list2);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    batch.setAdapter(adapter2);

                    batch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            final String itemvalue2 = parent.getItemAtPosition(position).toString();

                            if (itemvalue2.equals("Saltlake")) {
                                List<String> list3 = new ArrayList<>();
                                list3.add("Select semester");
                                list3.add("Sem1");
                                list3.add("Sem2");
                                list3.add("Sem3");
                                list3.add("Sem4");
                                list3.add("Sem5");
                                list3.add("Sem6");
                                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(StudentLog.this, android.R.layout.simple_spinner_item, list3);
                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                semester.setAdapter(adapter3);

                                semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String itemvalue3 = parent.getItemAtPosition(position).toString();

                                        if (itemvalue3.equals("Select semester")){
                                            List<String> list4 = new ArrayList<>();
                                            ArrayAdapter<String> adapter4 = new ArrayAdapter<>(StudentLog.this,android.R.layout.simple_spinner_item,list4);
                                            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                            roll.setAdapter(adapter4);
                                        }else {
                                            int cyear = Calendar.getInstance().get(Calendar.YEAR);
                                            int eyear = cyear+3;
                                            String ssn = cyear+"-"+eyear;
                                            String s = itemvalue1 + itemvalue2 + itemvalue3 + ssn;
                                            databaseReference = FirebaseDatabase.getInstance().getReference("Student").child(s);
                                            databaseReference.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    Iterable<DataSnapshot> temp = dataSnapshot.getChildren();
                                                    //Toast.makeText(StudentLog.this,dataSnapshot.getKey(),Toast.LENGTH_LONG).show();
                                                    List<String> list4 = new ArrayList<>();
                                                    list4.add("Select roll");
                                                    for (DataSnapshot lists : temp){
                                                        list4.add(lists.getKey());
                                                    }
                                                    ArrayAdapter<String> adapter4 = new ArrayAdapter<>(StudentLog.this,android.R.layout.simple_spinner_item,list4);
                                                    adapter4.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                                    roll.setAdapter(adapter4);
                                                }
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                }
                                            });
                                        }
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                            }else{
                                List<String> list3 = new ArrayList<>();
                                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(StudentLog.this, android.R.layout.simple_spinner_item,list3);
                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                semester.setAdapter(adapter3);
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else if (itemvalue1.equals("BCA")){
                    List<String> list2 = new ArrayList<>();
                    list2.add("Select batch");
                    list2.add("Saltlake-A");
                    list2.add("Saltlake-B");
                    list2.add("Kolkata-1");
                    list2.add("Kolkata-2");
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(StudentLog.this,android.R.layout.simple_spinner_item, list2);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    batch.setAdapter(adapter2);

                    batch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            final String itemvalue2 = parent.getItemAtPosition(position).toString();

                            if (itemvalue2.equals("Saltlake-A") || itemvalue2.equals("Saltlake-B") || itemvalue2.equals("Kolkata-1") || itemvalue2.equals("Kolkata-2")) {
                                List<String> list3 = new ArrayList<>();
                                list3.add("Select semester");
                                list3.add("Sem1");
                                list3.add("Sem2");
                                list3.add("Sem3");
                                list3.add("Sem4");
                                list3.add("Sem5");
                                list3.add("Sem6");
                                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(StudentLog.this, android.R.layout.simple_spinner_item, list3);
                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                semester.setAdapter(adapter3);

                                semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String itemvalue3 = parent.getItemAtPosition(position).toString();

                                        if (itemvalue3.equals("Select semester")){
                                            List<String> list4 = new ArrayList<>();
                                            ArrayAdapter<String> adapter4 = new ArrayAdapter<>(StudentLog.this,android.R.layout.simple_spinner_item,list4);
                                            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                            roll.setAdapter(adapter4);
                                        }else {
                                            int cyear = Calendar.getInstance().get(Calendar.YEAR);
                                            int eyear = cyear+3;
                                            String ssn = cyear+"-"+eyear;
                                            String s = itemvalue1 + itemvalue2 + itemvalue3 + ssn;
                                            databaseReference = FirebaseDatabase.getInstance().getReference("Student").child(s);
                                            databaseReference.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    Iterable<DataSnapshot> temp = dataSnapshot.getChildren();
                                                    //Toast.makeText(StudentLog.this,dataSnapshot.getKey(),Toast.LENGTH_LONG).show();
                                                    List<String> list4 = new ArrayList<>();
                                                    list4.add("Select roll");
                                                    for (DataSnapshot lists : temp){
                                                        list4.add(lists.getKey());
                                                    }
                                                    ArrayAdapter<String> adapter4 = new ArrayAdapter<>(StudentLog.this,android.R.layout.simple_spinner_item,list4);
                                                    adapter4.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                                    roll.setAdapter(adapter4);
                                                }
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                }
                                            });
                                        }
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                            }else {
                                List<String> list3 = new ArrayList<>();
                                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(StudentLog.this, android.R.layout.simple_spinner_item, list3);
                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                semester.setAdapter(adapter3);
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }else {
                    List<String> list2 = new ArrayList<>();
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(StudentLog.this, android.R.layout.simple_spinner_item,list2);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    batch.setAdapter(adapter2);

                    List<String> list3 = new ArrayList<>();
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(StudentLog.this, android.R.layout.simple_spinner_item, list3);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    semester.setAdapter(adapter3);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (course.getSelectedItem().equals("Select course")){
                    Toast.makeText(StudentLog.this,"Select Course",Toast.LENGTH_LONG).show();
                }else if (batch.getSelectedItem().equals("Select batch")){
                    Toast.makeText(StudentLog.this,"Select batch",Toast.LENGTH_LONG).show();
                }else if (semester.getSelectedItem().equals("Select semester")){
                    Toast.makeText(StudentLog.this,"Select semester",Toast.LENGTH_LONG).show();
                }else if (roll.getSelectedItem().equals("Select roll")){
                    Toast.makeText(StudentLog.this,"Select roll",Toast.LENGTH_LONG).show();
                }else if (Pass.getText().toString().isEmpty()){
                    Toast.makeText(StudentLog.this,"Enter password",Toast.LENGTH_LONG).show();
                }else {
                    final String crs = course.getSelectedItem().toString();
                    final String btch = batch.getSelectedItem().toString();
                    final String sem = semester.getSelectedItem().toString();
                    final String rl = roll.getSelectedItem().toString();
                    final String pass = Pass.getText().toString().trim();
                    int cyear = Calendar.getInstance().get(Calendar.YEAR);
                    int eyear = cyear+3;
                    final String ssn = cyear+"-"+eyear;
                    reff = FirebaseDatabase.getInstance().getReference("Student").child(crs+btch+sem+ssn);
                    reff.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int i = 0;
                            Iterable<DataSnapshot> temp = dataSnapshot.getChildren();
                            for (DataSnapshot lists : temp){
                                if (lists.child("pwd").getValue().toString().equals(pass)){
                                    i = 1;
                                    break;
                                }
                            }
                            if (i==1){
                                Intent intent = new Intent(StudentLog.this,Student.class);
                                intent.putExtra("session",crs+btch+sem+ssn);
                                intent.putExtra("roll",rl);
                                startActivity(intent);
                            }else {
                                Toast.makeText(StudentLog.this,"Login Failed",Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentLog.this,LogIn.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(StudentLog.this,LogIn.class);
        startActivity(intent);
    }
}