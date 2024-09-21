import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;


public class typeSpeedProgram {
    private static JTextArea text;
    private static JLabel timeLabel,wpmLabel;
    private static JButton sB;
    private static long startTime;
    private static boolean testrunning = false;
    public static void main(String[] args) {
        JFrame frame = new JFrame("Type speed checker");
        frame.setSize(600,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        
        text = new JTextArea("Write text here");
        text.setBounds(50,50,500,350);
        text.setEditable(false);
        frame.add(text);
        sB = new JButton("Start test");
        sB.setBounds(50, 420, 100, 30);
        frame.add(sB);
        timeLabel = new JLabel("Time : 0s");
        timeLabel.setBounds(200, 420, 100, 30);
        frame.add(timeLabel);
        wpmLabel = new JLabel("WPM : 0");
        wpmLabel.setBounds(300, 420, 100, 30);
        frame.add(wpmLabel);
        frame.setVisible(true);
        sB.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                startTest();
            }
        });
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e)
            {
                if(!testrunning) return;
            }
        });
    }
    public static void startTest()
    {
        testrunning = true;
        startTime = System.currentTimeMillis();
        text.setEditable(true);
        text.setText("");
        wpmLabel.setText("WPM: 0");
        sB.setEnabled(false);
        
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int seconds = 0;
            @Override
            public void run()
            {
                if(!testrunning)
                {
                    timer.cancel();
                }
                seconds++;
                timeLabel.setText("Time:"+seconds+"s");
                if(seconds>=60)
                {
                    endTest();
                    timer.cancel();
                }
            }
        };        
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }
    private static void endTest()
    {
        testrunning = false;
        long endTime = System.currentTimeMillis();
        long elapsedTime = (endTime - startTime) / 1000;
        String typedText = text.getText();
        int wordcount = typedText.split("\\s+").length;
        int wpm = (int) ((wordcount * 60)/elapsedTime);
        wpmLabel.setText("WPM: "+wpm);
        text.setEditable(false);
        sB.setEnabled(true);
    }
}
