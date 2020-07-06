package com.example.asimg.sams;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class AddAdmin extends AppCompatActivity {

    private EditText user,pass;
    private Button add,cancel;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        user = findViewById(R.id.emailET);
        pass = findViewById(R.id.passET);
        add = findViewById(R.id.AddBTN);
        cancel = findViewById(R.id.CnclBTN);

        firebaseAuth = FirebaseAuth.getInstance();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getText().toString().trim().isEmpty()){
                    Toast.makeText(AddAdmin.this,"Enter email",Toast.LENGTH_LONG).show();
                }else if (isEmailValid(user.getText().toString().trim())){
                    if (pass.getText().toString().trim().isEmpty()){
                        Toast.makeText(AddAdmin.this,"Enter password",Toast.LENGTH_LONG).show();
                    }else if (validate()) {
                        //upload data to database
                        String user_email = user.getText().toString().trim();
                        String user_password = pass.getText().toString().trim();

                        firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    sendEmailVerification();
                                } else {
                                    Toast.makeText(AddAdmin.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                }else {
                    Toast.makeText(AddAdmin.this,"Email format is incorrect",Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAdmin.this,Admin.class);
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

    private Boolean validate() {
        Boolean result = false;

        String name = user.getText().toString();
        String password = pass.getText().toString();
        //String cpassword = userCPassword.getText().toString();
        String email = user.getText().toString();

        if (name.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        } else{

            result = true;
        }

        return result;
    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser !=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){
                        Toast.makeText(AddAdmin.this,"Successfully Registred, verification mail sent",Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                    }else {
                        Toast.makeText(AddAdmin.this,"Verification mail has not been sent!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(AddAdmin.this,Admin.class);
        startActivity(intent);
    };
}