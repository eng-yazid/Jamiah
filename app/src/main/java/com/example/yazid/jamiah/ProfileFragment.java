package com.example.yazid.jamiah;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by yazid on 4/24/17.
 */

public class ProfileFragment extends Fragment {

    private TextView username;
    private ImageView avator;
    private Button signOutBtn;
    private FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.profile_fragment, null);

        username = (TextView) root.findViewById(R.id.profile_username);
        avator = (ImageView) root.findViewById(R.id.profile_avator);
        signOutBtn = (Button) root.findViewById(R.id.sing_out_btn);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
            }
        });
        return root;

    }
}
