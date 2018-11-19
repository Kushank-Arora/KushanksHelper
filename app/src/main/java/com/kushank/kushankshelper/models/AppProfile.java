package com.kushank.kushankshelper.models;

import java.util.ArrayList;
import java.util.List;

public class AppProfile {
    String packageName;
    String appName;
    List<Utils.TimeInterval> timeIntervals;

    public void addTimeInterval(int startTime, int endTime, String day) {
        if (timeIntervals == null) timeIntervals = new ArrayList<>();
        boolean inserted = false;
        for (Utils.TimeInterval interval: timeIntervals){
            if(day.equals(interval.day)){
                if (endTime < interval.startTime) {
                    
                }
            }
        }
    }
}
