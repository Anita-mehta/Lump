package com.bhanguz.lump.fragment;

import android.R.color;
import android.app.Dialog;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bhanguz.lump.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

public class Bottomsheet extends BottomSheetDialogFragment {
    TextView close1;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

            BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
            dialog.setOnShowListener(dialog1 -> {
                BottomSheetDialog d = (BottomSheetDialog) dialog1;

                FrameLayout bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                bottomSheet.setBackground(null);
                BottomSheetBehavior.from(Objects.requireNonNull(bottomSheet)).setState(BottomSheetBehavior.STATE_EXPANDED);
            });




        return dialog;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.detaillayout,container,false);
       close1=view.findViewById(R.id.close);
        close1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        dismiss();
            }
        });
        return view;
    }

}
