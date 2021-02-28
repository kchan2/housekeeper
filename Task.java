/*
 * Task - Name, Status
 */
package wichackathon2020;

/**
 *
 * @author notebook
 */
public class Task {
    
    private String name;
    private boolean done;
    
    public Task(String name, boolean done) {
        this.name = name;
        this.done = done;
    }
    
    public void finish() {
        done = true;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isDone() {
        return done;
    }
}
