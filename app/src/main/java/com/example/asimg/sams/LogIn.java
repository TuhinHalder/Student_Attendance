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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogIn extends AppCompatActivity {

    private EditText usrnm, pswrd;
    private Button alogin, flogin, student;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        usrnm = (EditText)findViewById(R.id.UserET);
        pswrd = (EditText)findViewById(R.id.PassET);
        alogin = (Button)findViewById(R.id.ALogBTN);
        flogin = (Button)findViewById(R.id.FLogBTN);
        student = (Button)findViewById(R.id.StudentBTN);
        databaseReference = FirebaseDatabase.getInstance().getReference("Faculty");

        firebaseAuth = FirebaseAuth.getInstance();
        /*FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            finish();
            startActivity(new Intent(LogIn.this, Admin.class));
        }*/

        alogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usrnm.getText().toString().trim();
                String password = pswrd.getText().toString().trim();
                if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)){
                    Toast.makeText(LogIn.this,"Enter Username & Password",Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(username)){
                    Toast.makeText(LogIn.this,"Enter Username",Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(password)){
                    Toast.makeText(LogIn.this,"Enter Password",Toast.LENGTH_LONG).show();
                }else {
                    validate(usrnm.getText().toString(),pswrd.getText().toString());
                }
            }
        });

        flogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    String username = usrnm.getText().toString().trim();
                    String password = pswrd.getText().toString().trim();
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)){
                            Toast.makeText(LogIn.this,"Enter Username & Password",Toast.LENGTH_LONG).show();
                        }else if (TextUtils.isEmpty(username)){
                            Toast.makeText(LogIn.this,"Enter Username",Toast.LENGTH_LONG).show();
                        }else if (TextUtils.isEmpty(password)){
                            Toast.makeText(LogIn.this,"Enter Password",Toast.LENGTH_LONG).show();
                        }else if (dataSnapshot.child(username).exists()){
                            String pass = dataSnapshot.child(username).child("pwd").getValue().toString();
                            if (password.equals(pass)){
                                Intent intent = new Intent(LogIn.this,Faculty.class);
                                intent.putExtra("uname",username);
                                startActivity(intent);
                            }else {
                                Toast.makeText(LogIn.this,"Login Failed",Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(LogIn.this,"This user not registered",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this,StudentLog.class);
                startActivity(intent);
            }
        });
    }

    private void validate(String userName,String userPassword){

        firebaseAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    checkEmailVerification();

                }else{
                    Toast.makeText(LogIn.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void checkEmailVerification(){
        FirebaseUser firebaseUser=firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        if (emailflag){
            finish();
            startActivity(new Intent(LogIn.this,Admin.class));
        }else{
            Toast.makeText(this,"Verify your email",Toast.LENGTH_SHORT).show();
        }
    }

    private static long back_pressed;
    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit",
                    Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }
}