package com.liugs.loadinganimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 裁剪动画
 */
public class RevealActivity extends AppCompatActivity {

    private ImageView myView;
    private int width;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal);

        // previously visible view
        myView = findViewById(R.id.my_view);
    }

    public void reveal(View view) {
        // Check if the runtime version is at least Lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && myView.getVisibility() == View.VISIBLE) {
            // get the center for the clipping circle
            width = myView.getWidth();
            int cx = width / 2;
            height = myView.getHeight();
            int cy = height / 2;

            // get the initial radius for the clipping circle
            float initialRadius = (float) Math.hypot(cx, cy);

            // create the animation (the final radius is zero)
            Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx , cy , initialRadius, 0f);

            // make the view invisible when the animation is done
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.GONE);
                }
            });

            // start the animation
            anim.start();
        } else {
            // set the view to visible without a circular reveal animation below Lollipop
            myView.setVisibility(View.VISIBLE);
        }
    }
}
