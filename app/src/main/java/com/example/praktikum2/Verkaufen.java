package com.example.praktikum2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Verkaufen extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button zurueckzustart;
    private Button zuKaufen;
    private ImageView imoBild;
    private static int PICK_IMAGE = 123;
    Uri bildPath;
    private FirebaseStorage firebaseStorage;
    private EditText editName, editStandort, editFlaeche, editPreis, editZimmer, editBeschreibung;

    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent data){
        if(requestCode == PICK_IMAGE && resultcode == RESULT_OK && data.getData() != null){
            bildPath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), bildPath);
                imoBild.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultcode, data);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verkaufen);

        editName = (EditText) findViewById(R.id.editText9);
        editStandort = (EditText) findViewById(R.id.editText10);
        editFlaeche = (EditText) findViewById(R.id.editText11);
        editPreis = (EditText) findViewById(R.id.editText12);
        editZimmer = (EditText) findViewById(R.id.editText13);
        editBeschreibung = (EditText) findViewById(R.id.editText14);
        imoBild = (ImageView) findViewById(R.id.imageView3);

        imoBild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Bild aussuchen"), PICK_IMAGE);
            }
        });


        zurueckzustart = (Button) findViewById(R.id.button12);
        zurueckzustart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent zuKaufenintent = new Intent(Verkaufen.this, Startseite.class);
                startActivity(zuKaufenintent);
            }
        });

        zuKaufen = (Button) findViewById(R.id.button11);
        zuKaufen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ueberpruefen()) {
                    immobilieErstellen();

                    Intent zuKaufenintent = new Intent(Verkaufen.this, Kaufen.class);
                    startActivity(zuKaufenintent);
                }
            }
        });

    }

    private void uiVerbinden(){

        editName = (EditText) findViewById(R.id.editText9);
        editStandort = (EditText) findViewById(R.id.editText10);
        editFlaeche = (EditText) findViewById(R.id.editText11);
        editPreis = (EditText) findViewById(R.id.editText12);
        editZimmer = (EditText) findViewById(R.id.editText13);
        editBeschreibung = (EditText) findViewById(R.id.editText14);

    }



    private boolean ueberpruefen(){
        boolean ergebniss = false;

        String name = editName.getText().toString();
        String standort = editStandort.getText().toString();
        String flache = editFlaeche.getText().toString();
        String preis = editPreis.getText().toString();
        String Zimmer = editZimmer.getText().toString();
        String beschreibung = editBeschreibung.getText().toString();

        if(name.isEmpty() || standort.isEmpty() || flache.isEmpty() || preis.isEmpty() || Zimmer.isEmpty() || beschreibung.isEmpty() || bildPath == null){
            Toast.makeText(this, "@String/datenunvollständig", Toast.LENGTH_SHORT).show();
        }
        else{
            ergebniss = true;

        }

        return ergebniss;
    }

    private void immobilieErstellen() {

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        firebaseStorage = firebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();

        final String name = editName.getText().toString();
        final String standort = editStandort.getText().toString();
        final String flache = editFlaeche.getText().toString();
        final String preis = editPreis.getText().toString();
        final String zimmer = editZimmer.getText().toString();
        final String beschreibung = editBeschreibung.getText().toString();
        //String imoBildPath;
        int idNummer = standort.hashCode();
        final String id = String.valueOf(idNummer);

        final StorageReference imageReference = storageReference.child(id).child("Bild");
        final UploadTask uploadTask = imageReference.putFile(bildPath);

        ////UM AUF DAS BILD ZU ZUGREIFEN AM BESTEN EIN QUERY OBJJEKT MIT DER IMAGEREFERENCE INITIALISIEREN
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Verkaufen.this, "Upload fehlgeschlagen", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Verkaufen.this, "Upload erfolgreich", Toast.LENGTH_SHORT).show();

               String dieURL = uploadTask.getSnapshot().getUploadSessionUri().toString();





                     /////////////////////////////////////////////
                firebaseAuth.getCurrentUser().getEmail().toString();
                //String kontakt = firebaseAuth.toString();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String kontakt = user.getEmail();
                      ///////////////////////////////////////////////


                final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference myRef = firebaseDatabase.getReference("Imobilie").child(id);
                DatabaseReference myOwnRef = firebaseDatabase.getReference("User").child(firebaseAuth.getUid()).child("Imobilie").child(id);
                Imobilie imobilie = new Imobilie(id, name, standort, flache, preis, zimmer, beschreibung, dieURL, kontakt);

                myRef.setValue(imobilie);
                myOwnRef.setValue(imobilie);
            }
        });


 //       //////////////////////////////////////////////
        //StorageReference myImageReference = storageReference.child(id).child("Bild");
        //String daisses ="https://www.hurra-wir-bauen.de/_Resources/Persistent/d50b8e8191bfece3a1297c986036f2009def94ce/gussek-haus-ponticelli-aussenansicht.jpg8";//ImageReference.getDownloadUrl().toString();

   //     /////////////////////////////////////////////
        //firebaseAuth.getCurrentUser().getEmail().toString();
        //String kontakt = firebaseAuth.toString();

        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //String kontakt = user.getEmail();
  //      ///////////////////////////////////////////////


        //final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = firebaseDatabase.getReference("Imobilie").child(id);
        //DatabaseReference myOwnRef = firebaseDatabase.getReference("User").child(firebaseAuth.getUid()).child("Imobilie").child(id);
        //Imobilie imobilie = new Imobilie(id, name, standort, flache, preis, zimmer, beschreibung, daisses, kontakt);

        //myRef.setValue(imobilie);
        //myOwnRef.setValue(imobilie);


    }



}
