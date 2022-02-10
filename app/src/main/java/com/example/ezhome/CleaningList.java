package com.example.ezhome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ezhome.Interface.ItemClickListener;
import com.example.ezhome.Model.Cleaning;
import com.example.ezhome.ViewHolder.CleaningViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CleaningList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference cleaningList;

    String categoryId="";

    FirebaseRecyclerAdapter<Cleaning, CleaningViewHolder>adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_cleaning_list);

        //firebase
        database = FirebaseDatabase.getInstance();
        cleaningList = database.getReference("Cleaning");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_cleaning);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //getintent
        if (getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty() && categoryId != null)
        {
            loadListCleaning(categoryId);
        }


    }

    private void loadListCleaning(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Cleaning, CleaningViewHolder>(Cleaning.class,
                R.layout.cleaning_item,
                CleaningViewHolder.class,
                cleaningList.orderByChild("MenuId").equalTo(categoryId)
        ) {
            @Override
            protected void populateViewHolder(CleaningViewHolder viewHolder, Cleaning model, int i) {
                viewHolder.cleaning_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.cleaning_image);

                final Cleaning local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
//                        Toast.makeText(CleaningList.this, ""+local.getName(), Toast.LENGTH_SHORT).show();
                        Intent servicesDetail = new Intent(CleaningList.this,ServicesDetail.class);
                        servicesDetail.putExtra("CleaningId",adapter.getRef(position).getKey());
                        startActivity(servicesDetail);

                    }
                });

            }
        };
        Log.d("TAG",""+adapter.getItemCount());
        recyclerView.setAdapter(adapter);
    }
}