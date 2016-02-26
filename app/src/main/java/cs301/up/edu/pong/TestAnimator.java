package cs301.up.edu.pong;

import android.graphics.*;
import android.view.MotionEvent;

import java.util.Random;


/**
 * class that animates a ball repeatedly moving diagonally on
 * simple background
 *
 * @author Steve Vegdahl
 * @author Andrew Nuxoll
 * @version February 2016
 */
public class TestAnimator implements Animator {

    // instance variables
    public static int countx = 0; // counts the number of logical clock ticks
    public static int county = 0;
    private boolean goBackwardsx = false; // whether clock is ticking backwards
    private boolean goBackwardsy = false;
    private int radius = 30;
    public static boolean start = false;
    public static int difficulty=1;

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
        return Color.rgb(180, 200, 255);
    }


    /**
     * Action to perform on clock tick
     *
     * @param g the graphics object on which to draw
     */
    public void tick(Canvas g) {
        drawBorders(g);
        drawPaddle(difficulty,g);
        int xnum;
        int ynum;
        xnum = (g.getWidth()-100)/2;
        ynum = (g.getHeight()-100)/2;

        if (!start)
        {
            drawGameText(g);
            randomizeDirs();
        }
        if (start) {


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

            xnum += (countx * 30);
            ynum += (county * 30);



            if (xnum+radius > (g.getWidth()-50) || (xnum < 50+radius)) {
                goBackwardsx = !goBackwardsx;
            }

            if (ynum < 50+radius)
            {
                goBackwardsy=!goBackwardsy;
            }

            if (ynum > g.getHeight() - (radius+65)) {
                if (difficulty==1)
                {
                    if (xnum>300-radius && xnum<(g.getWidth()-(300+radius)))
                    {
                        goBackwardsy = !goBackwardsy;
                    }
                    else
                    {
                        start = false;
                    }
                }
                else
                {
                    if (xnum>500-radius && xnum<(g.getWidth()-(500+radius)))
                    {
                        goBackwardsy = !goBackwardsy;
                    }
                    else
                    {
                        start = false;
                    }
                }




            }



            Paint redPaint = new Paint();
            redPaint.setColor(Color.RED);
            g.drawCircle(xnum, ynum, radius, redPaint);


        }
    }

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

    public void drawBorders(Canvas g) {

        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.rgb(252, 236, 160));
        g.drawRect(0, 0, 50, g.getHeight(), borderPaint);
        g.drawRect(0, 0, g.getWidth(), 50, borderPaint);
        g.drawRect(g.getWidth() - 50, 0, g.getWidth(), g.getHeight(), borderPaint);
    }

    public int genRandom() {
        Random gen = new Random();
        int random = gen.nextInt(2);

        return random;
    }

    public void randomizeDirs()
    {
        int num = genRandom();
        if (num==0)
        {
            goBackwardsx=!goBackwardsx;
            goBackwardsy=!goBackwardsy;
        }
    }

    public void drawPaddle(int diff, Canvas g)
    {
        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.rgb(252, 236, 160));
        if (diff==1)
        {
            g.drawRect(300,g.getHeight()-55,g.getWidth()-300,g.getHeight()-10,borderPaint);
        }
        else
        {
            g.drawRect(500,g.getHeight()-55,g.getWidth()-500,g.getHeight()-10,borderPaint);
        }
    }

    public void drawGameText(Canvas g)
    {
        Paint wordPaint = new Paint();
        wordPaint.setColor(Color.RED);
        wordPaint.setTextSize(50f);

        g.drawText("PRESS START TO PLAY!",400f,g.getHeight()/2,wordPaint);
    }


}//class TextAnimator
