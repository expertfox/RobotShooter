import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

//THis runnable creates robots from randomRobot class to get a random position and puts them into a shared blocking queue
public class RobotToQueue implements Runnable {
    //blocking queue
    public static BlockingQueue<Robot> robotListBlockingQueue = new LinkedBlockingDeque<>(10);
    public static int ROBOFREQ =10;

    //TO CREATE RANDOM ROBOTS
    public RandomRobot randomRobot= new RandomRobot();

    public void run(){
        int i =1;
        while (i <= ROBOFREQ ) {
            try {
                robotListBlockingQueue.put(randomRobot.getRandRobot());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}