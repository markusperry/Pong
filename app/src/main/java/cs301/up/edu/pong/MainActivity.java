package cs301.up.edu.pong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    AnimationSurface mySurface;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pong_main);

        // Connect the animation surface with the animator
        mySurface = (AnimationSurface) this
                .findViewById(R.id.animationSurface);
        mySurface.setAnimator(new TestAnimator());


        //Start button reference
        Button start = (Button)findViewById(R.id.startButton);
        start.setOnClickListener(this);

        //Radio Groud Reference
        RadioGroup grouping = (RadioGroup)findViewById(R.id.group);
        grouping.setOnCheckedChangeListener(this);

        //Set the default to easy
        grouping.check(R.id.easyButton);



    }

    /**
     * Handles click events
     * @param v the view that was clicked
     */
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.startButton)
        {
            TestAnimator.countx=0;
            TestAnimator.county=0;
            TestAnimator.start=true;
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
            TestAnimator.difficulty=1;
        }
        else
        {
            TestAnimator.difficulty=2;
        }
    }
}
