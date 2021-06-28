package com.task.loginanddashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;

public class Splash_activity extends AppCompatActivity {

    private static int SPLASH_TIMER = 4000;
    //variable
    ImageView backgroundImage;
    Animation sideAnim, bottomAnim;
    SharedPreferences onBoardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        backgroundImage = findViewById(R.id.splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIMER);

        ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite wave = new ThreeBounce();
        progressBar.setIndeterminateDrawable(wave);

    }
}


