package com.example.asimg.sams;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentProfile extends AppCompatActivity {

    private TextView id,roll,name,dob,mail,mob;
    private EditText pass;
    private Button update,cancel;
    DatabaseReference databaseReference1;
    DatabaseAddStudent databaseAddStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        final Intent intent = getIntent();
        id = findViewById(R.id.IDTV);
        roll = findViewById(R.id.RollTV);
        name = findViewById(R.id.NameTV);
        dob = findViewById(R.id.DOBTV);
        mail = findViewById(R.id.EmailTV);
        mob = findViewById(R.id.MobTV);
        pass = findViewById(R.id.PassET);
        update = findViewById(R.id.UpdtBTN);
        cancel = findViewById(R.id.CnclBTN);

        databaseAddStudent = new DatabaseAddStudent();
        databaseReference1 = FirebaseDatabase.getInstance().getReference("Student").child(intent.getStringExtra("session")).child(intent.getStringExtra("roll"));
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                id.setText(dataSnapshot.child("id").getValue().toString());
                roll.setText(dataSnapshot.child("roll").getValue().toString());
                name.setText(dataSnapshot.child("name").getValue().toString());
                dob.setText(dataSnapshot.child("dob").getValue().toString());
                mail.setText(dataSnapshot.child("email").getValue().toString());
                mob.setText(dataSnapshot.child("mobile").getValue().toString());
                pass.setText(dataSnapshot.child("pwd").getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass.getText().toString().trim().isEmpty()){
                    Toast.makeText(StudentProfile.this,"Enter password",Toast.LENGTH_LONG).show();
                }else if (pass.getText().toString().trim().length()<6){
                    Toast.makeText(StudentProfile.this,"Minimum 6 character requires",Toast.LENGTH_LONG).show();
                }else {
                    databaseAddStudent.setId(id.getText().toString().trim());
                    Long r = Long.parseLong(id.getText().toString().trim());
                    databaseAddStudent.setRoll(r);
                    databaseAddStudent.setName(name.getText().toString().trim());
                    databaseAddStudent.setDob(dob.getText().toString().trim());
                    databaseAddStudent.setEmail(mail.getText().toString().trim());
                    Long phn = Long.parseLong(mob.getText().toString().trim());
                    databaseAddStudent.setMobile(phn);
                    databaseAddStudent.setPwd(pass.getText().toString().trim());
                    databaseReference1.setValue(databaseAddStudent);
                    //Toast.makeText(StudentProfile.this,"Update Successfully",Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(StudentProfile.this,StudentLog.class);
                    startActivity(intent1);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(StudentProfile.this,Student.class);
                intent1.putExtra("roll",intent.getStringExtra("roll"));
                intent1.putExtra("session",intent.getStringExtra("session"));
                startActivity(intent1);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = getIntent();
        Intent intent1 = new Intent(StudentProfile.this,Student.class);
        intent1.putExtra("roll",intent.getStringExtra("roll"));
        intent1.putExtra("session",intent.getStringExtra("session"));
        startActivity(intent1);
    }
}