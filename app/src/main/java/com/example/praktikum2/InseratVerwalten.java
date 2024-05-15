package com.example.praktikum2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class InseratVerwalten extends AppCompatActivity {

    private TextView name, standort, flaeche, preis, zimmer, beschreibung, kontakt;
    private ImageView bild;
    private Button inseratEntfernen;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserat_verwalten);

        firebaseAuth = FirebaseAuth.getInstance();

        getIncomingIntent();


        inseratEntfernen = findViewById(R.id.button15);
        inseratEntfernen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeImobilie();
            }
        });


    }

    private void getIncomingIntent(){

        if(getIntent().hasExtra("ImoName") && getIntent().hasExtra("ImoStandort") && getIntent().hasExtra("ImoFlaeche") && getIntent().hasExtra("ImoPreis") && getIntent().hasExtra("ImoZimmer") && getIntent().hasExtra("ImoBeschreinbung") && getIntent().hasExtra("ImoBild")){
            String name = getIntent().getStringExtra("ImoName");
            String standort = getIntent().getStringExtra("ImoStandort");
            String flaeche = getIntent().getStringExtra("ImoFlaeche");
            String preis = getIntent().getStringExtra("ImoPreis");
            String zimmer = getIntent().getStringExtra("ImoZimmer");
            String beschreibung = getIntent().getStringExtra("ImoBeschreinbung");
            String bild = getIntent().getStringExtra("ImoBild");
            String kontakt = getIntent().getStringExtra("ImoKontakt");

            setData(name, standort, flaeche, preis, zimmer, beschreibung, bild, kontakt);
        }
    }

    private void setData(String Iname, String Istandort, String Iflaeche, String Ipreis, String Izimmer, String Ibeschreibung, String Ibild, String Ikontakt){
        name = findViewById(R.id.textView16);
        name.setText(Iname);

        standort = findViewById(R.id.textView17);
        standort.setText(Istandort);

        flaeche = findViewById(R.id.textView18);
        flaeche.setText(Iflaeche + "qm");

        preis = findViewById(R.id.textView19);
        preis.setText(Ipreis + "€");

        zimmer = findViewById(R.id.textView20);
        zimmer.setText(Izimmer);

        beschreibung = findViewById(R.id.textView21);
        beschreibung.setText(Ibeschreibung);

        kontakt = findViewById(R.id.textView22);
        kontakt.setText("Kontakt: " + Ikontakt);

        bild = findViewById(R.id.imageView5);
        Picasso.with(getApplicationContext()).load(Ibild).into(bild);


    }

    private void removeImobilie(){

        String fffff = getIntent().getStringExtra("ImoStandort");
        int idNummer = fffff.hashCode();
        String id = String.valueOf(idNummer);

        System.out.println(fffff + "!!!!!!!!!!!!!!!!!!!!!!!!!ID" + id );

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User").child(firebaseAuth.getUid()).child("Favoriten").child(id);
        //Query dataQuery = FirebaseDatabase.getInstance().getReference().child("User").child(firebaseAuth.getUid()).child("Favoriten").child(id);


        Intent favoritenIntent = new Intent(InseratVerwalten.this, MeineInserate.class);
        favoritenIntent.putExtra("löschen", id);
        startActivity(favoritenIntent);



    }


}
