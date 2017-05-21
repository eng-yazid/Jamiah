package com.example.yazid.jamiah;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yazid on 5/20/17.
 */

public class Util {


    public static String convertDateToString(Date date){
        String pattern = "dd/MM/yyyy";
        String convertedDate;

        SimpleDateFormat dateFormat= new SimpleDateFormat(pattern);
        convertedDate = dateFormat.format(date);

        return convertedDate;

    }
}
