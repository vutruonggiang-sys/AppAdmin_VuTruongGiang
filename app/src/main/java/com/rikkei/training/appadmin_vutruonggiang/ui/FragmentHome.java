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
    private ConstraintLayout manageRes;
    private ConstraintLayout manageCart;
    private ConstraintLayout manageStatistical;
    MainActivity mainActivity;
    String idRes="";

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
        Bundle bundle=getArguments();
        if(bundle!=null){
            idRes=idRes+bundle.getString("IdRes","");
        }
        manageRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentRestaurant fragmentRestaurant=new FragmentRestaurant();
                Bundle bundle1=new Bundle();
                bundle1.putString("IdRes",idRes);
                fragmentRestaurant.setArguments(bundle1);
                mainActivity.getFragment(fragmentRestaurant);
            }
        });
        manageCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentBill fragment=new FragmentBill();
                Bundle bundle1=new Bundle();
                bundle1.putString("IdRes",idRes);
                fragment.setArguments(bundle1);
                mainActivity.getFragment(fragment);
            }
        });
        return view;
    }

    public void init() {
        mainActivity = (MainActivity) getActivity();
        manageCart = view.findViewById(R.id.manageCart);
        manageRes = view.findViewById(R.id.manageRestaurant);
        manageStatistical = view.findViewById(R.id.manageStatistical);
    }
}
