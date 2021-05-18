package com.example.taskmaster;

import android.preference.PreferenceScreen;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView title, body, state;
    ItemClickListener itemClickListener;

    public Holder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.modelTitle);
        body = itemView.findViewById(R.id.modelBody);
        state = itemView.findViewById(R.id.modelState);

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
