package com.bhanguz.lump.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bhanguz.lump.R;
import com.bhanguz.lump.activity.ChatActivity;
import com.bhanguz.lump.model.ModelChatDetailData;
import com.bhanguz.lump.model.ModelMessageData;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private static ArrayList<ModelMessageData> modelMessageData;
    static Context context;

    public MessageAdapter(ArrayList<ModelMessageData> model, Context context) {
        this.modelMessageData = model;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.name.setText(modelMessageData.get(position).getUsername());

//        if (modelMessageData.get(position).getMessage_type().equals("image")){
//            holder.message.setText("Image");
//        }
//        else {
//            holder.message.setText(modelMessageData.get(position).getMessage());
//        }
//        if (modelMessageData.get(position).getMessage().contains(".jpg")){
//            holder.message.setText("Image");
//        }
//        else {
//            holder.message.setText(modelMessageData.get(position).getMessage());
//

        holder.message.setText(modelMessageData.get(position).getMessage());
        //   holder.date1.setText(modelMessageData.get(position).getCreated_at());
//        Picasso.get().load("http://103.43.153.37:8060/apis/" + modelMessageData.get(position).getImage())
//                .placeholder(R.drawable.ic_woman).into(holder.imageView);
        Picasso.get().load(context.getString(R.string.img_url) +
                modelMessageData.get(position).getImage()).placeholder(R.drawable.placeholder).into(holder.imageView1);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ChatActivity.class);
                i.putExtra("username", modelMessageData.get(position).getUsername());
                i.putExtra("client_id", modelMessageData.get(position).getUserId());
                i.putExtra("friend_id", modelMessageData.get(position).getFriendId());
                i.putExtra("image", modelMessageData.get(position).getImage());
                context.startActivity(i);
            }
        });

        if (modelMessageData.get(position).getChatId().equals("")) {
            holder.relativeLayout1.setVisibility(View.GONE);
        } else {
            holder.relativeLayout1.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return modelMessageData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView1;
        public TextView name, date1;
        public RelativeLayout relativeLayout1;

        public TextView message;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView1 = (ImageView) itemView.findViewById(R.id.imageView);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.message = (TextView) itemView.findViewById(R.id.message);
            this.date1 = itemView.findViewById(R.id.date);
            this.relativeLayout1 = (RelativeLayout) itemView.findViewById(R.id.relativeLayout1);

        }

    }
}


