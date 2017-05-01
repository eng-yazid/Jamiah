package com.example.yazid.jamiah;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.ArrayList;
import java.util.List;

//from lib for bottomBar

/**
 * Created by yazid on 4/22/17.
 */

public class MasterActivity  extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private BottomBar mBottomBar;

    private String mUsername;

    //firebase auth
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth, auth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseUser mFirebaseUser;

    //firebase database
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private String mPhotoUrl;
    private GoogleApiClient mGoogleApiClient;

    private ProfileFragment profileFragment;
    private SigninFragment  signinFragment;
    private RegisterFragment registerFragment = new RegisterFragment();

    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    public static final int RC_SIGN_IN = 1;
    public static final int RC_PHOTO_PICKER=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);


        mUsername = ANONYMOUS;

        //-------------------Firebase-----------------------------------------------------//
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();


        //bottom bar
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItems(R.menu.bottombar_menu);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected( int menuItemId) {
                if (menuItemId == R.id.bb_menu_create) {
                    // The user selected the "Recents" tab.
                    Intent intent = new Intent(MasterActivity.this, MainActivity.class);
                    startActivity(intent);

                }else if(menuItemId == R.id.bb_menu_profile)
                {
                    //browse profile

                    if (auth.getCurrentUser() != null) {

                        profileFragment = new ProfileFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                        transaction.replace(R.id.coordinator, profileFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                    }else {


                        signinFragment= new SigninFragment();

                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                        transaction.replace(R.id.coordinator, signinFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
                else if(menuItemId ==R.id.bb_menu_jamiahs)
                {
                    // Create fragment and give it an argument specifying the article it should show
                    CardContentFragment newFragment = new CardContentFragment();

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    // Replace whatever is in the fragment_container view with this fragment,

                    // and add the transaction to the back stack so the user can navigate back
                    transaction.replace(R.id.coordinator, newFragment);
                    transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();
                }
            }

            @Override
            public void onMenuTabReSelected( int menuItemId) {
                if (menuItemId == R.id.bb_menu_create) {
                    // The user reselected the "Recents" tab. React accordingly.
                    Intent intent = new Intent(MasterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        // Create Navigation drawer and inflate layout
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();

        if(supportActionBar != null)
        {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set behavior of Navigation drawer
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener()
                {
                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Set item in checked state
                        menuItem.setChecked(true);
                        // TODO: handle navigation //
                        // Closing drawer on item click
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );
        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //------ this is the part to be used in browsing Jamiahs

        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        //adding tabs to the layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        //------ this is the part to be used in browsing Jamiahs



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (item.getItemId())
        {
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mUsername = ANONYMOUS;
                startActivity(new Intent(this, SignInActivity.class));
                return true;
            case R.id.action_settings:
                return true;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
//        return super.onOptionsItemSelected(item);
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager upViewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
//        adapter.addFragment(new ListContentFragment(), "List");
//        adapter.addFragment(new TileContentFragment(), "Tile");
//        adapter.addFragment(new CardContentFragment(), "Card");
        upViewPager.setAdapter(adapter);
    }


    static class Adapter extends FragmentPagerAdapter
    {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title)
        {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

    }
//---------------------------firebase functions ----------------------------------
//    private void attachDatabaseReadListener(){
//
//        if(mChildEventListener == null) {
//            mChildEventListener = new ChildEventListener() {
//                @Override
//                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                    FriendlyMessage friendlyMessage = dataSnapshot.getValue(FriendlyMessage.class);
//                    mMessageAdapter.add(friendlyMessage);
//                }
//
//                @Override
//                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                }
//
//                @Override
//                public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                }
//
//                @Override
//                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            };
//
//            mMessagesDatabaseReference.addChildEventListener(mChildEventListener);
//        }
//
//    }
//    private void detachDatabaseReadListener(){
//        if(mChildEventListener != null) {
//            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
//            mChildEventListener = null;
//        }
//    }
//    private void onSignedInInitialize(String username){
//        mUsername = username;
//        attachDatabaseReadListener();
//
//    }
//
//    private void onSignedOutCleanup(){
//        mUsername =ANONYMOUS;
//        mMessageAdapter.clear();
//        detachDatabaseReadListener();
//
//    }
}
