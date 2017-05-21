package com.example.yazid.jamiah;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yazid.jamiah.model.Jamiah;
import com.example.yazid.jamiah.viewHolder.JamiahViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class JamiahListFragment extends Fragment {

    public static final String TAG = "JamiahListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Jamiah, JamiahViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private FirebaseAuth auth;

    public JamiahListFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.all_jamiahs, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = (RecyclerView) rootView.findViewById(R.id.all_jamiahs_list);
        mRecycler.setHasFixedSize(true);

        return  rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        auth = FirebaseAuth.getInstance();
        // Set up FirebaseRecyclerAdapter with the Query
        Query jamiahsQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<Jamiah, JamiahViewHolder>(Jamiah.class, R.layout.jamiah_list_item,
                JamiahViewHolder.class, jamiahsQuery){
            @Override
            protected void populateViewHolder(JamiahViewHolder viewHolder, Jamiah model, final int position) {
                final DatabaseReference jamiahRef = getRef(position);

                final String jamKey = jamiahRef.getKey();

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), JamiahDetailsActivity.class);
                        intent.putExtra(JamiahDetailsActivity.EXTRA_JAM_KEY, jamKey);
                        startActivity(intent);
                    }
                });
                viewHolder.bindToPost(model);

            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }


    public Query getQuery(DatabaseReference databaseReference) {
        // All my Jams
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        return databaseReference.child("user-jamiahs")
                .child(userId);
    }
}