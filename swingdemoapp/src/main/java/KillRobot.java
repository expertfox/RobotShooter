import javax.swing.*;
import java.util.Iterator;

public class KillRobot implements Runnable{
    Click click;


    //ONE THREAD FOR ONE CLICK
    public void run(){
        if(!SwingArena.clickListBlockingQueue.isEmpty()){
            try {
                click = SwingArena.clickListBlockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!RobotToQueue.robotListBlockingQueue.isEmpty()) {
                Iterator<Robot> robotObj = RobotToQueue.robotListBlockingQueue.iterator();

                while (robotObj.hasNext()) {
                    Robot robo = robotObj.next();
                    if (robo.getY() == click.getY()  && robo.getX() == click.getX()){
                        System.out.println("Grid vals:" + click.getX()  + "," +click.getY()+ "---" + "Robo vals:" + robo.getX() +","+ robo.getY());
                        robo.interruptThread();
                        RobotToQueue.robotListBlockingQueue.remove(robo);

                        //calc score when killed
                        long finaltime = System.currentTimeMillis();
                        long killdelay = finaltime - App.startTime;
                        App.GAMESCORE = App.GAMESCORE + 10 + 100*((int)killdelay/robo.getRandDelay());
                        App.logger.append("ROBOT "+robo.getUniqueid()+" Killed\n");
                        System.out.println("SCORE="+App.GAMESCORE);
                        App.arena.repaint();
                    }
                }

            }
//            if(RobotToQueue.robotListBlockingQueue.isEmpty()) {
//                JOptionPane.showMessageDialog(App.window, "You Win! Your Score:" + App.GAMESCORE);
//            }
        }
    }
}
