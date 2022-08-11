package main;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow {

    private final JFrame jframe;
    public GameWindow(GamePanel gamePanel){
        jframe = new JFrame();


        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jframe.add(gamePanel);

        jframe.setResizable(false);



        jframe.setVisible(true);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }
        });
    }
}
