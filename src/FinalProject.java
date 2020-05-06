import processing.core.PApplet;
import processing.core.PImage;


public class FinalProject extends PApplet {
    PImage oof;
    PImage background;
    PImage obstacle;
    PImage yeet;
    PImage over;

    int oofX = 150;
    int oofY = 675;

    int yeetX = 1500;
    int yeetY = 675;

    int bgX = 0;
    int bgY = 0;

    int obstacleX;
    int obstacleY = 865;

    float speed = 25;

    int jumpHeight;

    int startTime;
    int timer;

    float speedIncrease = .8f;
    float rateOfDifficulty = 5;
    int difficultyTime = 0;

    int score = 0;
    int highScore = 0;


    enum GameState {
        GAMEOVER, RUNNING
    }
    static GameState currentState;
    public static void main(String[] args) {
        PApplet.main("FinalProject");
    }

    public void setup() {

        oof = loadImage ("Images/player.png");
        background = loadImage("Images/Sky2.png");
        obstacle = loadImage("Images/woodbox.png");
        obstacle.resize(65,0);
        startTime = millis();
        currentState = GameState.RUNNING;
        yeet = loadImage("Images/woodbox.png");
        yeet.resize(65,0);
        over = loadImage("Images/over.png");
        over.resize(800,800);

    }

    public void settings() {
        size(1900, 900);
    }

    public void draw() {
        switch(currentState){
            case RUNNING:
                timer = (millis() - startTime) / 1000;
                drawBackground();
                drawoof();
                createObstacles();
                createYeet();
                drawScore();
                increaseDifficulty();
                break;
            case GAMEOVER:
                image(over, 500, 500);
                break;
        }

    }
    public void drawoof(){
        imageMode(CENTER);
        if (oofY <= 775){
            jumpHeight += 1;
            oofY += jumpHeight;
        }
        image(oof, oofX, oofY);
    }

    public void drawBackground () {
        imageMode(CORNER);
        image(background, bgX, bgY);
        image(background, bgX + background.width, bgY);
        bgX -= speed;
        if (bgX <= (background.width * -1)) {
            bgX = 0;
        }
    }

    public void keyPressed() {
        if (key == 'w') {
            if (currentState == GameState.RUNNING) {
                if (oofY >= 675) {
                    jumpHeight = -15;
                    oofY += jumpHeight;
                }
            }
            if (currentState == GameState.GAMEOVER) {
                obstacleX = 0;
                bgX = 0;
                startTime = millis();
                speed = 5;
                currentState = GameState.RUNNING;
            }
        }
       if (key == 's') {
           if (currentState == GameState.RUNNING) {
               oofY = 850;
           }
           if (currentState == GameState.GAMEOVER) {
               obstacleX = 0;
               bgX = 0;
               startTime = millis();
               speed = 5;
               currentState = GameState.RUNNING;
           }
        }
    }

    public void keyReleased(){
        if (key == 's') {
            oofY = 675;
        }
    }

    public void mousePressed() {
        if (currentState == GameState.RUNNING) {
             if (oofY >= 675) {
                jumpHeight = -15;
                oofY += jumpHeight;
            }
        }
        if (currentState == GameState.GAMEOVER) {
            obstacleX = 0;
            bgX = 0;
            startTime = millis();
            speed = 5;
            currentState = GameState.RUNNING;
        }
    }
    public void createObstacles()   {
        imageMode(CENTER);
        image(obstacle, obstacleX, obstacleY);
        obstacleX -= speed;
        if(obstacleX < 0) {
            obstacleX = width;
        }
        if ((abs(oofX - obstacleX) < 50) && abs(oofY - obstacleY) < 100)  {
            score = timer;
            if(score > highScore)  {
                highScore = score;
            }
            currentState = GameState.GAMEOVER;
        }
    }

    public void createYeet(){
        imageMode(CENTER);
        image(yeet, yeetX, yeetY);
        yeetX -= speed /2;
        if(yeetX < 0) {
            yeetX = width;
        }
        if ((abs(oofX - yeetX) < 50) && abs(oofY - yeetY) < 150)  {
            score = timer;
            if(score > highScore)  {
                highScore = score;
            }
            currentState = GameState.GAMEOVER;
        }

    }
    public void drawScore()   {
        fill(255,255, 255);
        textAlign(CENTER);
        text("Score: " + timer, width - 70, 30);
    }

    public void increaseDifficulty() {
        if (timer % rateOfDifficulty == 0 && difficultyTime != timer) {
            speed += speedIncrease;
            difficultyTime = timer;
        }

    }
}