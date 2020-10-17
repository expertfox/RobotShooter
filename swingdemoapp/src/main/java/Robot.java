import javax.swing.*;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

//IN THIS CLASS, THE ROBOT STEPS ARE INCREMENTED TO VALID SPOTS
//EVERY ROBOT RUNS IN SEPARATE THREADS


public class Robot {

    Thread n;
    boolean InterruptStatus = true;
    double x = 0;
    double y = 0;
    private int uniqueid;
    Random randVar = new Random();

    public int getRandDelay() {
        return randDelay;
    }


    int randDelay= randVar.nextInt(1500)+500;

    private AtomicBoolean running = new AtomicBoolean(false);

    public Robot() {
    }

    public Robot(double x, double y, int uniqueid) {
        this.x = x;
        this.y = y;
        this.uniqueid = uniqueid;


        App.logger.append("ROBOT "+getUniqueid()+" Started\n");
        startThread();
    }

    //----------------------------Methods for the stepping--------------------------//

    public class SimpleThread extends Thread {
        public SimpleThread(String str) {
            super(str);
        }

        public void run() {
            System.out.println("Thread" + Thread.currentThread().getId());
                running.set(true);
                while (running.get()) {

                    try {
                        Thread.sleep(randDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int step = randVar.nextInt(8);
                    if(getY() == 4 && getX() ==4 ){
                        System.out.println("REACHED MIDDLE "+"ROBOT:"+getUniqueid());
                        JOptionPane.showMessageDialog(App.window, "GAME OVER! Your Score:"+App.GAMESCORE);

                    }else {
                        switch (step) {
                            case 0:
                                moveUp();
                                break;
                            case 1:
                                moveDown();
                                break;
                            case 2:
                                moveLeft();
                                break;
                            case 3:
                                moveRight();
                                break;
                            case 4:
                                moveUpLeft();
                                break;
                            case 5:
                                moveUpRight();
                                break;
                            case 6:
                                moveDownLeft();
                                break;
                            case 7:
                                moveDownRight();
                                break;
                            default:
                        }
                        App.arena.repaint();
                    }

                }
                this.interrupt();
        }
    }


    //REPAINTING FUNCTION
    public void repaintScreen(){
        App.arena.repaint();
    }

    //Start Robot Thread

    public void startThread(){
        n = new SimpleThread("obj");
        n.start();
    }

    //STOP ROBOT THREAD
    public void interruptThread(){

        try {
            if (Thread.currentThread().isAlive()) {
                running.set(false);
                Thread.currentThread().interrupt();
                System.out.println("THREAD" + Thread.currentThread().getId() + " REMOVED");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   // -------- MOVEMENT AND MOVEMENT VALIDATION ------------//
    private void moveDownRight() {
        if (getX() < 8 && getY() < 8) {
            if (validateDownRight()) {
                setX(getX() + 1);
                setY(getY() + 1);
            }
        }
    }
    private boolean validateDownRight() {
        boolean check = true;

        if (!RobotToQueue.robotListBlockingQueue.isEmpty()) {
            Iterator<Robot> robotObj = RobotToQueue.robotListBlockingQueue.iterator();

            while (robotObj.hasNext()) {
                Robot robo = robotObj.next();
                if ((robo.getX() == getX() + 1) || (robo.getY() == getY() + 1)) {
                    check = false;
                }
            }

        }
        return check;
    }

    private void moveDownLeft() {
        if (getX() > 0 && getY() < 8) {
            if (validateDownLeft()) {
                setX(getX() - 1);
                setY(getY() + 1);
            }
        }

    }
    private boolean validateDownLeft() {
        boolean check = true;

        if (!RobotToQueue.robotListBlockingQueue.isEmpty()) {
            Iterator<Robot> robotObj = RobotToQueue.robotListBlockingQueue.iterator();

            while (robotObj.hasNext()) {
                Robot robo = robotObj.next();
                if ((robo.getX() == getX() - 1) || (robo.getY() == getY() + 1)) {
                    check = false;
                }
            }

        }
        return check;
    }

    private void moveUpRight() {
        if (getX() < 8 && getY() > 0) {
            if (validateUpRight()) {
                setX(getX() + 1);
                setY(getY() - 1);
            }
        }

    }
    private boolean validateUpRight() {
        boolean check = true;

        if (!RobotToQueue.robotListBlockingQueue.isEmpty()) {
            Iterator<Robot> robotObj = RobotToQueue.robotListBlockingQueue.iterator();

            while (robotObj.hasNext()) {
                Robot robo = robotObj.next();
                if ((robo.getX() == getX() + 1) || (robo.getY() == getY() - 1)) {
                    check = false;
                }
            }

        }
        return check;

    }

    private void moveUpLeft() {
        if (getX() > 0 && getY() > 0) {
            if(validateUpLeft()) {
                setX(getX() - 1);
                setY(getY() - 1);
            }
        }
    }
    private boolean validateUpLeft() {

        boolean check = true;

        if (!RobotToQueue.robotListBlockingQueue.isEmpty()) {
            Iterator<Robot> robotObj = RobotToQueue.robotListBlockingQueue.iterator();

            while (robotObj.hasNext()) {
                Robot robo = robotObj.next();
                if ((robo.getX() == getX() - 1) || (robo.getY() == getY() - 1)) {
                    check = false;
                }
            }

        }
        return check;
    }

    private void moveRight() {
        if (getX() < 8) {
            if(validateRight())
                setX(getX() + 1);
        }
    }
    private boolean validateRight() {
        boolean check = true;

        if (!RobotToQueue.robotListBlockingQueue.isEmpty()) {
            Iterator<Robot> robotObj = RobotToQueue.robotListBlockingQueue.iterator();

            while (robotObj.hasNext()) {
                Robot robo = robotObj.next();
                if (robo.getX() == getX() + 1) {
                    check = false;
                }
            }

        }
        return check;
    }

    private void moveLeft() {
        if (getX() > 0){
            if(validateLeft())
                setX(getX() - 1);
        }
    }
    private boolean validateLeft() {
        boolean check = true;

        if (!RobotToQueue.robotListBlockingQueue.isEmpty()) {
            Iterator<Robot> robotObj = RobotToQueue.robotListBlockingQueue.iterator();

            while (robotObj.hasNext()) {
                Robot robo = robotObj.next();
                if (robo.getX() == getX() - 1) {
                    check = false;
                }
            }

        }
        return check;
    }

    private void moveDown() {
        if (getY() < 8) {
            if(validateDown())
                setY(getY() + 1);
        }
    }
    private boolean validateDown() {
        boolean check = true;

        if (!RobotToQueue.robotListBlockingQueue.isEmpty()) {
            Iterator<Robot> robotObj = RobotToQueue.robotListBlockingQueue.iterator();

            while (robotObj.hasNext()) {
                Robot robo = robotObj.next();
                if (robo.getY() == getY() + 1) {
                    check = false;
                }
            }

        }
        return check;
    }

    private void moveUp() {
        if (getY() > 0) {
            if (validateUp())
                setY(getY() - 1);
        }
    }
    private boolean validateUp() {
        boolean check = true;

        if (!RobotToQueue.robotListBlockingQueue.isEmpty()) {
            Iterator<Robot> robotObj = RobotToQueue.robotListBlockingQueue.iterator();

            while (robotObj.hasNext()) {
                Robot robo = robotObj.next();
                if (robo.getY() == getY() - 1) {
                    check = false;
                }
            }
        }
        return check;
    }


    //----------------------------END Methods for the stepping--------------------------//

    public double getX() {
        return x;
    }

    public void setX(double x) {
            this.x = x;
            repaintScreen();
    }
    public double getY() {
        return y;
    }

    public void setY(double y) {
            this.y = y;
            repaintScreen();
    }

    public int getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(int uniqueid) {
        this.uniqueid = uniqueid;
    }


}
