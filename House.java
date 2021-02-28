/*
 * House
 */
package wichackathon2020;

/**
 *
 * @author notebook
 */
public class House {
    
    private boolean damaged;
    
    public House(boolean status) {
        damaged = status;
    }
    
    public void changeStatus() {
        damaged = !damaged;
    }
    
    public void stateStatus() {
        if (damaged) {
            System.out.println("Oh no! Your house is damaged. \n"
                    + "Please finish your tasks as soon as possible to fix your house.");
        } else {
            System.out.println("Your house is in a good condition. Keep up the good work!");
        }
    }
    
}
