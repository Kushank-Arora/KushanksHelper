package com.kushank.kushankshelper.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    public static final String MONDAY = "Monday";
    public static final String TUESDAY = "Tuesday";
    public static final String WEDNESDAY = "Wednesday";
    public static final String THURSDAY = "Thursday";
    public static final String FRIDAY = "Friday";
    public static final String SATURDAY = "Saturday";
    public static final String SUNDAY = "Sunday";

    public static final List<String> DAYS = new ArrayList<>(Arrays.asList(
            MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    ));

    public class TimeInterval {
        public int startTime; // In milliseconds
        public int endTime; // In milliseconds
        public String day;
        public Date date; // Optional, used in Tracking
    }

    public class Date {
        private int date;
        private int month;
        private int year;

        public Date(int date, int month, int year) {
            this.date = date;
            this.month = month;
            this.year = year;
        }
    }
}
