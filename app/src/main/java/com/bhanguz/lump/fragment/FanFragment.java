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
import com.bhanguz.lump.model.ModelFav;
import com.bhanguz.lump.model.ModelFavData;
import com.bhanguz.lump.utilities.ApiUrl;
import com.bhanguz.lump.utilities.SavedSharedPreference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FanFragment extends Fragment {
    RecyclerView recyclerView;
    FrameLayout mloaderLayout1;
    static ArrayList<ModelFavData> modelFavData;
    FanAdapter favourite;
    private Context context;
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fan, container, false);
        recyclerView =  view.findViewById(R.id.Onlineview);
        mloaderLayout1=view.findViewById(R.id.mloaderLayout);

        getFav_func();
        return view;
    }
    private void getFav_func() {

        mloaderLayout1.setVisibility(View.VISIBLE);

        String uname="bhanguz";
        String token="012d%lump%4071";
        String user_id= SavedSharedPreference.getKey(getActivity(),"KEY_user_id");
        modelFavData = new ArrayList<>();
        ApiUrl.getAllClient().getFavData(user_id,uname,token).enqueue(new Callback<ModelFav>() {
            @Override
            public void onResponse(Call<ModelFav> call, Response<ModelFav> response) {
                mloaderLayout1.setVisibility(View.GONE);

                if (response.isSuccessful() && null != response.body()) {
                    ModelFav getDistributorResponse = response.body();
                    if (getDistributorResponse.getError().equalsIgnoreCase("1")) {
                        modelFavData.addAll(getDistributorResponse.getData());
                        favourite = new FanAdapter(modelFavData,getActivity());
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        recyclerView.setAdapter(favourite);
                    } else {
                        Toast.makeText(getContext(), getDistributorResponse.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ModelFav> call, Throwable t) {
                mloaderLayout1.setVisibility(View.GONE);
                Log.e("onFailureFav: ", t.toString());
                Toast.makeText(getContext(), "No friend", Toast.LENGTH_SHORT).show();

            }
        });
    }

}