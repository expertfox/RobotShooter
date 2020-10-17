import java.util.Iterator;
import java.util.Random;

import static java.lang.Thread.sleep;

//THIS IS THE CLASS TO CREATE RANDOM ENEMY ROBOTS OBJECTS FROM A RANDOM CORNER
//USE THE getRandRobot()
public class RandomRobot {
    public static int UNIQUEID = 1;
    private Random var = new Random();
    private int xPos;
    private int yPos;

    public RandomRobot() {

    }

    public Robot getRandRobot() {
        switch(var.nextInt(4)) {
            case 0:
                xPos = 0;
                yPos = 0;
                break;
            case 1:
                xPos = 8;
                yPos = 0;
                break;
            case 2:
                xPos = 0;
                yPos = 8;
                break;
            case 3:
                xPos = 8;
                yPos = 8;
                break;
            default:
                // code block
        }
        System.out.println(UNIQUEID+" x:" + xPos +" y:" + yPos);
        while(invalidSpawn()) {
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Robot r = new Robot(this.xPos, this.yPos, UNIQUEID);
        UNIQUEID++;
        return r;
    }

    public boolean invalidSpawn(){
        boolean check = false;
        if(!RobotToQueue.robotListBlockingQueue.isEmpty()) {
            Iterator<Robot> robotObj = RobotToQueue.robotListBlockingQueue.iterator();

            while (robotObj.hasNext()) {
                Robot robo = robotObj.next();
                if (robo.getX() == xPos && robo.getY() == yPos)
                {
                    check = true;
                    return check;
                }
            }
            return check;
        }
        return check;
    }
}
