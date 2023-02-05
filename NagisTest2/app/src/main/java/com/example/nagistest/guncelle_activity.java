package com.example.nagistest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class guncelle_activity extends AppCompatActivity {

    TextView txt_guncelle_ad, txt_guncelle_soyad;

    EditText edt_guncelle_ad, edt_guncelle_soyad;


    Button guncelle_btn;
    DatabaseReference databaseReference;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guncelle);

        txt_guncelle_ad = findViewById(R.id.guncelle_text_ad);
        txt_guncelle_soyad = findViewById(R.id.guncelle_text_soyad);


        edt_guncelle_ad = findViewById(R.id.edt_guncelle_ad);
        edt_guncelle_soyad = findViewById(R.id.edt_guncelle_soyad);



        guncelle_btn = findViewById(R.id.guncelle_btn);



        Intent intent = getIntent();
        String gelenad = intent.getStringExtra("ad");
        String gelensoyad = intent.getStringExtra("soyad");
        String gelenmesaj = intent.getStringExtra("mesaj");
        String gelenid = intent.getStringExtra("id");
        String id = gelenid;



        txt_guncelle_ad.setText(gelenad);
        txt_guncelle_soyad.setText(gelensoyad);

        guncelle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference = FirebaseDatabase.getInstance().getReference("Canli").child("Insan").child(id);

                String ad= edt_guncelle_ad.getText().toString().trim();
                String soyad = edt_guncelle_soyad.getText().toString().trim();

                Canli insan =new Canli(id, ad, soyad, gelenmesaj);
                databaseReference.setValue(insan);
                Toast.makeText(getApplicationContext(),"Güncelleme Başarılı", Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(guncelle_activity.this, MainActivity.class);
                startActivity(intent1);

            }
        });

        Button sil_btn = findViewById(R.id.sil_btn);

        sil_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                databaseReference = FirebaseDatabase.getInstance().getReference("Canli").child("Insan").child(id);
                databaseReference.removeValue();
                Intent intent2 = new Intent(guncelle_activity.this, MainActivity.class);
                startActivity(intent2);

            }
        });


    }
}