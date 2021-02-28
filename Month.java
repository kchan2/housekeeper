/*
 * Month - a Hashmap with dates as keys and lists of assignments as values
 */
package wichackathon2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author notebook
 */
public class Month {

    private String name;
    private HashMap<Integer, List<Task>> month;

    public Month(String name) {
        this.name = name;
        month = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public HashMap<Integer, List<Task>> getHashmap() {
        return month;
    }

    public void addTask(int date, String task, boolean done) {
        if (month.containsKey(date)) {
            if (!month.get(date).contains(task)) {
                month.get(date).add(new Task(task, done));
            }
        } else {
            month.put(date, new ArrayList<Task>());
            month.get(date).add(new Task(task, done));
        }
    }

    public void deleteTask(int date, int index) {
        if (index >= 0 && index < month.get(date).size()) {
            month.get(date).remove(index);
        } else if (index < -1 || index >= month.get(date).size()) {
            System.out.println("Not a valid index. Try again.");
        }
    }

    public void finishTask(int date, int index) {
        if (index >= 0 && index < month.get(date).size()) {
            month.get(date).get(index).finish();
        } else if (index < -1 || index >= month.get(date).size()) {
            System.out.println("Not a valid index. Try again.");
        }
    }

    public void showTask(int date) {
        if (month.containsKey(date)) {
            if (month.get(date).isEmpty()) {
                System.out.println("No task on this date.");
            } else {
                for (int i = 0; i < month.get(date).size(); i++) {
                    String taskName = month.get(date).get(i).getName();
                    int index = i + 1;
                    System.out.println(index + ". " + taskName + " Done? " + month.get(date).get(i).isDone());
                }
            }
        } else {
            System.out.println("No task on this date.");
        }
    }

    public void showAllTask() {
        for (Map.Entry day : month.entrySet()) {
            System.out.println(name + " " + day.getKey());
            int counter = 1;
            for (Task t : (List<Task>)day.getValue()) {
                String taskName = t.getName();
                System.out.println(counter + ". " + taskName + " Done? " + t.isDone());
                counter++;
            }
        }
    }

    public int getTotalTasks() {
        int total = 0;
        for (Map.Entry day : month.entrySet()) {
            List<Task> tasks = (List<Task>) day.getValue();
            int nrOfTasks = tasks.size();
            total = total + nrOfTasks;
        }
        return total;
    }

    public int getCompletedTasks() {
        int completed = 0;
        for (Map.Entry day : month.entrySet()) {
            List<Task> tasks = (List<Task>) day.getValue();
            int nrOfCompletedTasks = 0;
            for (Task t : tasks) {
                if (t.isDone()) {
                    nrOfCompletedTasks++;
                }
            }
            completed = completed + nrOfCompletedTasks;
        }
        return completed;
    }

    public int getDayTotal(int date) {
        if (month.containsKey(date)) {
            if (month.get(date).isEmpty()) {
                return 0;
            } else {
                return month.get(date).size();
            }
        }
        return 0;
    }

    public int getDayCompleted(int date) {
        if (month.containsKey(date)) {
            if (month.get(date).isEmpty()) {
                return 0;
            } else {
                int completed = 0;
                for (int i = 0; i < month.get(date).size(); i++) {
                    if (month.get(date).get(i).isDone()) {
                        completed++;
                    }
                }
                return completed;
            }
        }
        return 0;
    }
}
