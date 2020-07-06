package com.example.asimg.sams;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddFaculty extends AppCompatActivity {

    EditText name, hq, mob, email;
    Button save, cancel;
    DatabaseReference reff;
    DatabaseFaculty databaseFaculty;
    boolean ch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);

        name = findViewById(R.id.NameET);
        hq = findViewById(R.id.HQET);
        mob = findViewById(R.id.MobileET);
        email = (EditText)findViewById(R.id.EmailET);
        save = (Button)findViewById(R.id.SvBTN);
        cancel = (Button)findViewById(R.id.CnclBTN);
        databaseFaculty = new DatabaseFaculty();
        reff= FirebaseDatabase.getInstance().getReference("Faculty");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nm = name.getText().toString().trim();
                String hql = hq.getText().toString().trim();
                String mail = email.getText().toString().trim();
                String ph1 = mob.getText().toString().trim();

                if (TextUtils.isEmpty(nm) && TextUtils.isEmpty(hql) && TextUtils.isEmpty(mail) && TextUtils.isEmpty(ph1)){
                    Toast.makeText(AddFaculty.this,"Please fill all information",Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(mail)){
                    Toast.makeText(AddFaculty.this,"Please enter mail",Toast.LENGTH_LONG).show();
                }else if(isEmailValid(mail)){
                    if (TextUtils.isEmpty(nm)){
                        Toast.makeText(AddFaculty.this,"Please enter name",Toast.LENGTH_LONG).show();
                    }else if(TextUtils.isEmpty(hql)){
                        Toast.makeText(AddFaculty.this,"Please enter highest qualification",Toast.LENGTH_LONG).show();
                    }else if(TextUtils.isEmpty(ph1)){
                        Toast.makeText(AddFaculty.this,"Please enter phone number",Toast.LENGTH_LONG).show();
                    }else if (isMobileValid(ph1)){
                        if (ph1.length()>10 || ph1.length()<10){
                            Toast.makeText(AddFaculty.this,"Phone number should be 10 digit",Toast.LENGTH_LONG).show();
                        }else{
                            if (check(nm)){
                                databaseFaculty.setEmail(mail);
                                databaseFaculty.setName(nm);
                                databaseFaculty.setHq(hql);
                                databaseFaculty.setPwd(mail);
                                Long ph = Long.parseLong(ph1);
                                databaseFaculty.setPhone(ph);
                                reff.child("Faculty_"+name.getText().toString().trim()).setValue(databaseFaculty);
                                Toast.makeText(AddFaculty.this,"Faculty Added",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(AddFaculty.this, "This faculty already exist", Toast.LENGTH_LONG).show();
                            }
                        }
                    }else {
                        Toast.makeText(AddFaculty.this,"Mobile number is incorrect",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(AddFaculty.this,"This email format is incorrect",Toast.LENGTH_LONG).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddFaculty.this,Admin.class);
                startActivity(intent);
            }
        });
    }

    public static boolean isEmailValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean isMobileValid(String s)
    {
        Pattern p = Pattern.compile("(0/91)?[6-9][0-9]{9}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }
    public boolean check(final String nm1){
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nm3 = "Faculty_"+nm1;
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    //Toast.makeText(AddFaculty.this,data.getKey(),Toast.LENGTH_LONG).show();
                    if (nm3.equals(data.getKey())) {
                        //Toast.makeText(AddFaculty.this,data.getKey(),Toast.LENGTH_LONG).show();
                        ch = false;
                        break;
                    }else {
                        ch = true;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return ch;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(AddFaculty.this,Admin.class);
        startActivity(intent);
    }
}