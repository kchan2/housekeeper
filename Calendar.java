/*
 * Calendar - contains months of a semester (5 in total)
 */
package wichackathon2020;

/**
 *
 * @author notebook
 */
public class Calendar {
    
    private String semester;
    private int year;
    private Month month1;
    private Month month2;
    private Month month3;
    private Month month4;
    private Month month5;
    
    public Calendar(String semester, int year) {
        this.semester = semester;
        this.year = year;
        if (semester.equalsIgnoreCase("F")) {
            month1 = new Month("August");
            month2 = new Month("September");
            month3 = new Month("October");
            month4 = new Month("November");
            month5 = new Month("December");
        } else if (semester.equalsIgnoreCase("S")){
            month1 = new Month("January");
            month2 = new Month("February");
            month3 = new Month("March");
            month4 = new Month("April");
            month5 = new Month("May");
        }
    }
    
    public String getSemester() {
        return semester;
    }
    
    public Month getMonth(int month) {
        if (month == 1) {
            return month1;
        } else if (month == 2) {
            return month2;
        } else if (month == 3) {
            return month3;
        } else if (month == 4) {
            return month4;
        } else {
            return month5;
        }
    }
    
    public void setMonth(int month, Month m) {
        if (month == 1) {
            month1 = m;
        } else if (month == 2) {
            month2 = m;
        } else if (month == 3) {
            month3 = m;
        } else if (month == 4) {
            month4 = m;
        } else {
            month5 = m;
        }
    }
    
    public void addTask(int month, int date, String task, boolean done) {
        if (month == 1) {
            month1.addTask(date, task, done);
        } else if (month == 2) {
            month2.addTask(date, task, done);
        } else if (month == 3) {
            month3.addTask(date, task, done);
        } else if (month == 4) {
            month4.addTask(date, task, done);
        } else {
            month5.addTask(date, task, done);
        }
    }
    
    public void deleteTask(int month, int date, int task) {
        if (month == 1) {
            month1.deleteTask(date, task);
        } else if (month == 2) {
            month2.deleteTask(date, task);
        } else if (month == 3) {
            month3.deleteTask(date, task);
        } else if (month == 4) {
            month4.deleteTask(date, task);
        } else {
            month5.deleteTask(date, task);
        }
    }
    
    public void finishTask(int month, int date, int task) {
        if (month == 1) {
            month1.finishTask(date, task);
        } else if (month == 2) {
            month2.finishTask(date, task);
        } else if (month == 3) {
            month3.finishTask(date, task);
        } else if (month == 4) {
            month4.finishTask(date, task);
        } else {
            month5.finishTask(date, task);
        }
    }
    
    public void showTask(int month, int date) {
        if (month == 1) {
            month1.showTask(date);
        } else if (month == 2) {
            month2.showTask(date);
        } else if (month == 3) {
            month3.showTask(date);
        } else if (month == 4) {
            month4.showTask(date);
        } else {
            month5.showTask(date);
        }
    }
    
    public void showAllTask() {
        month1.showAllTask();
        month2.showAllTask();
        month3.showAllTask();
        month4.showAllTask();
        month5.showAllTask();
    }
    
    public int getTotalTasks() {
        return month1.getTotalTasks() + month2.getTotalTasks() + month3.getTotalTasks() + month4.getTotalTasks() + month5.getTotalTasks();
    }
    
    public int getCompletedTasks() {
        return month1.getCompletedTasks() + month2.getCompletedTasks() + month3.getCompletedTasks() + month4.getCompletedTasks() + month5.getCompletedTasks();
    }
    
    public int getDayTotal(int month, int date) {
        if (month == 1) {
            return month1.getDayTotal(date);
        } else if (month == 2) {
            return month2.getDayTotal(date);
        } else if (month == 3) {
            return month3.getDayTotal(date);
        } else if (month == 4) {
            return month4.getDayTotal(date);
        } else {
            return month5.getDayTotal(date);
        }
    }
    
    public int getDayCompleted(int month, int date) {
        if (month == 1) {
            return month1.getDayCompleted(date);
        } else if (month == 2) {
            return month2.getDayCompleted(date);
        } else if (month == 3) {
            return month3.getDayCompleted(date);
        } else if (month == 4) {
            return month4.getDayCompleted(date);
        } else {
            return month5.getDayCompleted(date);
        }
    }
    
}
