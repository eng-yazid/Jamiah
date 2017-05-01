//package com.example.yazid.jamiah;
//
//import android.app.DatePickerDialog;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Locale;
//
///**
// * Created by yazid on 4/24/17.
// */
//
//public class CreateJamiahFragment extends Fragment {
//
//    private Spinner spinnerAmounts;
//    private Spinner spinnerPersons;
//    private EditText startDateEt;
//    private EditText endDateEt;
//    private DatePickerDialog.OnDateSetListener StartDatePickerDialog;
//    private DatePickerDialog.OnDateSetListener EndDatePickerDialog;
//    private Calendar myCalendar;
//    private Calendar myCalendar2;
//    private String showResult;
//    private Date startDateD;
//    private Date endDateD;
//    private int numberOfMonths;
//    private int incremental;
//
//    private Jamiah jamiah;
//
//    public static final String TAG = "TAG";
//    public static final String TAG2 = "TAG2";
//
//    public static final String JAMIAH_SUCCESS = "package com.example.yazid.jamiah;";
//
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        //init everything
//        final TextView resultTV = (TextView) container.findViewById(R.id.result_view);
//        spinnerAmounts = (Spinner) container.findViewById(R.id.amount_spinner);
//        spinnerPersons = (Spinner) container.findViewById(R.id.persons_spinner);
//        Button createJamButton = (Button) container.findViewById(R.id.btn_create);
//        startDateEt = (EditText) container.findViewById(R.id.start_date_et);
//        endDateEt = (EditText) container.findViewById(R.id.end_date_et);
//
//        myCalendar = Calendar.getInstance();
//        myCalendar2 = Calendar.getInstance();
//
//
//         /* ---------------------------------spinner amount part -------------------------*/
//
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> amountAdapter = ArrayAdapter.createFromResource(this,
//                R.array.amount_array, R.layout.spinner_style);
//
//        // Specify the layout to use when the list of choices appears
//        amountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // Apply the adapter to the spinner
//        spinnerAmounts.setAdapter(amountAdapter);
//
//        spinnerAmounts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                int resulted = calculate();
//                //showResult = " كل شخص يدفع:"+ resulted  +"  شهريا";
//                resultTV.setText(showResult);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//                /* spinner persons part -------------------------*/
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> PersonsAdapter = ArrayAdapter.createFromResource(this,
//                R.array.persons_array, R.layout.spinner_style);
//
//        // Specify the layout to use when the list of choices appears
//        PersonsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // Apply the adapter to the spinner
//        spinnerPersons.setAdapter(PersonsAdapter);
//
//        spinnerPersons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                int resulted = calculate();
//
//
//                showResult = " كل شخص يدفع:" + resulted + "  شهريا";
//                resultTV.setText(showResult);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        incremental = spinnerToIntConverter(spinnerPersons);
//        //------------- Dates Picker ---------------------------------------
//
//        StartDatePickerDialog = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateLabel(startDateEt, myCalendar);
//                startDateD = myCalendar.getTime();
//
//            }
//
//        };
//
//        startDateEt.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                new DatePickerDialog(MainActivity.this, StartDatePickerDialog,
//                        myCalendar.get(Calendar.YEAR),
//                        myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//
//
//
//        EndDatePickerDialog = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth)
//            {
//
//                myCalendar2.set(Calendar.YEAR, year);
//                myCalendar2.set(Calendar.MONTH , monthOfYear);
//                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateLabel(endDateEt, myCalendar2);
//                endDateD = myCalendar2.getTime();
//            }
//        };
//
//
//        endDateEt.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                new DatePickerDialog(MainActivity.this, EndDatePickerDialog, myCalendar2
//                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH)+ incremental,
//                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
//
//                incremental = 0;
//                //TODO: handle on date changed from the start date and number of persons
//            }
//        });
//
//        //end date picker
//
//
//        return inflater.inflate(R.layout.create_new_jamiah, container, false);
//    }
//
//
//    public void updateLabel(EditText editText, Calendar calendar) {
//
//        String myFormat = "dd/MM/yy"; //In which you need put here
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
//        editText.setText(sdf.format(calendar.getTime()));
//    }
//
//    public int spinnerToIntConverter(Spinner item)
//    {
//        String itemString = item.getSelectedItem().toString();
//        return Integer.valueOf(itemString);
//    }
//
//
//    @Nullable
//    public Jamiah addJamiah() {
//        Jamiah j;
//        int amountValueInt = spinnerToIntConverter(spinnerAmounts);
//        int personsValueInt = spinnerToIntConverter(spinnerPersons);
//        numberOfMonths = myCalendar2.get(Calendar.MONTH) - myCalendar.get(Calendar.MONTH);
//
//        //Jamiah(int id, int amount, int months,int numberOfPersons, Date startDate , Date endDate){
//        j = new Jamiah(amountValueInt,numberOfMonths,personsValueInt, startDateD, endDateD);
//        //return j;
//
//        if(startDateD == null || endDateD == null)
//        {
//            Toast.makeText(getApplicationContext(),
//                    "missed one or more dates:"+ 0,
//                    Toast.LENGTH_LONG).show();
//            return null;
//        }
//
//        if(numberOfMonths != personsValueInt)
//        {
//            Toast.makeText(getApplicationContext(),
//                    "number of months not equal to the number of persons:"+ 0,
//                    Toast.LENGTH_LONG).show();
//            return null;
//        }
//
//        if(numberOfMonths <= 1)
//        {
//            Toast.makeText(getApplicationContext(),
//                    "the chosen dates are less than one month :"+ 0,
//                    Toast.LENGTH_LONG).show();
//            return null;
//        }
//
//        if (endDateD.before(startDateD))
//        {
//            Toast.makeText(getViewgetApplicationContext(),
//                    "end date is before start date :"+ 0,
//                    Toast.LENGTH_LONG).show();
//            return null;
//        }
//        //TODO: change the database to be matched with the current structured object
//        // j is not the correct object-----//
//        // mMessagesDatabaseReference.push().setValue(j);
//
//        return j;
//    }
//
//
//
//
//
//}
