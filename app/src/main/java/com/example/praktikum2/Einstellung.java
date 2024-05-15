package com.example.praktikum2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Einstellung extends AppCompatActivity {

    private Button neueDatenSpeichern;
    private Button abbrechen;
    private Button abmelden;
    private EditText editName, editNachName, editEmail, editTelefon, editPasswort, editPasswortBest;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseUser firebaseUser2;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einstellung);

        editName = (EditText) findViewById(R.id.editText);
        editNachName = (EditText) findViewById(R.id.editText2);
        editEmail = (EditText) findViewById(R.id.editText4);
        editTelefon = (EditText) findViewById(R.id.editText5);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference databaseReference = firebaseDatabase.getReference("User").child(firebaseAuth.getUid()).child("Daten");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {//@NonNull in die klammer vor data snapshot
                User user = dataSnapshot.getValue(User.class);
                editName.setText(user.getVor_name());
                editNachName.setText(user.getNach_name());
                editEmail.setText(user.getEmail());
                editTelefon.setText(user.getTelefon_nr());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Einstellung.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        neueDatenSpeichern = (Button) findViewById(R.id.button3);
        neueDatenSpeichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    datenErneuern();

                Intent datenSpeichernintent = new Intent(Einstellung.this, Startseite.class);
                startActivity(datenSpeichernintent);
            }
        });

        abbrechen = (Button) findViewById(R.id.button6);
        abbrechen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abbruchintent = new Intent(Einstellung.this, Startseite.class);
                startActivity(abbruchintent);
            }
        });

        abmelden = (Button) findViewById(R.id.button10);
        abmelden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                finish();
                Intent abmeldenintent = new Intent(Einstellung.this, MainActivity.class);
                startActivity(abmeldenintent);
            }
        });




    }

    private void datenErneuern(){

        editName = (EditText) findViewById(R.id.editText);
        editNachName = (EditText) findViewById(R.id.editText2);
        editEmail = (EditText) findViewById(R.id.editText4);
        editTelefon = (EditText) findViewById(R.id.editText5);
        editPasswort = (EditText) findViewById(R.id.editText6);
        editPasswortBest = (EditText) findViewById(R.id.editText8);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        DatabaseReference databaseReference = firebaseDatabase.getReference("User").child(firebaseAuth.getUid()).child("Daten");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                editName.setText(user.getVor_name());
                editNachName.setText(user.getNach_name());
                editEmail.setText(user.getEmail());
                editTelefon.setText(user.getTelefon_nr());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Einstellung.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        String name = editName.getText().toString();
        String vname = editNachName.getText().toString();
        String email = editEmail.getText().toString();
        String telefon = editTelefon.getText().toString();
        String passwort = editPasswort.getText().toString();
        String passwortBest = editPasswortBest.getText().toString();
        final String neuE = email;

        User user1 = new User(name, vname, email, telefon);





        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        firebaseUser.updatePassword(passwort).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(Einstellung.this, "Passwort ändern erfolgreich!", Toast.LENGTH_SHORT).show();

                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


                    firebaseUser.updateEmail(neuE).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Einstellung.this, "Email ändern erfolgreich!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(Einstellung.this, "Email ändern fehlgeschlagen!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    finish();
                }
                else{
                    Toast.makeText(Einstellung.this, "Passwort ändern fehlgeschlagen!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        databaseReference.setValue(user1);



        finish();


    }




}
