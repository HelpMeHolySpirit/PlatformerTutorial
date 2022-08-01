package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.JPanel;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;

    private float xDelta = 100;
    private float yDelta = 100;
    private float xDir = 1f, yDir = 1f;
    private int frames;
    private long lastCheck;
    private Color color = new Color(150, 20, 90);
    private Random random;

    public GamePanel(){
        random = new Random();
        addKeyListener(new KeyboardInputs(this));
        mouseInputs = new MouseInputs(this);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void changeXDelta(int value){
        this.xDelta += value;

    }
    public void changeYDelta(int value){
        this.yDelta += value;

    }

    public void setRectPos(int x, int y){
        this.xDelta = x;
        this.yDelta = y;
        repaint();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        updateRectangle();
        g.setColor(color);
        g.fillRect((int)xDelta, (int)yDelta, 200, 50);
        frames++;

        if(System.currentTimeMillis() - lastCheck >= 1000){
            lastCheck = System.currentTimeMillis();
            System.out.println("FPS: " + frames);
            frames = 0;
        }
    }

    private void updateRectangle() {
        xDelta += xDir;
        if(xDelta > 400 || xDelta < 0) {
            xDir *= -1;
            color = getRndColor();
        }
        yDelta += yDir;
        if(yDelta > 400 || yDelta < 0) {
            yDir *= -1;
            color = getRndColor();
        }
    }

    private Color getRndColor() {
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return new Color(r, g, b);
    }
}
