package entities;

import main.Game;
import utilz.LoadSave;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.stream.Stream;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

public class Player extends Entity{
    private BufferedImage[][] animations;

    private int aniTick, aniIndex, aniSpeed = 15;
    private int playerAction = IDLE;
    private  boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    private boolean attacking = false;
    private boolean moving = false;
    private float playerSpeed =1.5f;
    private int[][] lvlData;
    private float airSpeed = 2f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterColision = 0.5f * Game.SCALE;
    private boolean inAir = false;
    private boolean jump;

    public Player(float x, float y, int width, int height){
        super(x, y, width, height);
        loadAnimations();
        initHitBox(x, y, 20 * Game.SCALE, 27 * Game.SCALE);
    }

    public void update(){
        updatePos();
        updateAnimationTick();
        setAnimations();
    }

    public void render(Graphics g){
        g.drawImage(animations[playerAction][aniIndex], (int)(hitBox.x - xDrawOffset),(int)(hitBox.y - yDrawOffset), width, height, null);
        //drawHitBox(g);
    }

    private void loadAnimations() {
            BufferedImage image = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

            animations = new BufferedImage[9][6];
            Stream.of(animations).forEach(System.out::println);
            for(int j = 0; j < animations.length; j++){
                for(int i = 0; i < animations[j].length; i++){
                    animations[j][i] = image.getSubimage(i * 64, j * 40, 64, 40);
                }
            }
    }

    public void loadLvlData(int[][] lvlData){

        this.lvlData = lvlData;
        if(!IsEntityOnFloor(hitBox, lvlData))
            inAir = true;
    }

    public void resetDirBooleans(){
        left = false;
        right = false;
        up = false;
        down = false;
    }


    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;
            }
        }
    }

    private void setAnimations(){

        int startAnimation = playerAction;
        if (moving) {
            playerAction = RUNNING;
        }else {
            playerAction = IDLE;
        }

        if(inAir){
            if(airSpeed<0){
                playerAction = JUMP;
            }else {
                playerAction = FALLING;
            }
        }

        if(attacking){
            playerAction = ATTACK_1;
        }

        if(startAnimation != playerAction){
            resetAnimationTick();
        }
    }

    private void resetAnimationTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updatePos(){
        moving = false;
        if(jump){
            jump();
        }
        if(!left && !right && !inAir)
            return;


        float xSpeed = 0;
        if(left)
            xSpeed -= playerSpeed;
        if(right)
            xSpeed += playerSpeed;
        if(!inAir){
            if(!IsEntityOnFloor(hitBox, lvlData)){
                inAir = true;
            }
        }

        if(inAir){
            if(CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlData)){
                hitBox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            }else{
                hitBox.y = GetEntityYPosUnderRooforAboveFloor(hitBox, airSpeed);
                if(airSpeed > 0){
                    resetInAir();
                }else {
                    airSpeed = fallSpeedAfterColision;
                }
                updateXPos(xSpeed);
            }
        }else{
            updateXPos(xSpeed);
        }
        moving = true;
    }

    private void jump() {
        if(inAir){
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;

    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    public void setJump(boolean jump){
        this.jump = jump;
    }

    private void updateXPos(float xSpeed){
        if(CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData)){
            hitBox.x += xSpeed;
        }else{
            hitBox.x = GetEntityXPosNextToWall(hitBox, xSpeed);
        }
    }



    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
}
