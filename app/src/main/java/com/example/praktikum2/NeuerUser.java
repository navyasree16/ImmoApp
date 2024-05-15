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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NeuerUser extends AppCompatActivity {

    private Button zurückZuMain;
    private Button zurStartseite;
    private EditText editName, editNachName, editEmail, editTelefon, editPasswort, editPasswortBest;
    private FirebaseAuth firebaseauth;
    String name, nachName, email, telefon, passwort, passwortBest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neuer_user);
        UiVerbindung();

        firebaseauth = FirebaseAuth.getInstance();


        zurückZuMain = (Button) findViewById(R.id.button6);
        zurückZuMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent zurückzurMain = new Intent(NeuerUser.this, MainActivity.class);
                startActivity(zurückzurMain);
            }
        });

        zurStartseite = (Button) findViewById(R.id.button3);
        zurStartseite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Bestaetigen()){
                    ////NUr wenn alles true dann geths weiter
                    String useremail = editEmail.getText().toString().trim();
                    String userpasswort = editPasswort.getText().toString().trim();
                    firebaseauth.createUserWithEmailAndPassword(useremail, userpasswort).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                userDatenUebertragen();
                                Toast.makeText(NeuerUser.this, "Registrieren erfolgreich!", Toast.LENGTH_SHORT).show();

                                Intent zurStartseiteintent = new Intent(NeuerUser.this, Startseite.class);
                                startActivity(zurStartseiteintent);
                            }
                            else{
                                Toast.makeText(NeuerUser.this, "Registrieren fehlgeschlagen!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else{}

            }
        });
    }
    private void UiVerbindung(){

        editName = (EditText) findViewById(R.id.editText);
        editNachName = (EditText) findViewById(R.id.editText2);
        editEmail = (EditText) findViewById(R.id.editText4);
        editTelefon = (EditText) findViewById(R.id.editText5);
        editPasswort = (EditText) findViewById(R.id.editText6);
        editPasswortBest = (EditText) findViewById(R.id.editText8);
    }

    private Boolean Bestaetigen(){

        Boolean ergebiss = false;

        name = editName.getText().toString();
        nachName = editNachName.getText().toString();
        email = editEmail.getText().toString();
        telefon = editTelefon.getText().toString();
        passwort = editPasswort.getText().toString();
        passwortBest = editPasswortBest.getText().toString();
        String pasw = passwort.toString();
        String paswB = passwortBest.toString();

        if(name.isEmpty() || nachName.isEmpty() || email.isEmpty() || telefon.isEmpty() || passwort.isEmpty() || passwortBest.isEmpty()){
            Toast.makeText(this, "@String/datenunvollständig", Toast.LENGTH_SHORT).show();
        }
        else{
            if(!pasw.equals(paswB)){
                Toast.makeText(this, "Passwörter ungleich!", Toast.LENGTH_SHORT).show();
            }
            else {
                ergebiss = true;
            }

        }

        return ergebiss;

    }


    private void userDatenUebertragen(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("User").child(firebaseauth.getUid()).child("Daten");

        User user = new User(name, nachName, email, telefon);

        myRef.setValue(user);



    }




}
