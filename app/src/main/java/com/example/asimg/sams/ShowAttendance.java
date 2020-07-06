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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowAttendance extends AppCompatActivity {

    private TextView classes,attandance;
    private Spinner subcode;
    private Button ok;
    DatabaseReference databaseReference,reff,reff1,reff2;
    int t,atn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendance);

        final Intent intent = getIntent();
        ok = findViewById(R.id.OKBTN);
        subcode = findViewById(R.id.SubCodeSPNR);
        classes = findViewById(R.id.TtlTV);
        attandance = findViewById(R.id.AtnTV);
        databaseReference = FirebaseDatabase.getInstance().getReference("Newsession").child(intent.getStringExtra("session")).child("Subjects");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> temp = dataSnapshot.getChildren();
                List<String> list = new ArrayList<>();
                for (DataSnapshot lists : temp){
                    list.add(lists.getKey());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ShowAttendance.this,android.R.layout.simple_spinner_item,list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                subcode.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        subcode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String sc = parent.getItemAtPosition(position).toString();
                reff = FirebaseDatabase.getInstance().getReference("Attendance").child(intent.getStringExtra("session"));
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> temp1 = dataSnapshot.getChildren();
                        for (DataSnapshot lists1 : temp1){
                            if (lists1.getKey().equals(sc)){
                                reff1 = FirebaseDatabase.getInstance().getReference("Attendance").child(intent.getStringExtra("session")).child(sc);
                                reff1.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Iterable<DataSnapshot> temp2 = dataSnapshot.getChildren();
                                        t=0;
                                        for (DataSnapshot lists2 : temp2){
                                            t++;
                                            reff2 = FirebaseDatabase.getInstance().getReference("Attendance").child(intent.getStringExtra("session")).child(sc).child(lists2.getKey());
                                            reff2.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    Iterable<DataSnapshot> temp3 = dataSnapshot.getChildren();
                                                    atn=0;
                                                    for (DataSnapshot lists3 : temp3){
                                                        if (intent.getStringExtra("roll").equals(lists3.getKey())){
                                                            atn++;
                                                        }
                                                    }
                                                    attandance.setText(String.valueOf(atn));
                                                }
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                }
                                            });
                                        }
                                        classes.setText(String.valueOf(t));
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                                break;
                            }else {
                                classes.setText("0");
                                attandance.setText("0");
                            }
                        }
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

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ShowAttendance.this,Student.class);
                intent1.putExtra("roll",intent.getStringExtra("roll"));
                intent1.putExtra("session",intent.getStringExtra("session"));
                startActivity(intent1);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = getIntent();
        Intent intent1 = new Intent(ShowAttendance.this,Student.class);
        intent1.putExtra("roll",intent.getStringExtra("roll"));
        intent1.putExtra("session",intent.getStringExtra("session"));
        startActivity(intent1);
    }
}
