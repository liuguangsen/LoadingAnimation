package com.liugs.loadinganimation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void goDemoActivity(Class<?> cls){
        startActivity(new Intent(this,cls));
    }

    public void test1(View view) {
        goDemoActivity(MainActivity.class);
    }

    public void test2(View view) {
        goDemoActivity(CrossFadeActivity.class);
    }

    public void test3(View view) {
        goDemoActivity(CardFlipAnimationActivity.class);
    }

    public void test4(View view) {
        goDemoActivity(RevealActivity.class);
    }
}
