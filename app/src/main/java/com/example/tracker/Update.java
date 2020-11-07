package com.example.tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Update extends AppCompatActivity {
    private EditText infoname,infoquantity,infotype;
    private Button btnupdate,btndel,btninfook;
    private String childID ;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    newitem itm;

    private ArrayList<String> itemsname = new ArrayList<String>();
    private ArrayList<String> itemsquantity = new ArrayList<String>();
    private ArrayList<String> itemstype = new ArrayList<String>();
    //private ArrayList<String> childID = new ArrayList<String>();


    private void setUI(){
        Log.d("name11",childID);
        infoname = findViewById(R.id.nameupdate);
        infoquantity = findViewById(R.id.quantityupdate);
        infotype = findViewById(R.id.typeupdate);
        btnupdate = findViewById(R.id.btnupdate);
        btndel = findViewById(R.id.btndelete);
        btninfook = findViewById(R.id.btnokupdate);

        databaseReference = firebaseDatabase.getInstance().getReference().child("User");                    //databasereference  till User

        firebaseWrite();



    }
    private void  setActivitytoMain(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void firebaseWrite(){
       databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                try {

                    infoname.setText(snapshot.child(childID).child("itmname").getValue().toString());                       //taking value from the firebase using the childID
                    infoquantity.setText(snapshot.child(childID).child("itmquantity").getValue().toString());
                    infotype.setText(snapshot.child(childID).child("itmtype").getValue().toString());
                }catch (NullPointerException e){
                    //Log.d("name2",""+childID);
                }


            }

           @Override
          public void onCancelled(@NonNull DatabaseError error) {

           }
       });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intent=getIntent();
        childID=intent.getStringExtra("position"); //getting id of the adapter

            setUI();
            btndel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    databaseReference.child(childID).removeValue();                                              // removing the childID child
                    setActivitytoMain();
                }
            });
            btninfook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setActivitytoMain();
                }
            });
            btnupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itm = new newitem();
                    itm.setItmname(infoname.getText().toString());
                    itm.setItmquantity(infoquantity.getText().toString());
                    itm.setItmtype(infotype.getText().toString());
                    itm.setChildID(childID);
                    databaseReference.child(childID).setValue(itm);                                             //updating the values to firebase

                    setActivitytoMain();

                }
            });






    }
}