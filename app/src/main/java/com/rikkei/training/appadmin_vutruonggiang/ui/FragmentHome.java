package com.rikkei.training.appadmin_vutruonggiang.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.rikkei.training.appadmin_vutruonggiang.R;

public class FragmentHome extends Fragment {
    private View view;
    private ConstraintLayout manageFood;
    private ConstraintLayout manageRes;
    private ConstraintLayout manageCart;
    private ConstraintLayout manageStatistical;
    MainActivity mainActivity;

    public static Fragment newInstance() {

        Bundle args = new Bundle();

        FragmentHome fragment = new FragmentHome();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        manageRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getFragment(FragmentRestaurant.newInstance());
            }
        });
        return view;
    }

    public void init() {
        mainActivity = (MainActivity) getActivity();
        manageCart = view.findViewById(R.id.manageCart);
        manageFood = view.findViewById(R.id.manageFood);
        manageRes = view.findViewById(R.id.manageRestaurant);
        manageStatistical = view.findViewById(R.id.manageStatistical);
    }
}
