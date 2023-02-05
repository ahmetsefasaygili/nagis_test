package com.example.nagistest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {


    EditText edt_ad, edt_soyad;
    Button ekle_btn;
    DatabaseReference databaseReference;
    ListView listView_Canli;
    List<Canli> canliList;
    TextView txtTime;

  Retrofit retrofit;
     TimeApi timeApi;
     String baseUrl = "https://jsonplaceholder.typicode.com/todos/";
     Call<TimeTurkey> timeTurkeyCall;
     TimeTurkey timeTurkey;



    private void init(){

        txtTime = findViewById(R.id.txtTime);
        ekle_btn = findViewById(R.id.ekle_btn);
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRetrofitsettings();
        init();


        databaseReference = FirebaseDatabase.getInstance().getReference("Canli").child("Insan");


        edt_ad = findViewById(R.id.edt_ad);
        edt_soyad = findViewById(R.id.edt_soyad);
        ekle_btn = findViewById(R.id.ekle_btn);
        listView_Canli = findViewById(R.id.listView_Canli);
        canliList = new ArrayList<>();

        listView_Canli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Canli insan = canliList.get(position);
                Intent intent = new Intent(getApplicationContext(), guncelle_activity.class);

                intent.putExtra("id", insan.getCanliid());
                intent.putExtra("ad", insan.getCanliad());
                intent.putExtra("soyad",insan.getCanlisoyad());
                intent.putExtra("mesaj",insan.getMesaj());

                txtTime = findViewById(R.id.txtTime);
                ekle_btn = findViewById(R.id.ekle_btn);

                startActivity(intent);
            }
        });



        ekle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRetrofitsettings();
                kullaniciEkle();

            }

        });


    }
    private void setRetrofitsettings(){

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        timeApi = retrofit.create(TimeApi.class);
        timeTurkeyCall = timeApi.getTime();
        timeTurkeyCall.enqueue(new Callback<TimeTurkey>() {
            @Override
            public void onResponse(Call<TimeTurkey> call, Response<TimeTurkey> response) {

                if (!response.isSuccessful()) {
                    return;
                }

                timeTurkey = response.body();

                if (timeApi != null) {
                    assert timeTurkey != null;
                    txtTime.setText(timeTurkey.getDateTime().split("T")[0]);
                }

            }

            @Override
            public void onFailure(Call<TimeTurkey> call, Throwable t) {

                System.out.println(t.toString());
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                canliList.clear();

                for (DataSnapshot canliSnapshot : snapshot.getChildren()) {
                    Canli insan = canliSnapshot.getValue(Canli.class);
                    canliList.add(insan);
                }

                CanliList adapter = new CanliList(MainActivity.this, canliList);
                listView_Canli.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void kullaniciEkle(){
        String isim = edt_ad.getText().toString();
        String soyisim = edt_soyad.getText().toString();
        String mesaj = txtTime.getText().toString();

        String id = databaseReference.push().getKey();
        Canli insan = new Canli(id, isim, soyisim,mesaj);
        databaseReference.child(id).setValue(insan);






    }
}