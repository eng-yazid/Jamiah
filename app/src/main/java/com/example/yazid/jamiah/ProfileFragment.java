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

import com.example.yazid.jamiah.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by yazid on 4/24/17.
 */

public class ProfileFragment extends Fragment {

    private TextView username;
    private ImageView avator;
    private Button signOutBtn;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.profile_fragment, null);

        username = (TextView) root.findViewById(R.id.profile_username);
        avator = (ImageView) root.findViewById(R.id.profile_avator);
        signOutBtn = (Button) root.findViewById(R.id.sing_out_btn);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //TODO view profile info
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        username.setText(auth.getCurrentUser().getEmail().toString());
        avator.setImageResource(R.drawable.c_avator);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                //TODO destroy fragment after logout
                //Intent intent =new Intent(getActivity(),)
                //getActivity();
            }
        });
        return root;

    }

    /*
    TODO: check this snippet to add user information to his profile

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
if (user != null) {
    // Name, email address, and profile photo Url
    String name = user.getDisplayName();
    String email = user.getEmail();
    Uri photoUrl = user.getPhotoUrl();

    // Check if user's email is verified
    boolean emailVerified = user.isEmailVerified();

    // The user's ID, unique to the Firebase project. Do NOT use this value to
    // authenticate with your backend server, if you have one. Use
    // FirebaseUser.getToken() instead.
    String uid = user.getUid();
}
     */
}
