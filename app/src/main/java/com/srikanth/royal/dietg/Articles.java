package com.srikanth.royal.dietg;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.srikanth.royal.dietg.Model.Items;
import com.srikanth.royal.dietg.ViewHolder.AirtcleViewHolder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class Articles extends Fragment {

    public Articles() {

    }
    private DatabaseReference Iref;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private CircleImageView bmi,calorie;
    private long bpt;
    private Toast bt;






    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Items> options = new FirebaseRecyclerOptions.Builder<Items>()
                .setQuery(Iref, Items.class)
                .build();


        FirebaseRecyclerAdapter<Items, AirtcleViewHolder> adapter = new FirebaseRecyclerAdapter<Items, AirtcleViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AirtcleViewHolder holder, int position, @NonNull final Items model) {
                holder.title.setText(model.getTitle());
                holder.Sdescription.setText(model.getShortdescription());
                Picasso.get()
                        .load(model.getImage())
                        .placeholder(R.drawable.lbar)
                        .into(holder.image);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(), ArticleDetails.class);
                        intent.putExtra("pid", model.getIid());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public AirtcleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
                AirtcleViewHolder holder = new AirtcleViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_frag_articles, container, false);
        Iref = FirebaseDatabase.getInstance().getReference().child("Items");

        recyclerView=(RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        bmi=(CircleImageView)rootView.findViewById(R.id.bmi);
        calorie=(CircleImageView)rootView.findViewById(R.id.calorie);
        
        bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(),Bmi.class);
                startActivity(intent);
            }
        });

        calorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(),calorieC.class);
                startActivity(intent);
            }
        });



        return rootView;
    }


}
