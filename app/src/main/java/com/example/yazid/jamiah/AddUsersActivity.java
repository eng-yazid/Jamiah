package com.example.yazid.jamiah;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
    private User[] userObj;
    private ArrayList<User> usersAL;
    private int numberOfMonths;
    private Jamiah jamiah;
    private Date end;
    private Date start;
    private int amount, months, persons, id;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUsersDatabaseReference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_users_activity);
        linearLayout = (LinearLayout) findViewById(R.id.linear_layout);
        final Intent intent = getIntent();
        jamiah = new Jamiah();
        TextView jamiahTv = new TextView(this);

        usersAL = new ArrayList<User>();
        jamiah = (Jamiah) intent.getSerializableExtra("jamiah");




        //-------------------Firebase-----------------------------------------------------//
//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        mUsersDatabaseReference = mFirebaseDatabase.getReference("users");

        id = jamiah.getId();
        amount = jamiah.getAmount();
        months = jamiah.getMonths();
        persons = jamiah.getNumberOfPersons();
        end = jamiah.getEndDate();
        start = jamiah.getStartDate();
        jamiahTv.setText("id: " + id + "- amount: "+ amount +
                "- months:"+ months + "- persons:" + persons
                + "- start date: " + start + "-end Date:"+end);
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
        userObj= new User[persons];
        startJam = new Button(this);
        startJam.setText("start Jam");
        linearLayout.addView(startJam);
        startJam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Clicked Button Index :" + 0,
                        Toast.LENGTH_LONG).show();

              try {
                  for (int i = 0 ; i < persons ; i++)
                  {
                      final User rowUser = new User(i,usersEt[i].getText().toString(),"123456");
                      userObj[i] = rowUser;
                      //userObj[i] = new User();
                      //userObj[i].setUserName();
                      usersAL.add(rowUser);
                      Log.d("username",userObj[i].getUserName());
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

    public void sendJam() {
        Intent intent = new Intent(this, JamiahListFragment.class);
        intent.putExtra("jamiah",jamiah);
        startActivity(intent);
    }
}
