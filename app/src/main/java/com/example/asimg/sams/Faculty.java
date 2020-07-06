package com.example.asimg.sams;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.Calendar;
import java.util.List;

public class Faculty extends AppCompatActivity {

    private Button attendance;
    private Button View;
    private Spinner Stream, Sem,batch, Sub;
    private Button Logout;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);

        attendance = (Button)findViewById(R.id.AtnBTN);
        View = (Button)findViewById(R.id.CnclBTN);
        Stream = (Spinner)findViewById(R.id.CrsSPNR);
        batch = findViewById(R.id.BatchSPNR);
        Sem = findViewById(R.id.SemSPNR);
        Sub = (Spinner)findViewById(R.id.SubSPNR);
        Logout = (Button)findViewById(R.id.LgOutBTN);
        databaseReference = FirebaseDatabase.getInstance().getReference("Newsession");

        Intent intent = getIntent();
        String user = intent.getStringExtra("user");

        final List<String> list1= new ArrayList<>();
        list1.add("Select course");
        list1.add("BCA");
        list1.add("MCA");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
        Stream.setAdapter(adapter1);

        Stream.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String itemvalue1 = parent.getItemAtPosition(position).toString();

                if (itemvalue1.equals("MCA")){
                    List<String> list2 = new ArrayList<>();
                    list2.add("Saltlake");
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Faculty.this,android.R.layout.simple_spinner_item,list2);
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
                                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(Faculty.this, android.R.layout.simple_spinner_item, list3);
                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                Sem.setAdapter(adapter3);

                                Sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                                        final String itemvalue3 = parent.getItemAtPosition(position).toString();

                                        if (itemvalue3.equals("Select semester")){

                                        }else {
                                            databaseReference.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    int cyear = Calendar.getInstance().get(Calendar.YEAR);
                                                    int eyear = cyear+3;
                                                    String session = cyear+"-"+eyear;
                                                    if (dataSnapshot.child(itemvalue1+itemvalue2+itemvalue3+session).exists()){
                                                        Iterable<DataSnapshot> temp = dataSnapshot.child(itemvalue1+itemvalue2+itemvalue3+session).child("Subjects").getChildren();
                                                        List<String> list4 = new ArrayList<>();
                                                        list4.add("Select subcode");
                                                        for (DataSnapshot lists : temp){
                                                            list4.add(lists.getKey());
                                                        }
                                                        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(Faculty.this, android.R.layout.simple_spinner_item, list4);
                                                        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                                        Sub.setAdapter(adapter4);
                                                    }
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
                                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(Faculty.this, android.R.layout.simple_spinner_item,list3);
                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                Sem.setAdapter(adapter3);
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
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Faculty.this,android.R.layout.simple_spinner_item, list2);
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
                                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(Faculty.this, android.R.layout.simple_spinner_item, list3);
                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                Sem.setAdapter(adapter3);

                                Sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                                        final String itemvalue3 = parent.getItemAtPosition(position).toString();

                                        if (itemvalue3.equals("Select semester")){
                                            List<String> list4 = new ArrayList<>();
                                            ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(Faculty.this, android.R.layout.simple_spinner_item, list4);
                                            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                            Sub.setAdapter(adapter4);
                                        }else {
                                            databaseReference.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    int cyear = Calendar.getInstance().get(Calendar.YEAR);
                                                    int eyear = cyear+3;
                                                    String session = cyear+"-"+eyear;
                                                    if (dataSnapshot.child(itemvalue1+itemvalue2+itemvalue3+session).exists()){
                                                        Iterable<DataSnapshot> temp = dataSnapshot.child(itemvalue1+itemvalue2+itemvalue3+session).child("Subjects").getChildren();
                                                        List<String> list4 = new ArrayList<>();
                                                        list4.add("Select subcode");
                                                        for (DataSnapshot lists : temp){
                                                            list4.add(lists.getKey());
                                                        }
                                                        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(Faculty.this, android.R.layout.simple_spinner_item, list4);
                                                        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                                        Sub.setAdapter(adapter4);
                                                    }else {
                                                        List<String> list4 = new ArrayList<>();
                                                        list4.add("Select Subcode");
                                                        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(Faculty.this, android.R.layout.simple_spinner_item, list4);
                                                        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                                        Sub.setAdapter(adapter4);
                                                    }
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
                                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(Faculty.this, android.R.layout.simple_spinner_item, list3);
                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                Sem.setAdapter(adapter3);
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }else {
                    List<String> list2 = new ArrayList<>();
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Faculty.this, android.R.layout.simple_spinner_item,list2);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    batch.setAdapter(adapter2);

                    List<String> list3 = new ArrayList<>();
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(Faculty.this, android.R.layout.simple_spinner_item, list3);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    Sem.setAdapter(adapter3);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Stream.getSelectedItem().toString().equals("Select course")) {
                    Toast.makeText(Faculty.this, "Select course", Toast.LENGTH_LONG).show();
                }else if (batch.getSelectedItem().toString().equals("Select batch")) {
                    Toast.makeText(Faculty.this, "Select batch", Toast.LENGTH_LONG).show();
                }else if (Sem.getSelectedItem().toString().equals("Select semester")) {
                    Toast.makeText(Faculty.this, "Select semester", Toast.LENGTH_LONG).show();
                }else if (Sub.getSelectedItem().toString().equals("Select subcode")){
                    Toast.makeText(Faculty.this,"Select subcode",Toast.LENGTH_LONG).show();
                }else if (Sub.getSelectedItem().toString().equals("Select Subcode")){
                    Toast.makeText(Faculty.this,"This session is not created",Toast.LENGTH_LONG).show();
                }else {
                    Intent intent1 = new Intent(Faculty.this,Attendance.class);
                    intent1.putExtra("stream",Stream.getSelectedItem().toString());
                    intent1.putExtra("batch",batch.getSelectedItem().toString());
                    intent1.putExtra("sem",Sem.getSelectedItem().toString());
                    intent1.putExtra("sub",Sub.getSelectedItem().toString());
                    startActivity(intent1);
                }
            }
        });

        View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Stream.getSelectedItem().toString().equals("Select course")) {
                    Toast.makeText(Faculty.this, "Select course", Toast.LENGTH_LONG).show();
                }else if (batch.getSelectedItem().toString().equals("Select batch")) {
                    Toast.makeText(Faculty.this, "Select batch", Toast.LENGTH_LONG).show();
                }else if (Sem.getSelectedItem().toString().equals("Select semester")) {
                    Toast.makeText(Faculty.this, "Select semester", Toast.LENGTH_LONG).show();
                }else if (Sub.getSelectedItem().toString().equals("Select subcode")){
                    Toast.makeText(Faculty.this,"Select subcode",Toast.LENGTH_LONG).show();
                }else if (Sub.getSelectedItem().toString().equals("Select Subcode")){
                    Toast.makeText(Faculty.this,"This session is not created",Toast.LENGTH_LONG).show();
                }else {
                    Intent intent1 = new Intent(Faculty.this, Preview.class);
                    intent1.putExtra("stream", Stream.getSelectedItem().toString());
                    intent1.putExtra("batch", batch.getSelectedItem().toString());
                    intent1.putExtra("sem", Sem.getSelectedItem().toString());
                    intent1.putExtra("sub", Sub.getSelectedItem().toString());
                    startActivity(intent1);
                }
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Faculty.this,LogIn.class);
                startActivity(intent1);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent1 = new Intent(Faculty.this,LogIn.class);
        startActivity(intent1);
    }
}