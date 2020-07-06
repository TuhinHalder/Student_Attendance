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
import java.util.Calendar;
import java.util.List;

public class Preview extends AppCompatActivity {

    private Spinner roll;
    private TextView classes, attendance;
    private Button ok;
    DatabaseReference databaseReference,reff,reff1,reff2;
    int t,atn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        final Intent intent = getIntent();
        roll = findViewById(R.id.RollSPNR);
        classes = findViewById(R.id.ttlTV);
        attendance = findViewById(R.id.atnTV);
        ok = findViewById(R.id.OKBTN2);
        int cyear = Calendar.getInstance().get(Calendar.YEAR);
        int eyear = cyear+3;
        final String ssn = cyear+"-"+eyear;
        final String session = intent.getStringExtra("stream")+intent.getStringExtra("batch")+intent.getStringExtra("sem")+ssn;
        final String sc = intent.getStringExtra("sub");
        databaseReference = FirebaseDatabase.getInstance().getReference("Student").child(session );

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> temp = dataSnapshot.getChildren();
                List<String> list = new ArrayList<>();
                for (DataSnapshot lists : temp){
                    list.add(lists.getKey());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(Preview.this,android.R.layout.simple_spinner_item,list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                roll.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        roll.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String rl = parent.getItemAtPosition(position).toString();
                reff = FirebaseDatabase.getInstance().getReference("Attendance").child(session);
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> temp1 = dataSnapshot.getChildren();
                        for (DataSnapshot lists1 : temp1){
                            if (lists1.getKey().equals(sc)){
                                reff1 = FirebaseDatabase.getInstance().getReference("Attendance").child(session).child(sc);
                                reff1.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Iterable<DataSnapshot> temp2 = dataSnapshot.getChildren();
                                        t=0;
                                        for (DataSnapshot lists2 : temp2){
                                            t++;
                                            reff2 = FirebaseDatabase.getInstance().getReference("Attendance").child(session).child(sc).child(lists2.getKey());
                                            reff2.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    Iterable<DataSnapshot> temp3 = dataSnapshot.getChildren();
                                                    atn=0;
                                                    for (DataSnapshot lists3 : temp3){
                                                        if (rl.equals(lists3.getKey())){
                                                            atn++;
                                                        }
                                                    }
                                                    attendance.setText(String.valueOf(atn));
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
                                attendance.setText("0");
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
                Intent intent1 = new Intent(Preview.this,Faculty.class);
                intent1.putExtra("stream",intent.getStringExtra("stream"));
                intent1.putExtra("batch",intent.getStringExtra("batch"));
                intent1.putExtra("sem",intent.getStringExtra("sem"));
                intent1.putExtra("sub",intent.getStringExtra("sub"));
                startActivity(intent1);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent2 = getIntent();
        Intent intent1 = new Intent(Preview.this,Faculty.class);
        intent1.putExtra("stream",intent2.getStringExtra("stream"));
        intent1.putExtra("batch",intent2.getStringExtra("batch"));
        intent1.putExtra("sem",intent2.getStringExtra("sem"));
        intent1.putExtra("sub",intent2.getStringExtra("sub"));
        startActivity(intent1);
    }
}