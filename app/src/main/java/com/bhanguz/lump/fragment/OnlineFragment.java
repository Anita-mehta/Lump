package com.bhanguz.lump.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bhanguz.lump.R;
import com.bhanguz.lump.adapter.FanAdapter;
import com.bhanguz.lump.adapter.OnlineAdapter;
import com.bhanguz.lump.model.ListOnlineModel;
import com.bhanguz.lump.model.ListOnlinePeople;
import com.bhanguz.lump.model.ModelFav;
import com.bhanguz.lump.model.ModelFavData;
import com.bhanguz.lump.utilities.ApiUrl;
import com.bhanguz.lump.utilities.SavedSharedPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineFragment extends Fragment {
    private RecyclerView recyclerView;
    FrameLayout mloaderLayout1;
    static List<ListOnlinePeople> onlinelistModel;
    OnlineAdapter onlineAdapter;
    //private RecyclerView.LayoutManager layoutManager;
    private LinearLayoutManager layoutManager;
    private Context context;
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_online, container, false);
        recyclerView =  view.findViewById(R.id.Onlineview);
        mloaderLayout1=view.findViewById(R.id.mloaderLayout);
        onlinelistModel = new ArrayList<>();
        // layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
//        layoutManager = new GridLayoutManager(getContext(),2);
//        recyclerView.setLayoutManager(layoutManager);
//        mAdapter = new OnlineAdapter();
//        recyclerView.setAdapter(mAdapter);
        OnlineuserApi();
       return view;
    }
    private void OnlineuserApi() {

        mloaderLayout1.setVisibility(View.VISIBLE);

        String uname="bhanguz";
        String token="012d%lump%4071";
        String user_id= SavedSharedPreference.getKey(getActivity(),"KEY_user_id");
        ApiUrl.getAllClient().getlistOnline(uname,token,user_id).enqueue(new Callback<ListOnlineModel>() {
            @Override
            public void onResponse(Call<ListOnlineModel> call, Response<ListOnlineModel> response) {
                mloaderLayout1.setVisibility(View.GONE);

                if (response.isSuccessful() && null != response.body()) {
                    ListOnlineModel getDistributorResponse = response.body();
                    if (getDistributorResponse.getError().equalsIgnoreCase("0")) {
                        onlinelistModel = getDistributorResponse.getData();
                        onlineAdapter= new OnlineAdapter(onlinelistModel,context);
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        recyclerView.setAdapter(onlineAdapter);
                    } else {
                        Toast.makeText(getContext(), getDistributorResponse.getError(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ListOnlineModel> call, Throwable t) {
                mloaderLayout1.setVisibility(View.GONE);
                Log.e("onFailureFav: ", t.toString());
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

}