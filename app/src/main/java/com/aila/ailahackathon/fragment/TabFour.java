package com.aila.ailahackathon.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aila.ailahackathon.R;
import com.aila.ailahackathon.auth.Registration;
import com.aila.ailahackathon.fragment.minum.MinumAdapter;
import com.aila.ailahackathon.model.MinumModel;
import com.aila.ailahackathon.model.ScheduleModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFour extends Fragment {
    private List<MinumModel> listMinum = new ArrayList<MinumModel>();
    private RecyclerView recyclerView;
    private MinumAdapter minumAdapter;
    public TabFour() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        Registration.userRef
                .document("bD017zDCRfRpBVq0ec2PbA6QgsX2")
                .collection("minum")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshot;
                            MinumModel minumModel = documentSnapshot.toObject(MinumModel.class);
                            listMinum.add(minumModel);
                        }
                        minumAdapter = new MinumAdapter(listMinum);
                        recyclerView.setAdapter(minumAdapter);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_four, container, false);
    }

}
