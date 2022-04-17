package com.rikkei.training.appadmin_vutruonggiang.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.training.appadmin_vutruonggiang.R;
import com.rikkei.training.appadmin_vutruonggiang.modle.PassRes;

import java.util.ArrayList;
import java.util.List;

public class FragmentLogin extends Fragment {

    private View view;
    MainActivity mainActivity;
    EditText edPassRes;
    AppCompatButton butCheck;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    List<PassRes> passResList = new ArrayList<>();

    public static Fragment newInstance() {

        Bundle args = new Bundle();

        FragmentLogin fragment = new FragmentLogin();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        init();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.child("passRes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
                for (DataSnapshot data : dataSnapshotIterable) {
                    PassRes passRes = data.getValue(PassRes.class);
                    passResList.add(passRes);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        butCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = edPassRes.getText().toString().trim();
                if (!pass.equals("")) {
                    for (int i = 0; i < passResList.size(); i++) {
                        if(pass.equals(passResList.get(i).getPass()+"")){
                            FragmentHome fragmentHome=new FragmentHome();
                            Bundle bundle=new Bundle();
                            bundle.putString("IdRes",passResList.get(i).getId());
                            fragmentHome.setArguments(bundle);
                            mainActivity.getFragment(fragmentHome);
                        }
                    }
                    if(pass.equals("admin")){
                        FragmentHome fragmentHome=new FragmentHome();
                        Bundle bundle=new Bundle();
                        bundle.putString("IdRes","admin");
                        fragmentHome.setArguments(bundle);
                        mainActivity.getFragment(fragmentHome);
                    }
                }
            }
        });
        return view;
    }

    public void init() {
        mainActivity = (MainActivity) getActivity();
        edPassRes = view.findViewById(R.id.edPassRes);
        butCheck = view.findViewById(R.id.butCheck);
    }
}
