package com.example.yazid.jamiah;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yazid.jamiah.model.Jamiah;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerAmounts;
    private Spinner spinnerPersons;
    private EditText startDateEt;
    private EditText endDateEt;
    private DatePickerDialog.OnDateSetListener StartDatePickerDialog;
    private DatePickerDialog.OnDateSetListener EndDatePickerDialog;
    private Calendar myCalendar;
    private Calendar myCalendar2;
    private String showResult;
    private Date startDateD;
    private Date endDateD;
    private int numberOfMonths;
    private int incremental;


    //firebase database
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mJamiahDatabaseReference;
    private FirebaseAuth auth;



    private Jamiah jamiah;
    public static final String TAG = "TAG";
    public static final String TAG2 = "TAG2";

    public static final String JAMIAH_SUCCESS = "package com.example.yazid.jamiah;";

    private SigninFragment  signinFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_jamiah);

        //Firebase.getDefaultConfig().setLogLevel(Logger.Level.DEBUG);


        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_j));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //init everything
        final TextView resultTV = (TextView) findViewById(R.id.result_view);
        spinnerAmounts = (Spinner) findViewById(R.id.amount_spinner);
        spinnerPersons = (Spinner) findViewById(R.id.persons_spinner);
        Button createJamButton = (Button) findViewById(R.id.btn_create);
        startDateEt = (EditText) findViewById(R.id.start_date_et);
        endDateEt = (EditText) findViewById(R.id.end_date_et);

        myCalendar = Calendar.getInstance();
        myCalendar2 = Calendar.getInstance();


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        //-------------------Firebase-----------------------------------------------------//
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mJamiahDatabaseReference = mFirebaseDatabase.getReference("jamiahs");


        /* ---------------------------------spinner amount part -------------------------*/

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> amountAdapter = ArrayAdapter.createFromResource(this,
                R.array.amount_array, R.layout.spinner_style);

        // Specify the layout to use when the list of choices appears
        amountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerAmounts.setAdapter(amountAdapter);

        spinnerAmounts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int resulted = calculate();
                //showResult = " كل شخص يدفع:"+ resulted  +"  شهريا";
                resultTV.setText(showResult);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

                /* spinner persons part -------------------------*/
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> PersonsAdapter = ArrayAdapter.createFromResource(this,
                R.array.persons_array, R.layout.spinner_style);

        // Specify the layout to use when the list of choices appears
        PersonsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerPersons.setAdapter(PersonsAdapter);

        spinnerPersons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int resulted = calculate();


                showResult = " كل شخص يدفع:" + resulted + "  شهريا";
                resultTV.setText(showResult);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        incremental = spinnerToIntConverter(spinnerPersons);
        //------------- Dates Picker ---------------------------------------

        StartDatePickerDialog = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(startDateEt, myCalendar);
                startDateD = myCalendar.getTime();

            }

        };

        startDateEt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(MainActivity.this, StartDatePickerDialog,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        EndDatePickerDialog = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth)
            {

                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH , monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(endDateEt, myCalendar2);
                endDateD = myCalendar2.getTime();
            }
        };


        endDateEt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(MainActivity.this, EndDatePickerDialog, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH)+ incremental,
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();

                incremental = 0;
                //TODO: handle on date changed from the start date and number of persons
            }
        });

        //end date picker

        createJamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jamiah = addJamiah();
                        //TODO: change the database to be matched with the current structured object
                if (jamiah != null) {

                        // j is not the correct object-----//
                        // mMessagesDatabaseReference.push().setValue(j);
                        //jamiah data should be entered after adding its users
                    //    String idFireBase =  mJamiahDatabaseReference.push().getKey();
                    //   mJamiahDatabaseReference.child(idFireBase).setValue(jamiah);
                    //   Toast.makeText(MainActivity.this, "jamiah added",Toast.LENGTH_LONG).show();
                        sendJam();
                        Log.d("success", jamiah.toString());
                } else {
                    showResult = "Jamiah not entered correctly!";
                    resultTV.setText(showResult);
                }
            }
        });

    } // onCreate() End

    public void sendJam() {
        Intent intent;
        if(auth.getCurrentUser() != null)
        {
            intent = new Intent(this, AddUsersActivity.class);
            //if user is existed, add the Jamiah to DB
            String idFireBase =  mJamiahDatabaseReference.push().getKey();
            mJamiahDatabaseReference.child(idFireBase).setValue(jamiah);
            intent.putExtra("jamiah",jamiah);
            startActivity(intent);
        }
        else{
            intent = new Intent(this, SignInActivity.class);
            intent.putExtra("jamiah",jamiah);
            startActivity(intent);
        }

    }

    public int calculate()
    {
        String amountString = spinnerAmounts.getSelectedItem().toString();
        String personsString = spinnerPersons.getSelectedItem().toString();

        int amountValueInt = Integer.valueOf(amountString);
        int personsValueInt = Integer.valueOf(personsString);
        return amountValueInt /  personsValueInt;
    }

    public void updateLabel(EditText editText, Calendar calendar) {

        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        editText.setText(sdf.format(calendar.getTime()));
    }

//    public String dateToStringConverter(Date date){
//        String myFormat = "dd/MM/yy"; //In which you need put here
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
//        return sdf.format(date);
//    }


    public int spinnerToIntConverter(Spinner item)
    {
        String itemString = item.getSelectedItem().toString();
        return Integer.valueOf(itemString);
    }

    @Nullable
    public Jamiah addJamiah() {
        Jamiah j;
        int amountValueInt = spinnerToIntConverter(spinnerAmounts);
        int personsValueInt = spinnerToIntConverter(spinnerPersons);
        numberOfMonths = myCalendar2.get(Calendar.MONTH) - myCalendar.get(Calendar.MONTH);

        //Jamiah(int id, int amount, int months,int numberOfPersons, Date startDate , Date endDate){
        j = new Jamiah(amountValueInt,numberOfMonths,personsValueInt, startDateD, endDateD);
        //return j;

        if(startDateD == null || endDateD == null)
        {
            Toast.makeText(getApplicationContext(),
                    "missed one or more dates:"+ 0,
                    Toast.LENGTH_LONG).show();
            return null;
        }

        if(numberOfMonths != personsValueInt)
        {
            Toast.makeText(getApplicationContext(),
                    "number of months not equal to the number of persons:"+ 0,
                    Toast.LENGTH_LONG).show();
            return null;
        }

        if(numberOfMonths <= 1)
        {
            Toast.makeText(getApplicationContext(),
                    "the chosen dates are less than one month :"+ 0,
                    Toast.LENGTH_LONG).show();
            return null;
        }

        if (endDateD.before(startDateD))
        {
            Toast.makeText(getApplicationContext(),
                    "end date is before start date :"+ 0,
                    Toast.LENGTH_LONG).show();
            return null;
        }

        return j;
    }

}