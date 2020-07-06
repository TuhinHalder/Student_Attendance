package com.example.asimg.sams;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PracticalSubjectAdding extends AppCompatActivity {

    EditText l1,l2,l3,lc1,lc2,lc3,ssnl,ssnlc;
    Button save, cancel;
    DatabaseReference reff,reff2,reffl;
    DatabaseNewSession databaseNewSession,databaseNewSession1,databaseNewSession2,databaseNewSession2l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_subject_adding);

        final Intent intent = getIntent();
        final String corse = intent.getStringExtra("crs");
        final String sec = intent.getStringExtra("btch");
        final String sems = intent.getStringExtra("sem");
        final String fsyear = intent.getStringExtra("syear");
        final String teyear = intent.getStringExtra("eyear");
        final String session = fsyear+"-"+teyear;
        final String c = intent.getStringExtra("cnt");
        final int count = Integer.parseInt(c);
        Toast.makeText(PracticalSubjectAdding.this,corse+sems+session,Toast.LENGTH_LONG).show();

        l1 = findViewById(R.id.Lab1ET);
        l2 = findViewById(R.id.Lab2ET);
        l3 = findViewById(R.id.Lab3ET);
        lc1 = findViewById(R.id.LC1ET);
        lc2 = findViewById(R.id.LC2ET);
        lc3 = findViewById(R.id.LC3ET);
        ssnl = findViewById(R.id.SsnlET);
        ssnlc = findViewById(R.id.SsnlCET);
        save = findViewById(R.id.SvBTN);
        cancel = findViewById(R.id.CnclBTN);
        databaseNewSession = new DatabaseNewSession();
        databaseNewSession1 = new DatabaseNewSession();
        databaseNewSession2 = new DatabaseNewSession();
        databaseNewSession2l = new DatabaseNewSession();
        reff = FirebaseDatabase.getInstance().getReference().child("Newsession");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(l1.getText().toString().trim()) && TextUtils.isEmpty(lc1.getText().toString().trim())){
                    Toast.makeText(PracticalSubjectAdding.this,"Enter lab details",Toast.LENGTH_LONG).show();
                }else{
                    databaseNewSession.setCourse(corse);
                    databaseNewSession.setBatch(sec);
                    databaseNewSession.setSemester(sems);
                    databaseNewSession.setSession(session);
                    reff.child(corse+sec+sems+session).setValue(databaseNewSession);

                    //Theory entry
                    if (count>=1){
                        databaseNewSession2.setS1(intent.getStringExtra("subname1"));
                        databaseNewSession1.setSC1(intent.getStringExtra("subcode1"));
                        reff2 = FirebaseDatabase.getInstance().getReference().child("Newsession").child(corse+sec+sems+session).child("Subjects");
                        reff2.child(intent.getStringExtra("subcode1")).setValue(databaseNewSession2);
                    }
                    if (count>=2){
                        databaseNewSession2.setS1(intent.getStringExtra("subname2"));
                        databaseNewSession1.setSC1(intent.getStringExtra("subcode2"));
                        reff2 = FirebaseDatabase.getInstance().getReference().child("Newsession").child(corse+sec+sems+session).child("Subjects");
                        reff2.child(intent.getStringExtra("subcode2")).setValue(databaseNewSession2);
                    }
                    if (count>=3){
                        databaseNewSession2.setS1(intent.getStringExtra("subname3"));
                        databaseNewSession1.setSC1(intent.getStringExtra("subcode3"));
                        reff2 = FirebaseDatabase.getInstance().getReference().child("Newsession").child(corse+sec+sems+session).child("Subjects");
                        reff2.child(intent.getStringExtra("subcode3")).setValue(databaseNewSession2);
                    }
                    if (count>=4){
                        databaseNewSession2.setS1(intent.getStringExtra("subname4"));
                        databaseNewSession1.setSC1(intent.getStringExtra("subcode4"));
                        reff2 = FirebaseDatabase.getInstance().getReference().child("Newsession").child(corse+sec+sems+session).child("Subjects");
                        reff2.child(intent.getStringExtra("subcode4")).setValue(databaseNewSession2);
                    }
                    if (count>=5){
                        databaseNewSession2.setS1(intent.getStringExtra("subname5"));
                        databaseNewSession1.setSC1(intent.getStringExtra("subcode5"));
                        reff2 = FirebaseDatabase.getInstance().getReference().child("Newsession").child(corse+sec+sems+session).child("Subjects");
                        reff2.child(intent.getStringExtra("subcode5")).setValue(databaseNewSession2);
                    }
                    if (count>=6){
                        databaseNewSession2.setS1(intent.getStringExtra("subname6"));
                        databaseNewSession1.setSC1(intent.getStringExtra("subcode6"));
                        reff2 = FirebaseDatabase.getInstance().getReference().child("Newsession").child(corse+sec+sems+session).child("Subjects");
                        reff2.child(intent.getStringExtra("subcode6")).setValue(databaseNewSession2);
                    }

                    //Lab entry
                    databaseNewSession2l.setL1(l1.getText().toString().trim());
                    databaseNewSession1.setLC1(lc1.getText().toString().trim());
                    //reff1.child("Subjects").setValue(databaseNewSession1);
                    reffl = FirebaseDatabase.getInstance().getReference().child("Newsession").child(corse+sec+sems+session).child("Subjects");
                    reffl.child(lc1.getText().toString().trim()).setValue(databaseNewSession2l);
                    if ((TextUtils.isEmpty(l2.getText())) && (TextUtils.isEmpty(lc2.getText()))){
                    }else {
                        databaseNewSession2l.setL2(l2.getText().toString().trim());
                        databaseNewSession1.setLC2(lc2.getText().toString().trim());
                        reffl = FirebaseDatabase.getInstance().getReference().child("Newsession").child(corse+sec+sems+session).child("Subjects");
                        reffl.child(lc2.getText().toString().trim()).setValue(databaseNewSession2l);
                    }
                    if ((TextUtils.isEmpty(l3.getText())) && (TextUtils.isEmpty(lc3.getText()))){

                    }else {
                        databaseNewSession2l.setL3(l3.getText().toString().trim());
                        databaseNewSession1.setLC3(lc3.getText().toString().trim());
                        reffl = FirebaseDatabase.getInstance().getReference().child("Newsession").child(corse+sec+sems+session).child("Subjects");
                        reffl.child(lc3.getText().toString().trim()).setValue(databaseNewSession2l);
                    }
                    if ((TextUtils.isEmpty(ssnl.getText())) && (TextUtils.isEmpty(ssnlc.getText()))){

                    }else {
                        databaseNewSession2l.setSsnl(ssnl.getText().toString().trim());
                        databaseNewSession1.setSsnlc(ssnlc.getText().toString().trim());
                        reffl = FirebaseDatabase.getInstance().getReference().child("Newsession").child(corse+sec+sems+session).child("Subjects");
                        reffl.child(ssnlc.getText().toString().trim()).setValue(databaseNewSession2l);
                    }
                    Toast.makeText(PracticalSubjectAdding.this,"New session is created",Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(PracticalSubjectAdding.this,TheorySubjectAdding.class);
                intent1.putExtra("crs",intent.getStringExtra("crs"));
                intent1.putExtra("btch",intent.getStringExtra("btch"));
                intent1.putExtra("sem",intent.getStringExtra("sem"));
                intent1.putExtra("syear",intent.getStringExtra("syear"));
                intent1.putExtra("eyear",intent.getStringExtra("eyear"));
                startActivity(intent1);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent2 = getIntent();
        Intent intent1 = new Intent(PracticalSubjectAdding.this,TheorySubjectAdding.class);
        intent1.putExtra("crs",intent2.getStringExtra("crs"));
        intent1.putExtra("btch",intent2.getStringExtra("btch"));
        intent1.putExtra("sem",intent2.getStringExtra("sem"));
        intent1.putExtra("syear",intent2.getStringExtra("syear"));
        intent1.putExtra("eyear",intent2.getStringExtra("eyear"));
        startActivity(intent1);
    }
}