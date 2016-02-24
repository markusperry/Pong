package cs301.up.edu.pong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    AnimationSurface mySurface;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pong_main);

        // Connect the animation surface with the animator
        mySurface = (AnimationSurface) this
                .findViewById(R.id.animationSurface);
        mySurface.setAnimator(new TestAnimator());


        Button start = (Button)findViewById(R.id.startButton);
        start.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.startButton)
        {
            TestAnimator.countx=0;
            TestAnimator.county=0;
            TestAnimator.start=true;
        }
    }
}
