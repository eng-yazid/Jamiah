package com.example.yazid.jamiah;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yazid.jamiah.model.Jamiah;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yazid on 5/15/17.
 */

public class JamiahDetailsActivity extends AppCompatActivity{

    public static final String EXTRA_JAM_KEY = "jam_key";
    public static final String TAG = "JamiahDetailsActivity";

    private DatabaseReference mJamReference;
    private DatabaseReference mMembersReference;
    private ValueEventListener mJamListener;
    private String mJamKey;
    private ValueEventListener jamiahListener;

//    private String owner;
//    private List<User> membersList;
//    private int numberOfMonths;
//    private Jamiah jamiah;
//    private Date end;
//    private Date start;
//    private int amount, months, persons,remainingMonths;
//

    private TextView sDateTV,eDateTV,ownerTV, personsTV,amountTV;
    private Button modifyBtn, addMembersBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        // Get JAM key from intent
        mJamKey = getIntent().getStringExtra(EXTRA_JAM_KEY);
        if (mJamKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_JAM_KEY");

        }

        // Initialize Database
        mJamReference = FirebaseDatabase.getInstance().getReference()
                .child("jamiahs").child(mJamKey);

        mMembersReference = FirebaseDatabase.getInstance().getReference()
                .child("jam-members").child(mJamKey);



        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        mJamReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    System.out.println("hhh"+dataSnapshot.getValue().toString());
                    Jamiah jamiah = dataSnapshot.getValue(Jamiah.class);

                    amountTV.setText(jamiah.getAmount() + "");
                    Log.v(TAG, jamiah.getAmount() + "");
                    ownerTV.setText(jamiah.getOwner().toString());
                    sDateTV.setText(jamiah.getStartDate().toString());
                    eDateTV.setText(jamiah.getEndDate().toString());
                    personsTV.setText(jamiah.getNumberOfPersons() + "");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
        if (mJamListener != null) {
            mJamReference.removeEventListener(mJamListener);
        }

    }

    public void init(){

        amountTV = (TextView) findViewById(R.id.det_amount);
        sDateTV = (TextView) findViewById(R.id.det_s_date);
        eDateTV = (TextView) findViewById(R.id.det_end_date);
        ownerTV = (TextView) findViewById(R.id.det_owner);
        personsTV = (TextView) findViewById(R.id.det_num_persons);
        modifyBtn = (Button) findViewById(R.id.det_modify);
        addMembersBtn = (Button) findViewById(R.id.det_add_members);

    }

    public String convertDateToString(Date date){
        String pattern = "dd/MM/yyyy";
        String convertedDate;

        SimpleDateFormat dateFormat= new SimpleDateFormat(pattern);
        convertedDate = dateFormat.format(date);

        return convertedDate;

    }

    private class TestThread extends AsyncTask<String, String ,String>
    {
        public TestThread() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {

            mJamReference.
                    addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Jamiah jamiah = dataSnapshot.getValue(Jamiah.class);

                    System.out.println(dataSnapshot.getValue());

                    amountTV.setText(jamiah.getAmount() + "");
                    Log.v(TAG, jamiah.getAmount() + "");
                    ownerTV.setText(jamiah.getOwner().toString());
                    sDateTV.setText(jamiah.getStartDate().toString());
                    eDateTV.setText(jamiah.getEndDate().toString());
                    personsTV.setText(jamiah.getNumberOfPersons() + "");


//                            amountTV.setText("jamiah.getAmount()");
//                            ownerTV.setText("jamiah.getOwner().toString()");
//                            sDateTV.setText("jamiah.getStartDate().toString()");
//                            eDateTV.setText("jamiah.getEndDate().toString()");
//                            personsTV.setText("jamiah.getNumberOfPersons()");


                }



                @Override
                public void onCancelled(DatabaseError databaseError) {


                    Log.w("Error in DB", databaseError.toException());

                    Toast.makeText(JamiahDetailsActivity.this, "error activty db",Toast.LENGTH_SHORT).show();
                }
            });

            return null;
        }

    }



}
