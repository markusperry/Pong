package cs301.up.edu.pong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import java.util.Random;

/**
 * class that animates a ball repeatedly moving diagonally on
 * simple background
 *
 * @author Steve Vegdahl
 * @author Andrew Nuxoll
 * @version February 2016
 *
 * @author Markus Perry
 * @version Februar 2016 v.2
 * edited by Markus to fit description.
 */
public class TestAnimator extends MainActivity implements Animator {

    // instance variables
    public int countx = 0; // counts the number of logical clock ticks
    public int county = 0;
    private boolean goBackwardsx = false; // whether clock is ticking backwards
    private boolean goBackwardsy = false;
    private int radius = 30;
    public boolean start = false; //whether to start game or not
    public int difficulty=1; //paddle difficulty
    protected int paddleChange = 0;
    private int easyEdge = 300;
    private int hardEdge = 500;
    private int leftEdge;
    private int RighEdge;
    private int speedX=15;
    private int speedY=15;
    public int totalScore=0;
    protected int life = 5;

    /**
     * Interval between animation frames: .03 seconds (i.e., about 33 times
     * per second).
     *
     * @return the time interval between frames, in milliseconds.
     */
    public int interval() {
        return 30;
    }

    /**
     * The background color: a light blue.
     *
     * @return the background color onto which we will draw the image.
     */
    public int backgroundColor() {
        // create/return the background color
        return Color.rgb(0, 0, 0);
    }


    /**
     * Action to perform on clock tick
     *
     * @param g the graphics object on which to draw
     */
    public void tick(Canvas g) {
        //draw the borders and paddle
        drawBorders(g);
        drawPaddle(difficulty,g, paddleChange);

        //X and Y coordinate of the ball
        int xnum;
        int ynum;

        //initial position of ball
        xnum = (g.getWidth()-100)/2;
        ynum = (g.getHeight()-100)/2;

        //display startup and randomize directions
        if (!start)
        {
            drawGameText(g);
            randomizeDirs();
            changeSpeed();
        }
        //start the game
        if (start) {
            //determine direction of animation
            if (goBackwardsx) {
                countx--;
            }
            if (goBackwardsy) {
                county--;
            }
            if (!goBackwardsx) {
                countx++;
            }
            if (!goBackwardsy) {
                county++;
            }

            //animate ball movement
            xnum += (countx * speedX);
            ynum += (county * speedY);

            //wall bouncing
            if (xnum+radius > (g.getWidth()-50) || (xnum < 50+radius))
            {
                goBackwardsx = !goBackwardsx;
                wallStrike.start();
            }
            if (ynum < 50+radius)
            {
                goBackwardsy = !goBackwardsy;
                wallStrike.start();
            }

            //paddle bouncing based on difficulty
            if (ynum > g.getHeight() - (radius+65)) {
                //easy difficulty
                if (difficulty==1)
                {
                    //inside paddle
                    if (xnum>leftEdge && xnum<RighEdge) {
                        goBackwardsy = !goBackwardsy;
                        totalScore++;
                        wallStrike.start(); //play sound
                    }
                    //missed paddle
                    else
                    {
                        start = false;
                        totalScore=0;
                        life--;
                        missedBall.start();//play sound
                        v.vibrate(500);//vibrate
                    }
                }
                //hand difficulty
                else
                {
                    //inside paddle
                    if (xnum>leftEdge && xnum<RighEdge)
                    {
                        goBackwardsy = !goBackwardsy;
                        totalScore++;
                        wallStrike.start();
                    }
                    //missed paddle
                    else
                    {
                        start = false;
                        totalScore=0;
                        life--;
                        missedBall.start();
                        v.vibrate(500);
                    }
                }
            }
            //draw ball
            Paint redPaint = new Paint();
            redPaint.setColor(Color.RED);
            g.drawCircle(xnum, ynum, radius, redPaint);
        }
        /**
         * EXTERNAL CITATION
         * PROBLEM: trying to change a text view from animator thread
         * RESOURCE: Michael Waitt
         * SOLUTION: added a runOnUiThread method to run it on another thread.
         */
        //change the current lives and ball count if it changed.
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setTotal(totalScore);
                changeLives(life);
            }
        });
    }//tick

    /**
     * Tells that we never pause.
     *
     * @return indication of whether to pause
     */
    public boolean doPause() {
        return false;
    }

    /**
     * Tells that we never stop the animation.
     *
     * @return indication of whether to quit.
     */
    public boolean doQuit() {
        return false;
    }

    /**
     * reverse the ball's direction when the screen is tapped
     */
    public void onTouch(MotionEvent event) {
        return;
    }

    /**
     * draw the borders of the game
     * @param g the canvas to draw on
     */
    public void drawBorders(Canvas g) {

        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.rgb(255, 255, 255));
        g.drawRect(0, 0, 50, g.getHeight()-65, borderPaint);
        g.drawRect(0, 0, g.getWidth(), 50, borderPaint);
        g.drawRect(g.getWidth() - 50, 0, g.getWidth(), g.getHeight()-65, borderPaint);
    }

    /**
     * generate a random number between 0 and 1
     * @return a random number between 0 and 1
     */
    public int genRandom() {
        Random gen = new Random();
        int random = gen.nextInt(2);

        return random;
    }

    /**
     * Randomize the directions of the ball movement
     */
    public void randomizeDirs()
    {
        int num = genRandom();
        if (num==0)
        {
            goBackwardsx=!goBackwardsx;
            goBackwardsy=!goBackwardsy;
        }
    }

    /**
     * Draw paddle based on difficulty
     * @param diff the difficulty
     * @param g the canvas to draw on
     */
    public void drawPaddle(int diff, Canvas g, int startX)
    {
        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.rgb(255, 255, 255));

        //easy, draw big paddle
        if (diff==1)
        {
            int length = g.getWidth()-800;
            g.drawRect(easyEdge+startX,g.getHeight()-55,easyEdge+startX+length,g.getHeight()-10,borderPaint);
            leftEdge = easyEdge+startX;
            RighEdge = easyEdge+startX+length;
        }

        //hard, draw small paddle
        else
        {
            int length = g.getWidth()-1000;
            g.drawRect(hardEdge+startX,g.getHeight()-55,hardEdge+startX+length,g.getHeight()-10,borderPaint);

            leftEdge = hardEdge+startX;
            RighEdge = hardEdge+startX+length;
        }
    }

    /**
     * Draw the opening game text
     * @param g the canvas to draw on
     */
    public void drawGameText(Canvas g)
    {
        Paint wordPaint = new Paint();
        wordPaint.setColor(Color.RED);
        wordPaint.setTextSize(50f);
        /**
         * EXTERNAL CITATION
         * DATE: Feb 26, 2016
         * PROBLEM: Didn't know how to draw text on surface view
         * RESOURCE: http://developer.android.com/reference/android/
         *      graphics/Canvas.html#drawText(java.lang.String, float, float, android.graphics.Paint)
         * SOLUTION: used drawText method.
         */
        g.drawText("PRESS START TO PLAY!",400f,g.getHeight()/2,wordPaint);
    }

    /**
     * Change the speed of the game ball to a random X and Y velocity.
     */
    private void changeSpeed() {
        Random gen = new Random();

        speedX= gen.nextInt(30)+15;
        speedY = gen.nextInt(30)+15;
    }
}//class TextAnimator
