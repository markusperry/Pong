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
    private int radius = 60;
    public static boolean start = false;

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
        int xnum;
        int ynum;
        xnum = (g.getWidth()-100)/2;
        ynum = (g.getHeight()-100)/2;

        if (!start)
        {
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

            xnum += (countx * 20);
            ynum += (county * 20);



            if (xnum+radius > (g.getWidth()-50) || (xnum < 50+radius)) {
                goBackwardsx = !goBackwardsx;
            }

            if (ynum < 50+radius)
            {
                goBackwardsy=!goBackwardsy;
            }

            if (ynum > g.getHeight() - radius) {
                start = false;
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


}//class TextAnimator
