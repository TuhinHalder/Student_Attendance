package com.example.asimg.sams;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GiveAttendance extends AppCompatActivity {

    Spinner subcode;
    DatabaseReference databaseReference,reff;
    Button generateqr,done;
    ImageView qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_attendance);

        final Intent intent = getIntent();
        String ssn = intent.getStringExtra("session");
        final String roll = intent.getStringExtra("roll");
        subcode = findViewById(R.id.SCSPNR);
        generateqr = findViewById(R.id.GQRBTN);
        qr = findViewById(R.id.qrcode);
        done = findViewById(R.id.DNBTN);
        databaseReference = FirebaseDatabase.getInstance().getReference("Attendance").child(ssn);
        reff = FirebaseDatabase.getInstance().getReference("Newsession").child(ssn).child("Subjects");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> temp = dataSnapshot.getChildren();
                List<String> list4 = new ArrayList<>();
                for (DataSnapshot lists : temp){
                    list4.add(lists.getKey());
                }
                ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(GiveAttendance.this, android.R.layout.simple_spinner_item, list4);
                adapter4.setDropDownViewResource(android.R.layout.simple_spinner_item);
                subcode.setAdapter(adapter4);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        generateqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                //System.out.println(dateFormat.format(date));
                //int date = Calendar.getInstance().get(Calendar.DATE);
                String sc = roll+"_"+subcode.getSelectedItem().toString()+"_"+dateFormat.format(date);
                try {
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    BitMatrix bitMatrix = multiFormatWriter.encode(sc, BarcodeFormat.QR_CODE, 700, 700);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    qr.setImageBitmap(bitmap);
                }catch (WriterException e){
                    e.printStackTrace();
                }
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(GiveAttendance.this,Student.class);
                intent1.putExtra("roll",intent.getStringExtra("roll"));
                intent1.putExtra("session",intent.getStringExtra("session"));
                startActivity(intent1);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent2 = getIntent();
        Intent intent1 = new Intent(GiveAttendance.this,Student.class);
        intent1.putExtra("roll",intent2.getStringExtra("roll"));
        intent1.putExtra("session",intent2.getStringExtra("session"));
        startActivity(intent1);
    }
}