package com.example.yazid.jamiah;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class AddUsersActivity extends AppCompatActivity {

    private EditText[] usersEt;
    private LinearLayout linearLayout;
    private Button startJam;
    private User[] usersList;
    private ArrayList<User> usersAL;
    private int numberOfMonths;
    private Jamiah jamiah;
    private Date end;
    private Date start;
    private int amount, months, persons;
    private String id;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUsersDatabaseReference;
    private DatabaseReference mJamiahsDatabaseReference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_users_activity);
        linearLayout = (LinearLayout) findViewById(R.id.linear_layout);
        final Intent intent = getIntent();
        TextView jamiahTv = new TextView(this);

        usersAL = new ArrayList<User>();
        jamiah = (Jamiah) intent.getSerializableExtra("jamiah");

        /*

        TODO: use the video database of security rules to implement the users inside each Jamiah
        tickets list has the users who can see the content of the game
        one write rule is enough ored together, but validate rule should be implmented one by one(Anded)

         */

        //TODO'''''below
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_add_users));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //-------------------Firebase-----------------------------------------------------//
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUsersDatabaseReference = mFirebaseDatabase.getReference("users");
        mJamiahsDatabaseReference =mFirebaseDatabase.getReference("jamiahs");


        //____________________FOR DEBUGGING_____________________//

        id = jamiah.getId();
        amount = jamiah.getAmount();
        months = jamiah.getMonths();
        persons = jamiah.getNumberOfPersons();
        end = jamiah.getEndDate();
        start = jamiah.getStartDate();

        jamiahTv.setText("id: " + id + "- amount: "+ amount + "- months:"+ months + "- persons:" + persons  + "- start date: " + start + "-end Date:"+end);
        linearLayout.addView(jamiahTv);
        numberOfMonths =  jamiah.getMonths();      //intent.getIntExtra(MainActivity.JAMIAH_SUCCESS,0);
        TextView[] myTextViews = new TextView[numberOfMonths];

        usersEt = new EditText[numberOfMonths];

        for (int i = 0; i < numberOfMonths; i++)
        {
            // create a new textview
            final TextView rowTextView = new TextView(this);
            final EditText rowEditText = new EditText(this);
            // set some properties of rowTextView or something
            rowTextView.setText("month #:" + i); //+ amount + "-" + months + "-"+ persons

            linearLayout.addView(rowTextView);
            linearLayout.addView(rowEditText);
            // save a reference to the textview for later
            myTextViews[i] = rowTextView;
            usersEt[i]= rowEditText;
        }

        usersList = new User[persons]; //list of usersnames who are included in that jamiah

        startJam = new Button(this);
        startJam.setText("start Jam");
        linearLayout.addView(startJam);
        startJam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Clicked Button Index :" + 0,
                        Toast.LENGTH_LONG).show();

              try
              {
                  for (int i = 0 ; i < persons ; i++)
                  {
                      final User rowUser = new User(usersEt[i].getText().toString());
                      usersList[i] = rowUser;
                      usersAL.add(rowUser);
                      Log.d("username", usersList[i].getUserName());
                  }
                  //mUsersDatabaseReference.push().setValue(usersAL);
                  sendJam();
              }


              catch (Exception e){
                  Log.d("error","exception");
              }

            }
        });
    }

    public void sendJam()
    {

        Intent intent = new Intent(this, MasterActivity.class);


        // j is not the correct object-----//
        // mMessagesDatabaseReference.push().setValue(j);
        //jamiah data should be entered after adding its users
        String idFireBase =  mJamiahsDatabaseReference.push().getKey();

        mJamiahsDatabaseReference.child(idFireBase).child("members").setValue("");
           //Toast.makeText(MainActivity.this, "jamiah added",Toast.LENGTH_LONG).show();

        intent.putExtra("jamiah",jamiah);
        startActivity(intent);
    }
}
