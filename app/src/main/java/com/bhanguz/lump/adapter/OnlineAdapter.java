package com.bhanguz.lump.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bhanguz.lump.R;
import com.bhanguz.lump.activity.ChatActivity;
import com.bhanguz.lump.activity.FrienddetailActivity;
import com.bhanguz.lump.fragment.OnlineFragment;
import com.bhanguz.lump.model.ListOnlineModel;
import com.bhanguz.lump.model.ListOnlinePeople;
import com.bhanguz.lump.model.ModelFavData;
import com.bhanguz.lump.utilities.ApiUrl;
import com.bhanguz.lump.utilities.SavedSharedPreference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OnlineAdapter extends RecyclerView.Adapter<OnlineAdapter.ViewHolder> {
    private static List<ListOnlinePeople> onlinelistModel;
    private static Context context;

    public OnlineAdapter(List<ListOnlinePeople> onlinelistModel, Context context) {
        this.onlinelistModel = onlinelistModel;
        this.context = context;
    }


    @Override
    public OnlineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.onlinelayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onlineusername.setText(onlinelistModel.get(position).getUsername());
        holder.age1.setText(onlinelistModel.get(position).getAge());
        Picasso.get().load(context.getString(R.string.img_url)+onlinelistModel.get(position)
                .getUser_image()).placeholder(R.drawable.placeholder).into(holder.onlineimage);

      //  Picasso.get().load(context.getString(R.string.img_url)+onlinelistModel.get(position).getUser_image()).into(holder.onlineimage);
//        holder.chatbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(context, ChatActivity.class);
//                i.putExtra("username","usernameusername");
//                i.putExtra("image", "userfriendpic1");
//                i.putExtra("user_id", "user_id");
//                i.putExtra("friend_id",onlinelistModel.get(position).getUser_id());
//                context.startActivity(i);
//            }
//        });

        holder.onliecard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, FrienddetailActivity.class);
                i.putExtra("username", onlinelistModel.get(position).getUsername());
                i.putExtra("country", onlinelistModel.get(position).getAge());
                i.putExtra("image", onlinelistModel.get(position).getUser_image());
                i.putExtra("user_id",onlinelistModel.get(position).getUser_id());

                context.startActivity(i);
            }
        });
   }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return onlinelistModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView chatbtn,userfriendpic1;
        TextView usernameusername;
        public ImageView onlineimage;
        public TextView onlineusername;
        public TextView age1;
        CardView onliecard1;
        String user_id= SavedSharedPreference.getKey(context,"KEY_user_id");

        String friend_id,pic,usernameusername1;
        public ViewHolder(View v) {
            super(v);
          chatbtn = (ImageView) v.findViewById(R.id.chaticon);
          onlineusername=v.findViewById(R.id.userfriendname);
          onlineimage=v.findViewById(R.id.userfriendpic);
          onliecard1=v.findViewById(R.id.onliecard);
          age1=v.findViewById(R.id.userfriendlocation);



        }
    }


}



