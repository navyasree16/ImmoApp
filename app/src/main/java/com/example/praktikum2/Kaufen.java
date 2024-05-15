package com.example.praktikum2;

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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Kaufen extends AppCompatActivity {

    private RecyclerView blockList;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    private FirebaseStorage firebaseStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaufen);


        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Imobilie");
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
            protected void populateViewHolder(final BlogViewHolder viewHolder, final Imobilie model, int position){

                viewHolder.setTitle(model.getImobilien_name());//model.getImobilien_name()
                viewHolder.setDesc(model.getBeschreibung());//model.getBeschreibung()
                viewHolder.setImage(getApplicationContext(), model.getImage());//getApplicationContext(), model.getImage()

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent zuKaufenDetailsIntent = new Intent(Kaufen.this, KaufenDetails.class);
                        zuKaufenDetailsIntent.putExtra("ImoName", model.getImobilien_name());
                        zuKaufenDetailsIntent.putExtra("ImoStandort", model.getStandort());
                        zuKaufenDetailsIntent.putExtra("ImoFlaeche", model.getFlaeche());
                        zuKaufenDetailsIntent.putExtra("ImoPreis", model.getPreis());
                        zuKaufenDetailsIntent.putExtra("ImoZimmer", model.getZimmer());
                        zuKaufenDetailsIntent.putExtra("ImoBeschreinbung", model.getBeschreibung());
                        zuKaufenDetailsIntent.putExtra("ImoBild", model.getImage());
                        zuKaufenDetailsIntent.putExtra("ImoKontakt", model.getKonatkt());
                        startActivity(zuKaufenDetailsIntent);
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





}
