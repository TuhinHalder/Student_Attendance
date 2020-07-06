package com.example.asimg.sams;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewSession extends AppCompatActivity {

    private Spinner course;
    private Spinner batch;
    private Spinner semester;
    private EditText fyear;
    private EditText tyear;
    private Button newseason,cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_session);

        course = (Spinner)findViewById(R.id.CrsSPNR);
        batch = (Spinner)findViewById(R.id.BatchSPNR);
        semester = (Spinner)findViewById(R.id.SemSPNR);
        fyear = (EditText)findViewById(R.id.FYearET);
        tyear = (EditText)findViewById(R.id.TYearET);
        newseason = (Button)findViewById(R.id.AtnBTN);
        cancel = findViewById(R.id.CnclBTN);

        List<String> list1= new ArrayList<>();
        list1.add("Select course");
        list1.add("BCA");
        list1.add("MCA");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
        course.setAdapter(adapter1);

        course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemvalue1 = parent.getItemAtPosition(position).toString();

                if (itemvalue1.equals("MCA")){
                    List<String> list2 = new ArrayList<>();
                    list2.add("Saltlake");
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(NewSession.this,android.R.layout.simple_spinner_item,list2);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    batch.setAdapter(adapter2);

                    batch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String itemvalue2 = parent.getItemAtPosition(position).toString();

                            if (itemvalue2.equals("Saltlake")) {
                                List<String> list3 = new ArrayList<>();
                                list3.add("Select semester");
                                list3.add("Sem1");
                                list3.add("Sem2");
                                list3.add("Sem3");
                                list3.add("Sem4");
                                list3.add("Sem5");
                                list3.add("Sem6");
                                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(NewSession.this, android.R.layout.simple_spinner_item, list3);
                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                semester.setAdapter(adapter3);
                            }else{
                                List<String> list3 = new ArrayList<>();
                                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(NewSession.this, android.R.layout.simple_spinner_item,list3);
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
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(NewSession.this,android.R.layout.simple_spinner_item, list2);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    batch.setAdapter(adapter2);

                    batch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String itemvalue3 = parent.getItemAtPosition(position).toString();

                            if (itemvalue3.equals("Saltlake-A") || itemvalue3.equals("Saltlake-B") || itemvalue3.equals("Kolkata-1") || itemvalue3.equals("Kolkata-2")) {
                                List<String> list3 = new ArrayList<>();
                                list3.add("Select semester");
                                list3.add("Sem1");
                                list3.add("Sem2");
                                list3.add("Sem3");
                                list3.add("Sem4");
                                list3.add("Sem5");
                                list3.add("Sem6");
                                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(NewSession.this, android.R.layout.simple_spinner_item, list3);
                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                semester.setAdapter(adapter3);
                            }else {
                                List<String> list3 = new ArrayList<>();
                                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(NewSession.this, android.R.layout.simple_spinner_item, list3);
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
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(NewSession.this, android.R.layout.simple_spinner_item,list2);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    batch.setAdapter(adapter2);

                    List<String> list3 = new ArrayList<>();
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(NewSession.this, android.R.layout.simple_spinner_item, list3);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    semester.setAdapter(adapter3);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        newseason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String crs = course.getSelectedItem().toString();
                String btch;
                String sem;
                String from = fyear.getText().toString().trim();
                String to = tyear.getText().toString().trim();
                int cyear1 = Calendar.getInstance().get(Calendar.YEAR);
                int cyear = cyear1-3;
                int syear,eyear;
                if (course.getSelectedItem().toString().equals("Select course")){
                    Toast.makeText(NewSession.this,"Select course",Toast.LENGTH_LONG).show();
                }else{
                     btch = batch.getSelectedItem().toString();
                    if (btch.equals("Select batch")){
                        Toast.makeText(NewSession.this,"Select batch",Toast.LENGTH_LONG).show();
                    }else{
                        sem = semester.getSelectedItem().toString();
                        if (sem.equals("Select semester")){
                            Toast.makeText(NewSession.this,"Select semester",Toast.LENGTH_LONG).show();
                        }else if(TextUtils.isEmpty(from)){
                            Toast.makeText(NewSession.this,"Enter starting year",Toast.LENGTH_LONG).show();
                        }else if (!TextUtils.isEmpty(from)){
                            syear = Integer.parseInt(from);
                            if (syear>=cyear ){
                                if (TextUtils.isEmpty(to)){
                                    Toast.makeText(NewSession.this,"Enter ending year",Toast.LENGTH_LONG).show();
                                }else {
                                    eyear = Integer.parseInt(to);
                                    if ((syear+3)==eyear){
                                        Intent intent = new Intent(NewSession.this, TheorySubjectAdding.class);
                                        intent.putExtra("crs", crs);
                                        intent.putExtra("btch", btch);
                                        intent.putExtra("sem", sem);
                                        intent.putExtra("syear",from);
                                        intent.putExtra("eyear",to);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(NewSession.this,"Enter valid ending year",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }else {
                                Toast.makeText(NewSession.this,"Enter valid starting year",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewSession.this,Admin.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(NewSession.this,Admin.class);
        startActivity(intent);
    }
}