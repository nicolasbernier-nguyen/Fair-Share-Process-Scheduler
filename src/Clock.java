import java.lang.Runnable;

public class Clock implements Runnable{
   // --- Variables ---

    private static int clock;

    // --- Constructor ---
    Clock(){
        clock = 1000;
    }

    // --- Methods ---
    static public int getClock() {
        return clock;
    }
    // ---- Runnable ----
    public void run() {
        while(!Scheduler.isDone()) {

            try {
                Thread.sleep(10);
            }catch(InterruptedException e)
            {
                System.out.println(e);
            }
            clock+=10;
        }

    }
}
