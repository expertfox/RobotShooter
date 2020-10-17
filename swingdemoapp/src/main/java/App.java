import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;

import static java.lang.Thread.sleep;

public class App 
{

    ExecutorService executor1 = Executors.newFixedThreadPool(5);
    public static SwingArena arena;
    public static JFrame window;
    public static JScrollPane loggerArea;
    public static JTextArea logger;

    public static long startTime;

    public static int GAMESCORE = 0;

    public static void main(String[] args) 
    {
        // Note: SwingUtilities.invokeLater() is equivalent to JavaFX's Platform.runLater().
        SwingUtilities.invokeLater(() ->
        {
            window = new JFrame("Example App (Swing)");
            arena = new SwingArena();
            JLabel label = new JLabel("Score: "+ GAMESCORE);
            arena.addListener((x, y) ->
            {
                System.out.println("Arena click at (" + x + "," + y + ")");

            });


            JToolBar toolbar = new JToolBar();
            JButton btn1 = new JButton("START GAME");
            logger = new JTextArea();
            loggerArea = new JScrollPane(logger);
            loggerArea.setBorder(BorderFactory.createEtchedBorder());

            toolbar.add(btn1);
            toolbar.add(label);

            RobotToQueue robocop = new RobotToQueue();

            //Start button action listner
            //
             btn1.addActionListener((event) ->
             {
                 new Thread(new Runnable() {
                     @Override
                     public void run() {

                         while (true) {
                             try {
                                 sleep(1000);
                             } catch (InterruptedException e) {
                                 e.printStackTrace();
                             }
                             App.GAMESCORE += 10;
                             label.setText("Score: " + GAMESCORE);
                         }
                     }

                 }).start();
                 System.out.println("Button 1 pressed");
                 logger.append("Game Started! SHOOT EM ROBS\n\n\n");

                 //Thread to create robot objects and get robots to a queue
                 Thread roTh = new Thread(robocop);
                 roTh.start();
                 startTime = System.currentTimeMillis();



             });





            
            JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, arena, logger);

            Container contentPane = window.getContentPane();
            contentPane.setLayout(new BorderLayout());
            contentPane.add(toolbar, BorderLayout.NORTH);
            contentPane.add(splitPane, BorderLayout.CENTER);
            
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setPreferredSize(new Dimension(800, 800));
            window.pack();
            window.setVisible(true);
            
            splitPane.setDividerLocation(0.75);
        });
    }    
}
