package com.bhanguz.lump.adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bhanguz.lump.R;
import com.bhanguz.lump.model.ModelChatDetailData;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class ShowChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ModelChatDetailData> modelChatDetailData;
    Context context;
    public static final int MESSAGE_TYPE_IN = 0;
    public static final int MESSAGE_TYPE_OUT = 1;
    public ShowChatAdapter(ArrayList<ModelChatDetailData> model, Context context) {
        this.modelChatDetailData = model;
        this.context = context;
    }

    private class MessageInViewHolder extends RecyclerView.ViewHolder {

        TextView textView,dateArea11;
         ImageView type1,chatimagetxt1,imagemsg1;
        LinearLayout image_layoutin1,linear11;
        MessageInViewHolder(final View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.messageArea);
             type1 = itemView.findViewById(R.id.imageAreain);

            // chatimagetxt1 =itemView.findViewById(R.id.chatimagein);
             image_layoutin1=itemView.findViewById(R.id.image_layoutin);
             linear11=itemView.findViewById(R.id.linear);
             //imagemsg1=itemView.findViewById(R.id.imagemsgin0);
        }

        void bind(int position) {

            if(modelChatDetailData.get(position).getMessage_type().equalsIgnoreCase("image"))

            {
                image_layoutin1.setVisibility(View.VISIBLE);
                linear11.setVisibility(View.GONE);
                Picasso.get().load(context.getString(R.string.img_url) + modelChatDetailData.get(position).getMessage()).into(type1);
      //          Picasso.get().load(context.getString(R.string.img_url) + modelChatDetailData.get(position).getImage()).placeholder(R.drawable.placeholder).into(chatimagetxt1);


            }
            else{
                image_layoutin1.setVisibility(View.GONE);
                linear11.setVisibility(View.VISIBLE);
                textView.setText(modelChatDetailData.get(position).getMessage());

              //  Picasso.get().load(context.getString(R.string.img_url)+modelChatDetailData.get(position).getImage()).placeholder(R.drawable.placeholder).into(imagemsg1);

            }


        }}
    private class MessageOutViewHolder extends RecyclerView.ViewHolder {

        TextView textView1,dateArea12;
        ImageView type,userout_image1,image22;


        LinearLayout image_layoutout,linear2;
        MessageOutViewHolder(final View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.messageArea2);
            type =itemView.findViewById(R.id.imageAreaout);
            image_layoutout =itemView.findViewById(R.id.image_layoutout);
            linear2 =itemView.findViewById(R.id.linear2);
           // image22=itemView.findViewById(R.id.image2);
           // userout_image1=itemView.findViewById(R.id.userout_image);
        }

        void bind(int position) {

            if (modelChatDetailData.get(position).getMessage_type().equalsIgnoreCase("image")){
                image_layoutout.setVisibility(View.VISIBLE);
                linear2.setVisibility(View.GONE);
           //   dateArea12.setText(modelChatDetailData.get(position).getDate());

//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//                try {
//                    long time = sdf.parse("2020-01-24T16:00:00.000Z").getTime();
//                  // long time =sdf.parse(modelChatDetailData.get(position).getDate());
//                    long now = System.currentTimeMillis();
//                    CharSequence ago =
//                            DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
//
//                    dateArea12.setText(ago);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
               Picasso.get().load(context.getString(R.string.img_url)+modelChatDetailData.get(position).getMessage()).into(type);
               //Picasso.get().load(context.getString(R.string.img_url)+modelChatDetailData.get(position).getImage()).placeholder(R.drawable.placeholder).into(userout_image1);

//

            }
            else {
                image_layoutout.setVisibility(View.GONE);
                linear2.setVisibility(View.VISIBLE);
           //    dateArea12.setText(modelChatDetailData.get(position).getDate());
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//                try {
//                    long time = sdf.parse("2020-01-24T16:00:00.000Z").getTime();
//                    long now = System.currentTimeMillis();
//                    CharSequence ago =
//                            DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
//
//                    dateArea12.setText(ago);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
                textView1.setText(modelChatDetailData.get(position).getMessage());
           //     Picasso.get().load(context.getString(R.string.img_url)+modelChatDetailData.get(position).getImage()).placeholder(R.drawable.placeholder).into(image22);
            }
//            textView1.setText(modelChatDetailData.get(position).getMessage());
           // type.

        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MESSAGE_TYPE_IN) {
            return new MessageInViewHolder(LayoutInflater.from(context).inflate(R.layout.item_text_in, parent, false));
        }
        return new MessageOutViewHolder(LayoutInflater.from(context).inflate(R.layout.item_text_out, parent, false));
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (modelChatDetailData.get(position).getType() == MESSAGE_TYPE_IN) {
            ((MessageInViewHolder) holder).bind(position);
        } else {
            ((MessageOutViewHolder) holder).bind(position);
        }
    }


    @Override
    public int getItemCount() {
        return modelChatDetailData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return modelChatDetailData.get(position).getType();
    }
}
