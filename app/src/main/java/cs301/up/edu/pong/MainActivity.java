package cs301.up.edu.pong;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener, View.OnTouchListener
,GestureDetector.OnGestureListener{

    //Instance variables
    protected AnimationSurface mySurface;
    protected GestureDetector gd =null;
    protected TestAnimator myAnimator;
    protected static TextView scoreCount;
    /**
     * EXTERNAL CITATION
     * PROBLEM: Could not get method to run on UI thread
     * RESOURCE: James Schimdt
     * SOLUTION: Make some things static and I know I know it's bad but it's the only way to make
     * it work
     */
    protected static TextView livesCount;
    protected static Button start;
    protected MediaPlayer backgroundMusic;
    protected static MediaPlayer wallStrike;
    protected static MediaPlayer missedBall;
    protected static Vibrator v;

    /**
     * Inflate the app and start it off
     * @param savedInstanceState I don't really know what this is
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pong_main);

        myAnimator = new TestAnimator();

        // Connect the animation surface with the animator
        mySurface = (AnimationSurface) this
                .findViewById(R.id.animationSurface);
        mySurface.setAnimator(myAnimator);
        mySurface.setOnTouchListener(this);

        //Start button reference
        start = (Button)findViewById(R.id.startButton);
        start.setOnClickListener(this);
        start.setClickable(true);
        start.setVisibility(View.VISIBLE);

        //Radio Groud Reference
        RadioGroup grouping = (RadioGroup)findViewById(R.id.group);
        grouping.setOnCheckedChangeListener(this);

        //Set the default to easy
        grouping.check(R.id.easyButton);

        //Register the gesture detector to listen to gestures
        gd = new GestureDetector(this, this);

        //Define Text Views
        scoreCount = (TextView)findViewById(R.id.scoreView);
        livesCount = (TextView)findViewById(R.id.livesView);

        //Lock orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Define and run background music
        backgroundMusic = MediaPlayer.create(this,R.raw.backgroundjams);
        backgroundMusic.setLooping(true);
        backgroundMusic.start();

        //Define sound effects
        wallStrike = MediaPlayer.create(this, R.raw.wallbloop);
        missedBall = MediaPlayer.create(this,R.raw.deathsound);

        //Register the vibrator
        v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
    }

    /**
     * Change the new Total
     * @param score the new total
     */
    public void setTotal(int score)
    {
        scoreCount.setText(String.valueOf(score));
    }

    /**
     * Change the current lives
     * @param lives the new number lives
     */
    public void changeLives(int lives)
    {
        //if you run out of lives take away the restart button
        if (lives<0)
        {
            start.setClickable(false);
            start.setVisibility(View.INVISIBLE);
        }
        //Decrement Lines
        else {
            livesCount.setText(String.valueOf(lives));
        }

    }
    /**
     * Handles click events
     * @param v the view that was clicked
     */
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.startButton)
        {
            myAnimator.countx=0;
            myAnimator.county=0;
            myAnimator.start=true;
        }
    }

    /**
     * Handle radio group changes (change difficulty)
     * @param group RadioGroup that was changed.
     * @param checkedId THe ID of the button that was checked.
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId==R.id.easyButton)
        {
            myAnimator.difficulty=1;
        }
        else
        {
            myAnimator.difficulty=2;
        }
    }

    /**
     * Through method to enable touch Events
     * @param v the view that was touched
     * @param event the MotionEvent info
     * @return always true.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    /**
     * NOT USED
     * @param e
     * @return
     */
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    /**
     * NOT USED
     * @param e
     */
    @Override
    public void onShowPress(MotionEvent e) {

    }

    /**
     * NOT USED
     * @param e
     * @return
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    /**
     * Handles drag events such as moving the paddle across the screen
     * @param e1 initial event
     * @param e2 final event
     * @param distanceX the distance changed in the x
     * @param distanceY the distance changed in the y
     * @return true if the method was handled.
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        float startX = e1.getX();
        float endX = e2.getX();
        int deltaX = (int)(endX-startX);

        //pass the change to the animator
        myAnimator.paddleChange = deltaX;
        return true;
    }

    /**
     * NOT USED
     * @param e
     */
    @Override
    public void onLongPress(MotionEvent e) {

    }

    /**
     * NOT USED
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    /**
     * What to do when the app is paused
     */
    public void onPause()
    {
        super.onPause();
        //pause background music
        backgroundMusic.pause();
    }

    /**
     * What to do on app resume
     */
    public void onResume()
    {
        super.onResume();
        //resume background music
        backgroundMusic.start();
    }
}
