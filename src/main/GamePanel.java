package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private BufferedImage image, subImage;
    private float xDelta = 100;
    private float yDelta = 100;

    private int frames;
    private long lastCheck;



    public GamePanel(){
        mouseInputs = new MouseInputs(this);
        importImage();
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));

        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void importImage() {
        InputStream is = getClass().getResourceAsStream("/player_sprites.png");
        try {
            image = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void changeXDelta(int value){
        this.xDelta += value;

    }
    public void changeYDelta(int value){
        this.yDelta += value;

    }
    public void setRectPos(int x, int y){
        this.yDelta = y;
        this.xDelta = x;
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        subImage = image.getSubimage(1 * 64, 8 * 40, 64, 40);
        g.drawImage(subImage, (int)xDelta, (int)yDelta, 128, 80, null);
    }
}
