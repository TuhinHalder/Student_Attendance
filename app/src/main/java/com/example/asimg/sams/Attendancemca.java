package com.example.asimg.sams;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Attendancemca extends AppCompatActivity {

    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendancemca);

        submit = (Button)findViewById(R.id.SbmtBTN);

        submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Attendancemca.this,"Submit successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Attendancemca.this,Faculty.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
