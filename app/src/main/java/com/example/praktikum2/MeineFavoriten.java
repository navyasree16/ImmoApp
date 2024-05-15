package com.example.praktikum2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MeineFavoriten extends AppCompatActivity {

    private RecyclerView blockList;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    private FirebaseStorage firebaseStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaufen);

        getIncomingIntent();


        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(firebaseAuth.getUid()).child("Favoriten");
        databaseReference.keepSynced(true);
        blockList =  (RecyclerView) findViewById(R.id.myrecyclerview);
        blockList.setHasFixedSize(true);
        blockList.setLayoutManager(new LinearLayoutManager(this));


    }


    @Override
    protected void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<Imobilie, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Imobilie, BlogViewHolder>
                (Imobilie.class, R.layout.block, BlogViewHolder.class, databaseReference){
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, final Imobilie model, int position){

                viewHolder.setTitle(model.getImobilien_name());//model.getImobilien_name()
                viewHolder.setDesc(model.getBeschreibung());//model.getBeschreibung()
                viewHolder.setImage(getApplicationContext(), model.getImage());//getApplicationContext(), model.getImage()

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent zuFavoritenDetailsIntent = new Intent(MeineFavoriten.this, FavoritenDetails.class);
                        zuFavoritenDetailsIntent.putExtra("ImoName", model.getImobilien_name());
                        zuFavoritenDetailsIntent.putExtra("ImoStandort", model.getStandort());
                        zuFavoritenDetailsIntent.putExtra("ImoFlaeche", model.getFlaeche());
                        zuFavoritenDetailsIntent.putExtra("ImoPreis", model.getPreis());
                        zuFavoritenDetailsIntent.putExtra("ImoZimmer", model.getZimmer());
                        zuFavoritenDetailsIntent.putExtra("ImoBeschreinbung", model.getBeschreibung());
                        zuFavoritenDetailsIntent.putExtra("ImoBild", model.getImage());
                        zuFavoritenDetailsIntent.putExtra("ImoKontakt", model.getKonatkt());
                        startActivity(zuFavoritenDetailsIntent);
                    }
                });


            }
        };
        blockList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public BlogViewHolder(View itemView){
            super(itemView);
            mView = itemView;

        }
        public void setTitle(String title){
            TextView post_title = (TextView)mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }
        public void setDesc(String desc){
            TextView post_description = (TextView)mView.findViewById(R.id.post_description);
            post_description.setText(desc);
        }
        public void setImage(Context ctx, String image){
            ImageView post_image = (ImageView)mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);
        }



    }




    private void getIncomingIntent(){

        if(getIntent().hasExtra("löschen")){

            String fff = getIntent().getStringExtra("löschen");
            int idNummer = fff.hashCode();
            String id = String.valueOf(idNummer);
            firebaseAuth = FirebaseAuth.getInstance();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();//.child("User").child(firebaseAuth.getUid()).child("Favoriten");
            Query dataQuery = ref.child("User").child(firebaseAuth.getUid()).child("Favoriten").orderByChild("id").equalTo(fff);
            //ref.removeValue();

            System.out.println("DAS IST DIE ID !!!!!! -> "+ fff);

            dataQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


              for (DataSnapshot imoSnapshot : dataSnapshot.getChildren()){
                 imoSnapshot.getRef().removeValue();
               }
             }

              @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MeineFavoriten.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
             }
            });

        }
    }



}
