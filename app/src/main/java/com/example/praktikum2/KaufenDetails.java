package com.example.praktikum2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class KaufenDetails extends AppCompatActivity {

    private TextView name, standort, flaeche, preis, zimmer, beschreibung, kontakt;
    private ImageView bild;
    private Button zuFavoriten;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaufen_details);

        getIncomingIntent();

        zuFavoriten = findViewById(R.id.button13);
        zuFavoriten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTofavorites();

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
        name = findViewById(R.id.textView2);
        name.setText(Iname);

        standort = findViewById(R.id.textView3);
        standort.setText(Istandort);

        flaeche = findViewById(R.id.textView4);
        flaeche.setText(Iflaeche + "qm");

        preis = findViewById(R.id.textView5);
        preis.setText(Ipreis + "€");

        zimmer = findViewById(R.id.textView6);
        zimmer.setText(Izimmer);

        beschreibung = findViewById(R.id.textView7);
        beschreibung.setText(Ibeschreibung);

        kontakt = findViewById(R.id.textView8);
        kontakt.setText("Kontakt: " + Ikontakt);

        bild = findViewById(R.id.imageView2);
        Picasso.with(getApplicationContext()).load(Ibild).into(bild);


    }

    private void addTofavorites(){

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        firebaseStorage = firebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();


        if(getIntent().hasExtra("ImoName") && getIntent().hasExtra("ImoStandort") && getIntent().hasExtra("ImoFlaeche") && getIntent().hasExtra("ImoPreis") && getIntent().hasExtra("ImoZimmer") && getIntent().hasExtra("ImoBeschreinbung") && getIntent().hasExtra("ImoBild")){
            String name = getIntent().getStringExtra("ImoName");
            String standort = getIntent().getStringExtra("ImoStandort");
            String flaeche = getIntent().getStringExtra("ImoFlaeche");
            String preis = getIntent().getStringExtra("ImoPreis");
            String zimmer = getIntent().getStringExtra("ImoZimmer");
            String beschreibung = getIntent().getStringExtra("ImoBeschreinbung");
            String bild = getIntent().getStringExtra("ImoBild");
            String kontakt = getIntent().getStringExtra("ImoKontakt");

            int idNummer = standort.hashCode();
            String id = String.valueOf(idNummer);



            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference myOwnRef = firebaseDatabase.getReference("User").child(firebaseAuth.getUid()).child("Favoriten").child(id);
            Imobilie imobilie = new Imobilie(id, name, standort, flaeche, preis, zimmer, beschreibung, bild, kontakt);

            myOwnRef.setValue(imobilie);

        }
    }



}
