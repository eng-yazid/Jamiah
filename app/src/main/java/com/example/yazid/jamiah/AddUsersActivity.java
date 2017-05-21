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

import com.example.yazid.jamiah.model.Jamiah;
import com.example.yazid.jamiah.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddUsersActivity extends AppCompatActivity {

    private static final String TAG = "AddUsersActivity";

    private EditText[] usersEt;
    private LinearLayout linearLayout, buttonsLayout;
    private Button startJam, skipAddingBtn;
    private User[] members;
    private List<User> membersList;
    private int numberOfMonths;
    private Jamiah jamiah;
    private String end;
    private String start;
    private int amount, months, persons;
    private String id;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_users_activity);

        linearLayout = (LinearLayout) findViewById(R.id.linear_layout);
        buttonsLayout= new LinearLayout(this);

        final Intent intent = getIntent();
        TextView jamiahTv = new TextView(this);

        auth = FirebaseAuth.getInstance();
        membersList = new ArrayList<User>();
        jamiah = (Jamiah) intent.getSerializableExtra("jamiah");

        /*
        TODO: use the video database of security rules to implement the users inside each Jamiah
        tickets list has the users who can see the content of the game
        one write rule is enough ored together, but validate rule should be implmented one by one(Anded)
        */

//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_add_users));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //-------------------Firebase-----------------------------------------------------//
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        //____________________FOR DEBUGGING_____________________//

        id = jamiah.getId();
        amount = jamiah.getAmount();
        months = jamiah.getMonths();
        persons = jamiah.getNumberOfPersons();
        end = jamiah.getEndDate();
        start = jamiah.getStartDate();

        jamiahTv.setText("id: " + id + "- amount: "+ amount + "- months:"+ months +
                "- persons:" + persons  + "- start date: " + start + "-end Date:"+end);

        linearLayout.addView(jamiahTv);
        numberOfMonths =  jamiah.getMonths();      //intent.getIntExtra(MainActivity.JAMIAH_SUCCESS,0);
        TextView[] myTextViews = new TextView[numberOfMonths];

        usersEt = new EditText[numberOfMonths];

        for (int i = 0; i < numberOfMonths; i++)
        {
            // create a new textview
            final TextView rowTextView = new TextView(this);
            final EditText rowEditText = new EditText(this);
           // User user1 = new User();

            // set some properties of rowTextView or something
            rowTextView.setText("month #:" + i); //+ amount + "-" + months + "-"+ persons
            rowEditText.setId(6+i);
            linearLayout.addView(rowTextView);
            linearLayout.addView(rowEditText);
            // save a reference to the textview for later
            myTextViews[i] = rowTextView;
            //user1.setUserName(rowEditText.getText().toString());
           // membersList.add(user1);
          //  Log.d(TAG, user1.getUserName());
            usersEt[i]= rowEditText;

        }
        //list of usersnames who are included in that jamiah, they are not real usres yet
//        members = new User[persons];
//
//        for( int i = 0 ;i <  members.length; i ++){
//            members[i] = new User();
//            members[i].setUserName(usersEt[i].getText().toString());
//        }

        startJam = new Button(this);
        startJam.setText("start Jam");

        skipAddingBtn = new Button(this);
        skipAddingBtn.setText("skip adding users");

        //linearLayout.addView(startJam);

        buttonsLayout.addView(startJam);
        buttonsLayout.addView(skipAddingBtn);
        linearLayout.addView(buttonsLayout);
        startJam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Clicked Button Index :" + 0,
                        Toast.LENGTH_LONG).show();
                submitJam();
            }
        });

        skipAddingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitJamWithoutMemebers();
            }
        });
    }

    public void submitJam()
    {
        final String userId = auth.getCurrentUser().getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get user value
                User user = dataSnapshot.getValue(User.class);

                // [START_EXCLUDE]
                if (user == null) {
                    // User is null, error out
                    Log.e(TAG, "User " + userId + " is unexpectedly null");
                    Toast.makeText(AddUsersActivity.this,
                            "Error: could not fetch user.",
                            Toast.LENGTH_SHORT).show();
                }else {
                    // Write new post
                    writeNewJam(userId, user.getUserName(),jamiah);
                }
                Intent intent = new Intent(AddUsersActivity.this, MasterActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());

            }
        });

    }

    public void submitJamWithoutMemebers(){
        final String userId = auth.getCurrentUser().getUid();

        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get user value
                User user = dataSnapshot.getValue(User.class);

                // [START_EXCLUDE]
                if (user == null) {
                    // User is null, error out
                    Log.e(TAG, "User " + userId + " is unexpectedly null");
                    Toast.makeText(AddUsersActivity.this,
                            "Error: could not fetch user.",
                            Toast.LENGTH_SHORT).show();
                }else {
                    // Write new post
                    writeNewJamWithoutMembers(userId, user.getUserName(),jamiah);
                }

                Intent intent = new Intent(AddUsersActivity.this, MasterActivity.class);
                startActivity(intent);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());

            }
        });

    }

    private void writeNewJam(String userId, String username, Jamiah jamiah1) {

        String key = mDatabase.child("jamiahs").push().getKey();

        for(int i =0; i< persons; i++){
            User user1 = new User();
            user1.setUserName(usersEt[i].getText().toString());
            membersList.add(user1);
        }
        jamiah1.setOwner(username);
        Map<String, Object> jamiahValues = jamiah1.toMap();
       //add map for users
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/jamiahs/" + key, jamiahValues);
        childUpdates.put("/user-jamiahs/" + userId + "/" + key, jamiahValues);
        childUpdates.put("/jam-members/"+key,membersList);

        //TODO: when adding users , make sure to update "childUpdates" to include them
        mDatabase.updateChildren(childUpdates);
    }

    //We may don't need to add this function in case we check which button is clicked TODO _____

    private void writeNewJamWithoutMembers(String userId, String username, Jamiah jamiah1) {

        String key = mDatabase.child("jamiahs").push().getKey();

        jamiah1.setOwner(username);
        Map<String, Object> jamiahValues = jamiah1.toMap();
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/jamiahs/" + key, jamiahValues);
        childUpdates.put("/user-jamiahs/" + userId + "/" + key, jamiahValues);
        //childUpdates.put("/jam-members/"+key,members);
        //TODO: when adding users , make sure to update "childUpdates" to include them
        mDatabase.updateChildren(childUpdates);
    }

}
