package com.example.praktikum2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Startseite extends AppCompatActivity {

    private Button zuKaufen;
    private Button zuVerkaufen;
    private Button zuMeineInserate;
    private Button zuMeineFavoriten;
    private Button zuEinstellungen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startseite);

        zuKaufen = (Button) findViewById(R.id.button2);
        zuKaufen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent zuKaufenintent = new Intent(Startseite.this, Kaufen.class);
                startActivity(zuKaufenintent);
            }
        });

        zuVerkaufen = (Button) findViewById(R.id.button4);
        zuVerkaufen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent zuVerkaufenintent = new Intent(Startseite.this, Verkaufen.class);
                startActivity(zuVerkaufenintent);
            }
        });

        zuMeineInserate = (Button) findViewById(R.id.button7);
        zuMeineInserate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent zuMeinenInseratenintent = new Intent(Startseite.this, MeineInserate.class);
                startActivity(zuMeinenInseratenintent);
            }
        });

        zuMeineFavoriten = (Button) findViewById(R.id.button8);
        zuMeineFavoriten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent zuMeinenFavoritenintent = new Intent(Startseite.this, MeineFavoriten.class);
                startActivity(zuMeinenFavoritenintent);
            }
        });

        zuEinstellungen = (Button) findViewById(R.id.button9);
        zuEinstellungen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent zurEinstellungintent = new Intent(Startseite.this, Einstellung.class);
                startActivity(zurEinstellungintent);
            }
        });
    }
}
