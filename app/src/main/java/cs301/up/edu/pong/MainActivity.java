package cs301.up.edu.pong;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaActionSound;
import android.media.MediaPlayer;
import android.media.SoundPool;
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

        gd = new GestureDetector(this, this);

        scoreCount = (TextView)findViewById(R.id.scoreView);
        livesCount = (TextView)findViewById(R.id.livesView);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        backgroundMusic = MediaPlayer.create(this,R.raw.backgroundjams);
        backgroundMusic.setLooping(true);
        backgroundMusic.start();

        wallStrike = MediaPlayer.create(this, R.raw.wallbloop);
        missedBall = MediaPlayer.create(this,R.raw.deathsound);

        v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void setTotal(int score)
    {
        scoreCount.setText(String.valueOf(score));
    }

    public void changeLives(int lives)
    {
        if (lives<0)
        {
            start.setClickable(false);
            start.setVisibility(View.INVISIBLE);
        }
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        boolean drag = gd.onTouchEvent(event);
        return true;
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        float startX = e1.getX();
        float endX = e2.getX();
        int deltaX = (int)(endX-startX);

        myAnimator.paddleChange = deltaX;
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    public void onPause()
    {
        super.onPause();
        backgroundMusic.pause();
    }

    public void onResume()
    {
        super.onResume();
        backgroundMusic.start();
    }
}
