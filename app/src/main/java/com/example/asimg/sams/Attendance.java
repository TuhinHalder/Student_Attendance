package com.example.asimg.sams;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Attendance extends AppCompatActivity {

    Button scan,cancel;
    DatabaseAttendance databaseAttendance;
    DatabaseReference databaseReference;
    String sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        final Intent intent = getIntent();
        int cyear = Calendar.getInstance().get(Calendar.YEAR);
        int eyear = cyear+3;
        String ss = cyear+"-"+eyear;
        String ssn = intent.getStringExtra("stream")+intent.getStringExtra("batch")+intent.getStringExtra("sem")+ss;
        sub = intent.getStringExtra("sub");
        scan = findViewById(R.id.ScanBTN);
        cancel = findViewById(R.id.CNCLBTN);
        databaseAttendance = new DatabaseAttendance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Attendance").child(ssn);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator=new IntentIntegrator(Attendance.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setCameraId(0);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setPrompt("Scanning");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setBarcodeImageEnabled(true);
                intentIntegrator.initiateScan();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Attendance.this,Faculty.class);
                intent1.putExtra("stream",intent.getStringExtra("stream"));
                intent1.putExtra("batch",intent.getStringExtra("batch"));
                intent1.putExtra("sem",intent.getStringExtra("sem"));
                intent1.putExtra("sub",intent.getStringExtra("sub"));
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        final IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null && result.getContents()!=null){
            new AlertDialog.Builder(Attendance.this)
                    .setTitle("Scan Result")
                    .setMessage(result.getContents())
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String data = result.getContents();
                            if (data.contains(sub)){
                                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                Date date = new Date();
                                if (data.contains(dateFormat.format(date))){
                                    char[] arr = data.toCharArray();
                                    char roll = arr[0];
                                    databaseAttendance.setAttent("Y");
                                    DateFormat dateFormat1 = new SimpleDateFormat("dd_MM_yyyy");
                                    Date date1 = new Date();
                                    databaseReference.child(sub).child(dateFormat1.format(date1)).child(String.valueOf(roll)).setValue(databaseAttendance);
                                }else {
                                    Toast.makeText(Attendance.this,"Your date is not matching",Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(Attendance.this,"Subject code is not matching",Toast.LENGTH_LONG).show();
                            }
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed(){
        Intent intent2 = getIntent();
        Intent intent1 = new Intent(Attendance.this,Faculty.class);
        intent1.putExtra("stream",intent2.getStringExtra("stream"));
        intent1.putExtra("batch",intent2.getStringExtra("batch"));
        intent1.putExtra("sem",intent2.getStringExtra("sem"));
        intent1.putExtra("sub",intent2.getStringExtra("sub"));
        startActivity(intent1);
    }
}