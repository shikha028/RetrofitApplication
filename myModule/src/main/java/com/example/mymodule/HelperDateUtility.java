package com.example.mymodule;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class HelperDateUtility {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDate getCurrentDate(){
        return LocalDate.now();
    }

    public static Date getCurrentDateLegacy(){
        return Calendar.getInstance().getTime();
    }
}
