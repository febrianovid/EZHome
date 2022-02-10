package com.example.ezhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ezhome.Database.Database;
import com.example.ezhome.Model.Cleaning;
import com.example.ezhome.Model.Order;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ServicesDetail extends AppCompatActivity {

    TextView cleaning_name,cleaning_price,cleaning_description;
    ImageView cleaning_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String cleaningId="";

    FirebaseDatabase database;
    DatabaseReference services;

    Cleaning currentCleaning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_detail);

        database = FirebaseDatabase.getInstance();
        services = database.getReference("Cleaning");

        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
        btnCart = (FloatingActionButton)findViewById(R.id.btnCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        cleaningId,
                        currentCleaning.getName(),
                        numberButton.getNumber(),
                        currentCleaning.getPrice(),
                        currentCleaning.getDiscount()

                ));

                Toast.makeText(ServicesDetail.this, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });

        cleaning_description = (TextView)findViewById(R.id.cleaning_description);
        cleaning_name = (TextView)findViewById(R.id.cleaning_name);
        cleaning_price = (TextView)findViewById(R.id.cleaning_price);
        cleaning_image = (ImageView)findViewById(R.id.cleaning_image);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        if (getIntent() != null)
            cleaningId = getIntent().getStringExtra("CleaningId");
        if (!cleaningId.isEmpty())
        {
            getDetailCleaning(cleaningId);
        }
    }

    private void getDetailCleaning(String cleaningId) {
        services.child(cleaningId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentCleaning = dataSnapshot.getValue(Cleaning.class);

                Picasso.with(getBaseContext()).load(currentCleaning.getImage())
                        .into(cleaning_image);

                collapsingToolbarLayout.setTitle(currentCleaning.getName());

                cleaning_price.setText(currentCleaning.getPrice());

                cleaning_name.setText(currentCleaning.getName());

                cleaning_description.setText(currentCleaning.getDescription());

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }
}