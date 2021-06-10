package com.example.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Holder>{

    Context c ;
    List<Tasks> models;

    public Adapter(Context c, List<Tasks> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.task_model,null);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.title.setText(models.get(position).getTitle());
        holder.body.setText(models.get(position).getBody());
        holder.state.setText(models.get(position).getState());
        Glide.with(c).load(models.get(position).getImage()).into(holder.image);

        //animation to determain the display the view

        Animation animation = AnimationUtils.loadAnimation(c,android.R.anim.slide_in_left);
        //start animation
        holder.itemView.startAnimation(animation);
        // use when you want to put each item data to same activity
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //get data from item clicked
                int id = models.get(pos).getId();
                String title = models.get(pos).getTitle();
                String body = models.get(pos).getBody();
                String state = models.get(pos).getState();
                String image = models.get(pos).getImage();



                Intent intent = new Intent(c , TaskDetail.class);
                intent.putExtra("id", id);
                intent.putExtra("title", title);
                intent.putExtra("body", body);
                intent.putExtra("state", state);
                intent.putExtra("image", image);

                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
