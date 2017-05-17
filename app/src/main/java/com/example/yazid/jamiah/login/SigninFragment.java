package com.example.yazid.jamiah.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.yazid.jamiah.R;
import com.example.yazid.jamiah.data.TileContentFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by yazid on 4/25/17.
 */

public class SigninFragment extends Fragment {
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    private RegisterFragment registerFragment;

    //test the way of communicating the activity with the fragments
   // OnHeadlineSelectedListener mCallback;

    // Container Activity must implement this interface
//    public interface OnHeadlineSelectedListener {
//        public void onArticleSelected(int position);
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //View root = inflater.inflate(R.layout.sigin_in_fragment, null);

        return inflater.inflate(R.layout.sigin_in_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputEmail = (EditText) view.findViewById(R.id.email);
        inputPassword = (EditText) view.findViewById(R.id.password);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        btnSignup = (Button) view.findViewById(R.id.btn_signup_f);
        btnLogin = (Button) view.findViewById(R.id.btn_login);
        btnReset = (Button) view.findViewById(R.id.btn_reset_password);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext().getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext().getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }



                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(getContext().getApplicationContext(), getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                }
                                else
                                {
//                                    profileFragment = new ProfileFragment();
//                                    TileContentFragment  tileContentFragment= new TileContentFragment();
//                                    // tileContentFragment.setArguments(getIntent().getExtras());
//
//                                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//                                    transaction.replace(R.id.coorinator, profileFragment);
//                                    transaction.addToBackStack(null);
//                                    transaction.commit();
                                    getActivity().finish();

                                }

                            }
                        });

            }
        });


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerFragment = new RegisterFragment();
                TileContentFragment tileContentFragment= new TileContentFragment();

                FragmentManager fm = getFragmentManager();

                FragmentTransaction transaction = fm.beginTransaction();

                transaction.replace(R.id.coordinator, registerFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }


//    // test activity call back
//    @Override
//    public void onAttach(Context activity) {
//        super.onAttach(activity);
//
//        // This makes sure that the container activity has implemented
//        // the callback interface. If not, it throws an exception
//        try {
//            mCallback = (OnHeadlineSelectedListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnHeadlineSelectedListener");
//        }
//    }
}
