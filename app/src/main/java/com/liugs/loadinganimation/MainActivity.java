package com.liugs.loadinganimation;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LoadingView loadingView = findViewById(R.id.loading);
        loadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingView.switchAnimatorSet();
            }
        });

        ImageView animation_drawable = findViewById(R.id.animation_drawable);
        animation_drawable.setBackgroundResource(R.drawable.animatorvectordrawable);
        final AnimatedVectorDrawable animationDrawable = (AnimatedVectorDrawable) animation_drawable.getBackground();
        animation_drawable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animationDrawable.isRunning()){
                    animationDrawable.stop();
                }else {
                    animationDrawable.start();
                }
            }
        });
    }
}
