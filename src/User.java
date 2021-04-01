import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.List;

public class User {


    // --- Varriables ---
    private List<Process> queue; // all processes not yet ready



    private List<Process> readyQ;


    // --- Constructors ----
    public User() {

        queue = new ArrayList<Process>();
        readyQ = new ArrayList<Process>();
    }
    // ---- Methods -----
    public List<Process> getReadyQ() {
        return readyQ;
    }

    public List<Process> getQ() {
        return queue;
    }

    public void addPQ(Process p) { // add process to queue
        queue.add(p);

    }
    public void moveToReadyQ(Process p, int i){ // move process from queue to readyQ
         readyQ.add(p);
         queue.remove(i);
    }
    public boolean hasProcess(){ // tells us if a user has any processes left
        System.out.println(!(queue.isEmpty() && allDoneP()));
        return !(queue.isEmpty() && allDoneP());

    }
    public boolean allDoneP(){
        boolean done = true;
        for (Process process : readyQ) {
            if (process.getState() != State.FINISHED) {
                done = false;
                break;
            }

        }
        return done;

    }
    public boolean isReady(){ // if a user is ready
        return !(readyQ.isEmpty());
    }

}
