import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.lang.Thread;

public class Scheduler implements  Runnable {
    //------ Varriables ------
    private List<User> users;
    private List<Thread> threads;
    private int quantum;
    private Clock clock;
    private static boolean done;

    // ----- Constructors --------
     Scheduler(List<User> users, int quan) throws FileNotFoundException {
        this.users = users;
        quantum = quan*1000;
        clock = new Clock();
        threads = new ArrayList<Thread>();
        done = false;

    }

    // ---------- Methods ----------
    public static boolean isDone() {
        return done;
    }

    //---------- Runnable ----------
    public void run() {

        Thread c = new Thread(clock);
        c.start();
        while(!done)
        {

            //  -- get ready process from user queue and send to ready queue --
            int quanU = 0; // number of ready users
           // go through all users
            for (User user : users) {
                //go through processes in q for a user
                for (int j = 0; j < user.getQ().size(); j++) {
                    Process p = user.getQ().get(j);

                    if (p.getAt() <= Clock.getClock()) {
                        user.moveToReadyQ(p, j); //if process is ready move to user readyQ

                    }
                }
                if (user.isReady())
                    quanU++; //number of users with ready processes
            }

        // fair share handling
            for(User u : users) {
                if (u.isReady()) {
                    int quanpp = (quantum / quanU) / u.getReadyQ().size(); // quantum per process
                    for (Process p : u.getReadyQ()) {
                        if(p.getState() != State.FINISHED) {
                            if (p.getState() == State.PAUSED) {
                                try {
                                    Thread.sleep(20); //delay to ensure process state updates and outputs to file
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                p.setState(State.RESUMED);
                                try {
                                    Thread.sleep(20); //delay to ensure process state updates and outputs to file
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                Thread t = new Thread(p); // list of threads for join
                                t.start();
                                threads.add(t);
                            }

                            try {
                                for(int i = 0; i <= quanpp; i += 10){ //time slice loop
                                    Thread.sleep(10);
                                }

                            } catch (InterruptedException e) {
                                System.out.println(e);
                                e.printStackTrace();
                            }

                            p.setState(State.PAUSED);

                            try {
                                Thread.sleep(20); //delay to ensure process state updates and outputs to file
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } // end of if(p.getState() != State.FINISHED)
                    } // end of for (Process p : u.getReadyQ())
                } // end of if (u.isReady())
            } // end of for(User u : users)

            // --- check if any processes are left un finished ---
            done = true;
            for(User u: users)
            {
                if (u.hasProcess())
                    done =false;
            }

        }

        // ---- NO MORE PROCESSES LEFT PREPARE FOR SHUTTING DOWN PROGRAM -----
         // join all threads
        try {
            c.join(); // clock thread
        }catch (InterruptedException e)
        {
            System.out.println(e);
        }
        for(Thread t: threads)
        {
            try {
                t.join(); // process threads
            }catch (InterruptedException e)
            {
                System.out.println(e);
            }
        }

    }
}




