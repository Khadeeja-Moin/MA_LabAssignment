package com.task.loginanddashboard;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {
    Dialog dialog;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        addListenerOnButton();

        dialog = new Dialog(this);


    }

    private void addListenerOnButton() {

        final Context context = this;
        Button logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, LoginActivity.class);
                openDialog();
                startActivity(intent);
                finish();

            }

        });

    }
    private void openDialog(){
        dialog.setContentView(R.layout.logoutdialog_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}