/*
 * Interesting way of tracking your tasks!
 */
package wichackathon2020;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author notebook
 */
public class Housekeeper {

    static Calendar calendar;
    static House house;
    static Shield shield;
    static String day;
    static int semYear;
    static int year;
    static String month;
    static int date;
    static String time;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Make a scanner for user input
        Scanner s = new Scanner(System.in);
        System.out.println("HOUSEKEEPER!");
        System.out.print("\nEnter S to start a new semester. Enter L to load an existing semester. ");
        String start = s.nextLine();
        startSchedule(s, start);
    }

    private static void startSchedule(Scanner s, String start) {
        if (start.equalsIgnoreCase("s")) {
            System.out.print("Year? ");
            semYear = Integer.parseInt(s.nextLine());
            System.out.print("\nEnter F for fall semester. Enter S for spring semester. ");
            String semester = s.nextLine();
            createCalendar(s, semester, semYear);
            house = new House(false); // Not damaged
            shield = new Shield(calendar);
            interpreter(s);
        } else if (start.equalsIgnoreCase("l")) {
            System.out.print("Enter the file name: ");
            String filename = s.nextLine();
            readXML(filename);
            house = new House(calculateDamage()); // Need to calculate
            shield = new Shield(calendar);
            interpreter(s);
        } else {
            System.out.println("Not a valid input.");
            System.out.print("\nEnter S to start a new semester. Enter L to load an existing semester. ");
            String another = s.nextLine();
            startSchedule(s, another);
        }
    }

    private static void createCalendar(Scanner s, String semester, int year) {
        if (semester.equalsIgnoreCase("f") || semester.equalsIgnoreCase("s")) {
            calendar = new Calendar(semester, year);
        } else {
            System.out.println("Not a valid semester.");
            System.out.print("\nEnter F for fall semester. Enter S for spring semester. ");
            String another = s.nextLine();
            createCalendar(s, another, year);
        }
    }

    private static void updateDT() {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter formatdt = DateTimeFormatter.ofPattern("E dd MMM yyyy HH:mm:ss");
        String formatteddt = dt.format(formatdt);
        day = formatteddt.substring(0, 3);
        date = Integer.parseInt(formatteddt.substring(4, 6));
        month = formatteddt.substring(7, 10);
        year = Integer.parseInt(formatteddt.substring(11, 15));
        time = formatteddt.substring(16);
        System.out.println("Today is " + day + ", " + month + " " + date + ", " + year + " Current time: " + time);
    }

    private static int monthIndex(String month) {
        if (month.equalsIgnoreCase("AUG") || month.equalsIgnoreCase("JAN")) {
            return 1;
        } else if (month.equalsIgnoreCase("SEP") || month.equalsIgnoreCase("FEB")) {
            return 2;
        } else if (month.equalsIgnoreCase("OCT") || month.equalsIgnoreCase("MAR")) {
            return 3;
        } else if (month.equalsIgnoreCase("NOV") || month.equalsIgnoreCase("APR")) {
            return 4;
        } else if (month.equalsIgnoreCase("DEC") || month.equalsIgnoreCase("MAY")) {
            return 5;
        } else {
            return 0;
        }
    }

    private static boolean validMonth(String month) {
        if (calendar.getSemester().equalsIgnoreCase("f")) {
            return (month.equalsIgnoreCase("AUG") || month.equalsIgnoreCase("SEP") || month.equalsIgnoreCase("OCT") || month.equalsIgnoreCase("NOV") || month.equalsIgnoreCase("DEC"));
        } else {
            return (month.equalsIgnoreCase("JAN") || month.equalsIgnoreCase("FEB") || month.equalsIgnoreCase("MAR") || month.equalsIgnoreCase("APR") || month.equalsIgnoreCase("MAY"));
        }
    }

    private static void interpreter(Scanner s) {
        updateDT();
        while (true) {
            System.out.print("\nWhat do you want to do? Enter H for list of commands. ");
            String cmd = s.nextLine();
            if (cmd.equalsIgnoreCase("H")) {
                System.out.println("A - Add a task\n"
                        + "C - Check conditions of house and shield\n"
                        + "D - Delete a task\n"
                        + "F - Finish a task\n"
                        + "H - Display all available commands\n"
                        + "S - Show tasks in the semester\n"
                        + "T - Display current date and time\n"
                        + "/ - End the program");
            } else if (cmd.equalsIgnoreCase("A")) {
                addTask(s);
            } else if (cmd.equalsIgnoreCase("C")) {
                conditionReport();
            } else if (cmd.equalsIgnoreCase("D")) {
                deleteTask(s);
            } else if (cmd.equalsIgnoreCase("F")) {
                finishTask(s);
            } else if (cmd.equalsIgnoreCase("S")) {
                showTasks(s);
            } else if (cmd.equalsIgnoreCase("T")) {
                updateDT();
            } else if (cmd.equalsIgnoreCase("/")) {
                System.out.println("Do you want to save the current semester?");
                System.out.print("Y - Yes\n"
                        + "N - NO\n"
                        + "Any other key - Stay\n");
                String save = s.nextLine();
                if (save.equalsIgnoreCase("Y")) {
                    saveAsXML();
                    System.out.println("Bye!");
                    break;
                } else if (save.equalsIgnoreCase("N")) {
                    System.out.println("Bye!");
                    break;
                }
            } else {
                System.out.println("Not a valid command.");
            }
        }
    }

    private static boolean calculateDamage() {
        return false; // TBD
    }

    private static void addTask(Scanner s) {
        System.out.println("\nEnter the due date of the task: ");
        System.out.print("Month? (Enter the first three letter) ");
        String m = s.nextLine();
        if (validMonth(m)) {
            int month = monthIndex(m);
            System.out.print("Date? ");
            int date = Integer.parseInt(s.nextLine());
            if (date > 0 && date < 32) {
                System.out.print("Name of the task? ");
                String task = s.nextLine();
                calendar.addTask(month, date, task, false);
            } else {
                System.out.println("Not a valid date. Try again.");
            }
        } else {
            System.out.println("Not a valid month. Try again.");
        }
    }

    private static void conditionReport() {
        System.out.println("Shield completion: " + shield.getCompletion(calendar) + "%");
        house.stateStatus();
    }

    private static void deleteTask(Scanner s) {
        System.out.println("\nEnter the due date of the task: ");
        System.out.print("Month? (Enter the first three letter) ");
        String m = s.nextLine();
        if (validMonth(m)) {
            int month = monthIndex(m);
            System.out.print("Date? ");
            int date = Integer.parseInt(s.nextLine());
            if (date > 0 && date < 32) {
                calendar.showTask(month, date);
                if (calendar.getDayTotal(month, date) != 0) {
                    System.out.println("Which task are you deleting? ");
                    System.out.print("Enter the index of the task or enter 0 to cancel the command: ");
                    int index = Integer.parseInt(s.nextLine());
                    calendar.deleteTask(month, date, index - 1);
                }
            } else {
                System.out.println("Not a valid date. Try again.");
            }
        } else {
            System.out.println("Not a valid month. Try again.");
        }
    }

    private static void finishTask(Scanner s) {
        System.out.println("\nEnter the due date of the task: ");
        System.out.print("Month? (Enter the first three letter) ");
        String m = s.nextLine();
        if (validMonth(m)) {
            int month = monthIndex(m);
            System.out.print("Date? ");
            int date = Integer.parseInt(s.nextLine());
            if (date > 0 && date < 32) {
                calendar.showTask(month, date);
                if (calendar.getDayTotal(month, date) != 0) {
                    System.out.println("Which task are you finishing? ");
                    System.out.print("Enter the index of the task or enter 0 to cancel the command: ");
                    int index = Integer.parseInt(s.nextLine());
                    calendar.finishTask(month, date, index - 1);
                }
            } else {
                System.out.println("Not a valid date. Try again.");
            }
        } else {
            System.out.println("Not a valid month. Try again.");
        }
    }

    private static void showTasks(Scanner s) {
        System.out.println("Enter:\n"
                + "A - all tasks in the semester\n"
                + "D - all tasks that are done\n"
                + "N - all tasks that are not done\n"
                + "U - Urgent tasks (Past due)\n"
                + "M - all tasks in a month\n"
                + "T - today's tasks\n"
                + "Any other key - cancel the action\n");
        String cmd = s.nextLine();
        if (cmd.equalsIgnoreCase("A")) {
            calendar.showAllTask();
        } else if (cmd.equalsIgnoreCase("D")) {
            System.out.println("TBD...");
        } else if (cmd.equalsIgnoreCase("N")) {
            System.out.println("TBD...");
        } else if (cmd.equalsIgnoreCase("U")) {
            System.out.println("TBD...");
        } else if (cmd.equalsIgnoreCase("M")) {
            System.out.println("TBD...");
        } else if (cmd.equalsIgnoreCase("T")) {
            System.out.println("TBD...");
        } else {
        }
    }

    private static void saveAsXML() {
        try {
            // create new `Document`
            DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            Document dom = builder.newDocument();

            // first create root element
            Element root = dom.createElement("schedule");
            dom.appendChild(root);

            // now create child elements (name, email, phone)
            Element cal = dom.createElement("calendar");
            Attr semester = dom.createAttribute("semester");
            semester.setValue(calendar.getSemester());
            cal.setAttributeNode(semester);
            Attr year = dom.createAttribute("year");
            year.setValue(Integer.toString(semYear));
            cal.setAttributeNode(year);

            Element month1 = dom.createElement("month1");
            Attr m1name = dom.createAttribute("name");
            m1name.setValue(calendar.getMonth(1).getName());
            month1.setAttributeNode(m1name);
            for (Map.Entry day : calendar.getMonth(1).getHashmap().entrySet()) {
                Element entry = dom.createElement("entry");
                Attr date = dom.createAttribute("date");
                date.setValue(Integer.toString((int) day.getKey()));
                entry.setAttributeNode(date);
                for (Task t : (List<Task>) day.getValue()) {
                    Element task = dom.createElement("task");
                    Attr name = dom.createAttribute("name");
                    name.setValue(t.getName());
                    task.setAttributeNode(name);
                    Attr done = dom.createAttribute("done");
                    done.setValue(Boolean.toString(t.isDone()));
                    task.setAttributeNode(done);
                    entry.appendChild(task);
                }
                month1.appendChild(entry);
            }
            Element month2 = dom.createElement("month2");
            Attr m2name = dom.createAttribute("name");
            m2name.setValue(calendar.getMonth(2).getName());
            month2.setAttributeNode(m2name);
            for (Map.Entry day : calendar.getMonth(2).getHashmap().entrySet()) {
                Element entry = dom.createElement("entry");
                Attr date = dom.createAttribute("date");
                date.setValue(Integer.toString((int) day.getKey()));
                entry.setAttributeNode(date);
                for (Task t : (List<Task>) day.getValue()) {
                    Element task = dom.createElement("task");
                    Attr name = dom.createAttribute("name");
                    name.setValue(t.getName());
                    task.setAttributeNode(name);
                    Attr done = dom.createAttribute("done");
                    done.setValue(Boolean.toString(t.isDone()));
                    task.setAttributeNode(done);
                    entry.appendChild(task);
                }
                month2.appendChild(entry);
            }
            Element month3 = dom.createElement("month3");
            Attr m3name = dom.createAttribute("name");
            m3name.setValue(calendar.getMonth(3).getName());
            month3.setAttributeNode(m3name);
            for (Map.Entry day : calendar.getMonth(3).getHashmap().entrySet()) {
                Element entry = dom.createElement("entry");
                Attr date = dom.createAttribute("date");
                date.setValue(Integer.toString((int) day.getKey()));
                entry.setAttributeNode(date);
                for (Task t : (List<Task>) day.getValue()) {
                    Element task = dom.createElement("task");
                    Attr name = dom.createAttribute("name");
                    name.setValue(t.getName());
                    task.setAttributeNode(name);
                    Attr done = dom.createAttribute("done");
                    done.setValue(Boolean.toString(t.isDone()));
                    task.setAttributeNode(done);
                    entry.appendChild(task);
                }
                month3.appendChild(entry);
            }
            Element month4 = dom.createElement("month4");
            Attr m4name = dom.createAttribute("name");
            m4name.setValue(calendar.getMonth(4).getName());
            month4.setAttributeNode(m4name);
            for (Map.Entry day : calendar.getMonth(4).getHashmap().entrySet()) {
                Element entry = dom.createElement("entry");
                Attr date = dom.createAttribute("date");
                date.setValue(Integer.toString((int) day.getKey()));
                entry.setAttributeNode(date);
                for (Task t : (List<Task>) day.getValue()) {
                    Element task = dom.createElement("task");
                    Attr name = dom.createAttribute("name");
                    name.setValue(t.getName());
                    task.setAttributeNode(name);
                    Attr done = dom.createAttribute("done");
                    done.setValue(Boolean.toString(t.isDone()));
                    task.setAttributeNode(done);
                    entry.appendChild(task);
                }
                month4.appendChild(entry);
            }
            Element month5 = dom.createElement("month5");
            Attr m5name = dom.createAttribute("name");
            m5name.setValue(calendar.getMonth(5).getName());
            month5.setAttributeNode(m5name);
            for (Map.Entry day : calendar.getMonth(5).getHashmap().entrySet()) {
                Element entry = dom.createElement("entry");
                Attr date = dom.createAttribute("date");
                date.setValue(Integer.toString((int) day.getKey()));
                entry.setAttributeNode(date);
                for (Task t : (List<Task>) day.getValue()) {
                    Element task = dom.createElement("task");
                    Attr name = dom.createAttribute("name");
                    name.setValue(t.getName());
                    task.setAttributeNode(name);
                    Attr done = dom.createAttribute("done");
                    done.setValue(Boolean.toString(t.isDone()));
                    task.setAttributeNode(done);
                    entry.appendChild(task);
                }
                month5.appendChild(entry);
            }
            cal.appendChild(month1);
            cal.appendChild(month2);
            cal.appendChild(month3);
            cal.appendChild(month4);
            cal.appendChild(month5);

            // add child nodes to root node
            root.appendChild(cal);

            // write DOM to XML file
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.transform(new DOMSource(dom), new StreamResult(new File(calendar.getSemester() + semYear + ".xml")));
            System.out.println("File is saved as " + calendar.getSemester() + semYear + ".xml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void readXML(String filename) {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
    	try {
        	SAXParser saxParser = saxParserFactory.newSAXParser();
        	MyHandler handler = new MyHandler();
        	File input = new File(filename);
        	saxParser.parse(input, handler);
                calendar = handler.getCalendar();

    	} catch (Exception e) {
        	e.printStackTrace();
    	}
    }
}
