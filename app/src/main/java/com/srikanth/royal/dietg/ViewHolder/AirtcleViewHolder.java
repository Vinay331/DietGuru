package com.srikanth.royal.dietg.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.srikanth.royal.dietg.Interface.itemclicklistener;
import com.srikanth.royal.dietg.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AirtcleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView title,Sdescription;
    public ImageView image;
    public itemclicklistener listener;

    public AirtcleViewHolder(@NonNull View itemView) {
        super(itemView);

        image=(ImageView)itemView.findViewById(R.id.Aimage);
        title=(TextView)itemView.findViewById(R.id.ATitle);
        Sdescription=(TextView)itemView.findViewById(R.id.Asd);
    }

    public void setItemClickListener(itemclicklistener listener)
    {
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
     listener.onClick(v,getAdapterPosition(),false);
    }
}
