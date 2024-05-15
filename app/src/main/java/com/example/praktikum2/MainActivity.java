package com.example.praktikum2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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


public class MainActivity extends AppCompatActivity{

    private Button neuerUser;
    private Button zurStartseite;
    private EditText useremail, userpasswort;
    private FirebaseAuth firebaseauth;
    private ProgressDialog progressdialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        firebaseauth = FirebaseAuth.getInstance();
        progressdialog = new ProgressDialog(this);

        FirebaseUser benutzer = firebaseauth.getCurrentUser();

        useremail = (EditText) findViewById(R.id.editText3);
        userpasswort = (EditText) findViewById(R.id.editText7);


        if(benutzer != null){
            finish();
            Intent zurStartseiteintent = new Intent(MainActivity.this, Startseite.class);
            startActivity(zurStartseiteintent);

        }


        neuerUser = (Button) findViewById(R.id.button);
        neuerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent neueruser = new Intent(MainActivity.this, NeuerUser.class);
                startActivity(neueruser);
            }
        });

        zurStartseite = (Button) findViewById(R.id.button5);
        zurStartseite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = useremail.getText().toString();
                String passwort = userpasswort.getText().toString();

                if(email.isEmpty() || passwort.isEmpty()){
                    Toast.makeText(MainActivity.this, "@String/datenunvollständig", Toast.LENGTH_SHORT).show();
                }
                else{

                    bestaetigen(useremail.getText().toString(), userpasswort.getText().toString());


                   // Intent zurStartseiteintent = new Intent(MainActivity.this, Startseite.class);
                   // startActivity(zurStartseiteintent);

                }

            }
        });
    }



    private void bestaetigen(String useremail, String userpasswort){
        firebaseauth.signInWithEmailAndPassword(useremail, userpasswort).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent zurStartseiteintent = new Intent(MainActivity.this, Startseite.class);
                    startActivity(zurStartseiteintent);
                }
                else{
                    Toast.makeText(MainActivity.this, "Anmelden fehlgeschlagen!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
