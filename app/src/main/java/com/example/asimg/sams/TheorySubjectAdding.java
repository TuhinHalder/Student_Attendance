package com.example.asimg.sams;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static java.lang.String.valueOf;

public class TheorySubjectAdding extends AppCompatActivity {

    private EditText subname1,subname2,subname3,subname4,subname5,subname6;
    private EditText subcode1,subcode2,subcode3,subcode4,subcode5,subcode6;
    private Button saveCon, cancel;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory_subject_adding);
        Intent intent = getIntent();
        final String corse = intent.getStringExtra("crs");
        final String sec = intent.getStringExtra("btch");
        final String sems = intent.getStringExtra("sem");
        final String fyear = intent.getStringExtra("syear");
        final String tyear = intent.getStringExtra("eyear");

        subname1 = (EditText)findViewById(R.id.SN1ET);
        subname2 = (EditText)findViewById(R.id.SN2ET);
        subname3 = (EditText)findViewById(R.id.SN3ET);
        subname4 = (EditText)findViewById(R.id.SN4ET);
        subname5 = (EditText)findViewById(R.id.SN5ET);
        subname6 = (EditText)findViewById(R.id.SN6ET);
        subcode1 = (EditText)findViewById(R.id.LC1ET);
        subcode2 = (EditText)findViewById(R.id.LC2ET);
        subcode3 = (EditText)findViewById(R.id.LC3ET);
        subcode4 = (EditText)findViewById(R.id.SC4ET);
        subcode5 = (EditText)findViewById(R.id.SC5ET);
        subcode6 = (EditText)findViewById(R.id.SC6ET);
        saveCon = (Button)findViewById(R.id.SvCntnBTN);
        cancel = (Button)findViewById(R.id.CnclBTN);

        saveCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(TheorySubjectAdding.this,PracticalSubjectAdding.class);
                intent1.putExtra("crs",corse);
                intent1.putExtra("btch",sec);
                intent1.putExtra("sem",sems);
                intent1.putExtra("syear",fyear);
                intent1.putExtra("eyear",tyear);
                if (corse.equals("MCA") && sems.equals("Sem6")){
                    String c = Integer.toString(count);
                    intent1.putExtra("cnt",c);
                    startActivity(intent1);
                }else if(TextUtils.isEmpty(subname1.getText().toString().trim()) && TextUtils.isEmpty(subcode1.getText().toString().trim()) &&
                        TextUtils.isEmpty(subname2.getText().toString().trim()) && TextUtils.isEmpty(subcode2.getText().toString().trim()) &&
                        TextUtils.isEmpty(subname3.getText().toString().trim()) && TextUtils.isEmpty(subcode3.getText().toString().trim())){
                    Toast.makeText(TheorySubjectAdding.this,"Enter Minimum 3 Subject Details",Toast.LENGTH_LONG).show();
                }else {
                    if ((TextUtils.isEmpty(subname1.getText().toString().trim())) && (TextUtils.isEmpty(subcode1.getText().toString().trim()))) {

                    } else {
                        intent1.putExtra("subname1", subname1.getText().toString().trim());
                        intent1.putExtra("subcode1", subcode1.getText().toString().trim());
                        count++;
                    }
                    if ((TextUtils.isEmpty(subname2.getText().toString().trim())) && (TextUtils.isEmpty(subcode2.getText().toString().trim()))) {

                    } else {
                        intent1.putExtra("subname2", subname2.getText().toString().trim());
                        intent1.putExtra("subcode2", subcode2.getText().toString().trim());
                        count++;
                    }
                    if ((TextUtils.isEmpty(subname3.getText().toString().trim())) && (TextUtils.isEmpty(subcode3.getText().toString().trim()))) {

                    } else {
                        intent1.putExtra("subname3", subname3.getText().toString().trim());
                        intent1.putExtra("subcode3", subcode3.getText().toString().trim());
                        count++;
                    }
                    if ((TextUtils.isEmpty(subname4.getText().toString().trim())) && (TextUtils.isEmpty(subcode4.getText().toString().trim()))) {

                    } else {
                        intent1.putExtra("subname4", subname4.getText().toString().trim());
                        intent1.putExtra("subcode4", subcode4.getText().toString().trim());
                        count++;
                    }
                    if ((TextUtils.isEmpty(subname5.getText().toString().trim())) && (TextUtils.isEmpty(subcode5.getText().toString().trim()))) {

                    } else {
                        intent1.putExtra("subname5", subname5.getText().toString().trim());
                        intent1.putExtra("subcode5", subcode5.getText().toString().trim());
                        count++;
                    }
                    if ((TextUtils.isEmpty(subname6.getText().toString().trim())) && (TextUtils.isEmpty(subcode6.getText().toString().trim()))) {

                    } else {
                        intent1.putExtra("subname6", subname6.getText().toString().trim());
                        intent1.putExtra("subcode6", subcode6.getText().toString().trim());
                        count++;
                    }
                    String c = Integer.toString(count);
                    intent1.putExtra("cnt", c);
                    startActivity(intent1);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(TheorySubjectAdding.this,NewSession.class);
                startActivity(intent1);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent1 = new Intent(TheorySubjectAdding.this,NewSession.class);
        startActivity(intent1);
    }
}