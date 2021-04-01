import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.Thread;



public class main {
    public static PrintWriter pw;
    public static void main (String[] args) throws IOException {

        pw = new PrintWriter("output.txt");

        //------------- File input handling -------------------------
        int q = 0;
        List<User> users = new ArrayList<User>();

        // get info from input.txt
        try{
            File in = new File("C:\\Users\\Nicolas\\IdeaProjects\\as2(1)\\as2\\src\\input.txt");
            Scanner inRead = new Scanner(in);
            // first line is quantum
            q = inRead.nextInt();

            //get users and their processes
            while(inRead.hasNext())
            {
                String userName = inRead.next();
                int numOfPro = inRead.nextInt();
                // get all processes for a user

                users.add(new User());
                for (int i =0; i< numOfPro; i++) {

                    users.get(users.size()-1).addPQ(new Process(userName, inRead.nextInt(), inRead.nextInt(), i));

                }

            }
            inRead.close();
        } catch (FileNotFoundException e){
            System.out.println(e);
        }


        // ------------- Begin Scheduling ----------------
        Thread schedule = null;
        try {
            schedule = new Thread(new Scheduler(users, q));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        schedule.start();
        try {
            schedule.join();
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        pw.close();

    }
}
