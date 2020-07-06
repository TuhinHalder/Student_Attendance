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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateFaculty extends AppCompatActivity {

    EditText email,name,hq,mob,password;
    Spinner faculty;
    Button update,cancel;
    DatabaseReference reff;
    DatabaseFaculty databaseFaculty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);

        email = findViewById(R.id.EmailET);
        name = findViewById(R.id.NameET);
        hq = findViewById(R.id.HQET);
        mob = findViewById(R.id.MobET);
        password = findViewById(R.id.PWDET);
        faculty = findViewById(R.id.FacultySPNR);
        update = findViewById(R.id.UpdtBTN);
        cancel = findViewById(R.id.CnclBTN);
        reff = FirebaseDatabase.getInstance().getReference("Faculty");
        databaseFaculty = new DatabaseFaculty();

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> temp = dataSnapshot.getChildren();
                if (dataSnapshot.getChildrenCount()==0){
                    Toast.makeText(UpdateFaculty.this,"No faculty to show",Toast.LENGTH_LONG).show();
                }else {
                    List<String> list = new ArrayList<>();
                    for (DataSnapshot lists : temp){
                        list.add(lists.getKey());
                    }
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(UpdateFaculty.this, android.R.layout.simple_spinner_item, list);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    faculty.setAdapter(adapter1);
                    faculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String itemvalue = parent.getItemAtPosition(position).toString();
                            String mail =dataSnapshot.child(itemvalue).child("email").getValue().toString();
                            String name1 =dataSnapshot.child(itemvalue).child("name").getValue().toString();
                            String hq1 =dataSnapshot.child(itemvalue).child("hq").getValue().toString();
                            String mob1 =dataSnapshot.child(itemvalue).child("phone").getValue().toString();
                            String pwd = dataSnapshot.child(itemvalue).child("pwd").getValue().toString();
                            email.setText(mail);
                            name.setText(name1);
                            hq.setText(hq1);
                            mob.setText(mob1);
                            password.setText(pwd);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty()){
                    Toast.makeText(UpdateFaculty.this,"Enter email",Toast.LENGTH_LONG).show();
                }else if (isEmailValid(email.getText().toString())){
                    if (name.getText().toString().isEmpty()){
                        Toast.makeText(UpdateFaculty.this,"Enter name",Toast.LENGTH_LONG).show();
                    }else if (hq.getText().toString().isEmpty()){
                        Toast.makeText(UpdateFaculty.this,"Enter highest qualification",Toast.LENGTH_LONG).show();
                    }else if (mob.getText().toString().isEmpty()){
                        Toast.makeText(UpdateFaculty.this,"Enter mobile number",Toast.LENGTH_LONG).show();
                    }else if (isMobileValid(mob.getText().toString())) {
                        if (password.getText().toString().isEmpty()){
                            Toast.makeText(UpdateFaculty.this,"Enter password",Toast.LENGTH_LONG).show();
                        }else {
                            databaseFaculty.setEmail(email.getText().toString().trim());
                            databaseFaculty.setName(name.getText().toString().trim());
                            databaseFaculty.setHq(hq.getText().toString().trim());
                            databaseFaculty.setPhone(Long.valueOf(mob.getText().toString().trim()));
                            databaseFaculty.setPwd(password.getText().toString().trim());
                            reff.child("Faculty_"+name.getText().toString().trim()).setValue(databaseFaculty);
                            Toast.makeText(UpdateFaculty.this,name.getText().toString()+" is updated",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(UpdateFaculty.this,"Mobile number is incorrect",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(UpdateFaculty.this,"Email id is incorrect",Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateFaculty.this,Admin.class);
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

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(UpdateFaculty.this,Admin.class);
        startActivity(intent);
    }
}