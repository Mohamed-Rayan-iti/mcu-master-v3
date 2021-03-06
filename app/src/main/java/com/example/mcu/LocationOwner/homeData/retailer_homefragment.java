package com.example.mcu.LocationOwner.homeData;

import android.content.Intent;
import android.net.IpPrefix;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mcu.Ip.And.Ordernum.model.ipandordermodel;
import com.example.mcu.LocationOwner.retailer_ip_settingfragment;
import com.example.mcu.LocationOwner.retailer_profilefragment;
import com.example.mcu.R;
import com.example.mcu.aboutus;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;


public class retailer_homefragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;


    private ImageView homeicon;

    private homeAdepter adepter;
    private List<ipandordermodel> list;

    private FirebaseFirestore firestore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.retailer_homefragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //hooks
        recyclerView = view.findViewById(R.id.recyclerview_home);
        progressBar = view.findViewById(R.id.progressbar_home);
        homeicon = view.findViewById(R.id.icon_ip_setting);
        firestore = FirebaseFirestore.getInstance();


        // to array data
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        list = new ArrayList<>();
        adepter = new homeAdepter(list,getActivity());
        recyclerView.setAdapter(adepter);
        getdata();


    }


    private void getdata() {

        progressBar.setVisibility(View.VISIBLE);


        firestore.collection("IP and order number")
                .orderBy("time", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {

                    if (error == null) {


                        if (value == null) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "value is null !  ", Toast.LENGTH_SHORT).show();


                        } else {

                            for (DocumentChange documentChange : value.getDocumentChanges()) {

                                ipandordermodel model = documentChange.getDocument().toObject(ipandordermodel.class);

                                list.add(model);


                                adepter.notifyDataSetChanged();
                            }
                            progressBar.setVisibility(View.GONE);

                        }


                    } else {
                        progressBar.setVisibility(View.GONE);
                        Log.e("error", error.toString());
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });




        // link from HOME to TIME AND SPEED
       // homeicon.setOnClickListener (new View.OnClickListener ( ) {
           // @Override
           // public void onClick ( View v ) {

             //   Intent intent = new Intent ( new Intent ( retailer_homefragment.this.getActivity ( ), retailer_ip_settingfragment.class ) );
              //  retailer_homefragment.this.startActivity ( intent );


          //  }
       // } );

    }
}