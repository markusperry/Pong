package cs301.up.edu.pong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, View.OnTouchListener
,GestureDetector.OnGestureListener{

    AnimationSurface mySurface;
    GestureDetector gd =null;
    TestAnimator myAnimator;
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
        Button start = (Button)findViewById(R.id.startButton);
        start.setOnClickListener(this);

        //Radio Groud Reference
        RadioGroup grouping = (RadioGroup)findViewById(R.id.group);
        grouping.setOnCheckedChangeListener(this);

        //Set the default to easy
        grouping.check(R.id.easyButton);

        gd = new GestureDetector(this, this);



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
        Log.i("Event","Enter onScroll");

        float startX = e1.getX();
        float endX = e2.getX();
        int deltaX = (int)(endX-startX);

        Log.i("Drag Distance",String.valueOf(deltaX));

        myAnimator.paddleEdge = deltaX;
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
