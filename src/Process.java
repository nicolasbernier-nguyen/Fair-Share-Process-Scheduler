import java.io.*;
import java.lang.Runnable;

public class Process implements Runnable {


    //------ Varriables ------
    private int bt;
    private int at;
    private int id;
    private String uName;
    private State state;

    // ----- Constructors --------
    Process(String n, int a, int b, int i) throws IOException {
        uName = n;
        at = a * 1000;
        bt = b * 1000;
        id = i;
        state = State.STARTED;
    }

    //---------- Methods ----------
    public State getState() {
        return state;
    }

    public void setState(State s) {
        if (state != State.FINISHED)
            state = s;

    }

    public int getAt() {
        return at;
    }


    //---------- Runnable ----------
    public void run() {
        State s = state;
        while (bt != 0) {
            // did the state change?
            if (s != state || s == State.STARTED) {
                // option for when a state changes
                switch (state) {
                    case STARTED:
                        main.pw.write("Time " + Clock.getClock() / 1000 + ", User " + uName + ", Process " + id + " STARTED" + '\n');
                        state = State.RESUMED;
                    case RESUMED:
                        main.pw.write("Time " + Clock.getClock() / 1000 + ", User " + uName + ", Process " + id + " RESUMED" + '\n');
                        break;
                    case PAUSED:
                        main.pw.write("Time " + Clock.getClock() / 1000 + ", User " + uName + ", Process " + id + " PAUSED" + '\n');
                        break;
                    default:
                        System.out.println("NONE");
                }
                // update last state
                s = state;
            } // end of if (s != state || s == State.STARTED)

            if (state == State.RESUMED) {
                bt -= 10;

                if (bt == 0) {
                    state = State.FINISHED;
                    main.pw.write("Time " + Clock.getClock() / 1000 + ", User " + uName + ", Process " + id + " FINISHED" + '\n');
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        } //end of while (bt != 0)

    }
}
