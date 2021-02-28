/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wichackathon2020;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author notebook
 */
public class Shield {

    private double completion;
    private Calendar calendar;
    private int month;
    private int date;

    public Shield(Calendar calendar) {
        this.calendar = calendar;
        updateDT();
        completion = calculateCompletion();
    }

    public double getCompletion(Calendar newCal) {
        updateCalendar(newCal);
        completion = calculateCompletion();
        return completion;
    }

    private double calculateCompletion() {
        int dayTotal = calendar.getDayTotal(month, date);
        int dayCompleted = calendar.getDayCompleted(month, date);
        if (dayTotal == 0) {
            return 100;
        } else {
            double percentage = ((double) dayCompleted / (double) dayTotal) * 100;
            return percentage;
        }
    }

    private void updateCalendar(Calendar newCal) {
        calendar = newCal;
    }

    private void updateDT() {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter formatdt = DateTimeFormatter.ofPattern("E dd MMM yyyy HH:mm:ss");
        String formatteddt = dt.format(formatdt);
        date = Integer.parseInt(formatteddt.substring(4, 6));
        month = monthIndex(formatteddt.substring(7, 10));
    }

    private int monthIndex(String month) {
        if (month.equalsIgnoreCase("AUG") || month.equalsIgnoreCase("JAN")) {
            return 1;
        } else if (month.equalsIgnoreCase("SEP") || month.equalsIgnoreCase("FEB")) {
            return 2;
        } else if (month.equalsIgnoreCase("OCT") || month.equalsIgnoreCase("MAR")) {
            return 3;
        } else if (month.equalsIgnoreCase("NOV") || month.equalsIgnoreCase("APRG")) {
            return 4;
        } else if (month.equalsIgnoreCase("DEC") || month.equalsIgnoreCase("MAY")) {
            return 5;
        } else {
            return 0;
        }
    }

}
