package com.example.taskmaster;

import android.preference.PreferenceScreen;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView title, body, state;
    ImageView image;
    ItemClickListener itemClickListener;

    public Holder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.modelTitle);
        body = itemView.findViewById(R.id.modelBody);
        state = itemView.findViewById(R.id.modelState);
        image = itemView.findViewById(R.id.image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(view , getLayoutPosition());
    }
    public void setItemClickListener(ItemClickListener ic){
        this.itemClickListener = ic;
    }

}
