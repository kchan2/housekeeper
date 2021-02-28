/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wichackathon2020;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author notebook
 */
class MyHandler extends DefaultHandler {

    // Instance variables store values across method calls
    private Calendar calendar;
    private Month currentMonth;
    private int currentDate;
    private String currentTask;
    private boolean currentDone;

    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        // Check for month
        if (qName.equals("calendar")) {
            int year = Integer.parseInt(attributes.getValue("year"));
            String semester = attributes.getValue("semester");
            calendar = new Calendar(semester, year);
        }

        if (qName.equals("month1")) {
            String name = attributes.getValue("name");
            currentMonth = new Month(name);
        }

        if (qName.equals("month2")) {
            String name = attributes.getValue("name");
            currentMonth = new Month(name);
        }

        if (qName.equals("month3")) {
            String name = attributes.getValue("name");
            currentMonth = new Month(name);
        }

        if (qName.equals("month4")) {
            String name = attributes.getValue("name");
            currentMonth = new Month(name);
        }

        if (qName.equals("month5")) {
            String name = attributes.getValue("name");
            currentMonth = new Month(name);
        }

        if (qName.equals("entry")) {
            currentDate = Integer.parseInt(attributes.getValue("date"));
        }

        if (qName.equals("task")) {
            currentTask = attributes.getValue("name");
            currentDone = Boolean.parseBoolean(attributes.getValue("done"));
        }
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equals("calendar")) {
            System.out.println("Calendar is set up.");
        }

        if (qName.equals("month1")) {
            calendar.setMonth(1, currentMonth);
        }

        if (qName.equals("month2")) {
            calendar.setMonth(2, currentMonth);
        }

        if (qName.equals("month3")) {
            calendar.setMonth(3, currentMonth);
        }

        if (qName.equals("month4")) {
            calendar.setMonth(4, currentMonth);
        }

        if (qName.equals("month5")) {
            calendar.setMonth(5, currentMonth);
        }

        if (qName.equals("task")) {
            currentMonth.addTask(currentDate, currentTask, currentDone);
        }
    }

    public Calendar getCalendar() {
        return calendar;
    }
}
