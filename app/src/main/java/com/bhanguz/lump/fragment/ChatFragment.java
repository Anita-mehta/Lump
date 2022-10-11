package com.bhanguz.lump.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bhanguz.lump.R;
import com.bhanguz.lump.adapter.MessageAdapter;
import com.bhanguz.lump.model.ModelMessage;
import com.bhanguz.lump.model.ModelMessageData;
import com.bhanguz.lump.utilities.ApiUrl;
import com.bhanguz.lump.utilities.SavedSharedPreference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatFragment extends Fragment {
    private ArrayList<ModelMessageData> modelChatDetails;
    RecyclerView recyclerView;
    FrameLayout mloaderLayout1;
    private Context context;
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView=view.findViewById(R.id.recyclerViewchat);
        mloaderLayout1=view.findViewById(R.id.mloaderLayout);
        getNewChat();
        return view;
    }
    private  void getNewChat(){
        //showProgressDialog();
        mloaderLayout1.setVisibility(View.VISIBLE);
        String uname = "bhanguz";
        String token = "012d%lump%4071";
        String user_id = SavedSharedPreference.getKey(getActivity(), "KEY_user_id");

        Log.e("user_id: ", user_id);

        modelChatDetails = new ArrayList<>();

        ApiUrl.getAllClient().getChatList(user_id,uname, token).enqueue(new Callback<ModelMessage>() {
            @Override
            public void onResponse(Call<ModelMessage> call, Response<ModelMessage> response) {
                mloaderLayout1.setVisibility(View.GONE);
                if(response.isSuccessful() && null!=response.body()) {
                    ModelMessage getDistributorResponse = response.body();
                    if (getDistributorResponse.getError().equalsIgnoreCase("0")) {

                        modelChatDetails.addAll(getDistributorResponse.getData());
                        MessageAdapter newChatAdapter = new MessageAdapter(modelChatDetails,getActivity());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
                        recyclerView.setAdapter(newChatAdapter);

                    }
                    else {
                        Toast.makeText(getContext(), getDistributorResponse.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelMessage> call, Throwable t) {
                Log.e("onFailure: ", t.toString());
                Toast.makeText(getContext(), "No Chat Found", Toast.LENGTH_SHORT).show();
                mloaderLayout1.setVisibility(View.GONE);
            }
        });

    }}