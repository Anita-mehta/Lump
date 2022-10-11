package com.bhanguz.lump.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bhanguz.lump.R;
import com.bhanguz.lump.activity.FrienddetailActivity;
import com.bhanguz.lump.model.ModelFavData;
import com.bhanguz.lump.utilities.ApiUrl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FanAdapter  extends RecyclerView.Adapter<FanAdapter.ViewHolder> {
    private static ArrayList<ModelFavData> modelFavData;
    private static Context context;
    public FanAdapter(ArrayList<ModelFavData> modelFavData, Context context) {
        this.modelFavData = modelFavData;
        this.context = context;
    }
    public FanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.fanlayout, parent, false);

        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FanAdapter.ViewHolder holder, final int position) {
        holder.textView.setText(modelFavData.get(position).getUsername());
        holder.textView2.setText(modelFavData.get(position).getCountry());
      //  Picasso.get().load(ApiUrl.BASE_url_image + modelFavData.get(position).getUserImage()).placeholder(R.drawable.profilegirl).into(holder.imageView);

         Picasso.get().load(context.getString(R.string.img_url)+modelFavData.get(position).getImage()).placeholder(R.drawable.placeholder).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, FrienddetailActivity.class);
                i.putExtra("username", modelFavData.get(position).getUsername());
                i.putExtra("country", modelFavData.get(position).getCountry());
                i.putExtra("image", modelFavData.get(position).getUserImage());
                i.putExtra("user_id",modelFavData.get(position).getUser_id());

                context.startActivity(i);
            }
        });


    }
    public int getItemCount() {
        return modelFavData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public ImageView imageView;
        public TextView textView;
        public TextView textView2;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            imageView = itemView.findViewById(R.id.userfriendpic1);
            textView = itemView.findViewById(R.id.userfriendname);
            textView2 = itemView.findViewById(R.id.userfriendlocation);
            cardView = itemView.findViewById(R.id.fancard);




        }}}



