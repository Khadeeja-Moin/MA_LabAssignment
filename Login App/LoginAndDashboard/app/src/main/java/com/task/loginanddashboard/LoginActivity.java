package com.task.loginanddashboard;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.task.loginanddashboard.PutDataintoDatabase.PutData;

public class LoginActivity extends AppCompatActivity {
    EditText login_email;
    EditText login_password;
    TextInputLayout login_email_textfield;
    TextInputLayout login_password_textfield;
    ProgressBar progressBar;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //edit text typecasting
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        dialog = new Dialog(this);
        progressBar = findViewById(R.id.progress);
        final Context context = this;
        // Text Input Layout Typecasting
        login_email_textfield = findViewById(R.id.login_email_textfield);
        login_password_textfield = findViewById(R.id.login_password_textfield);
        // login button
        Button login_activity_login_btn = findViewById(R.id.loginactivity_login_btn);
        login_activity_login_btn.setOnClickListener(new View.OnClickListener() {
            //class name will change to dashboard
            @Override
            public void onClick(View arg0) {
                login();
                String email,password;
                email= String.valueOf(login_email.getText());
                password= String.valueOf(login_password.getText());
                if(!email.equals("") && !password.equals("") ) {
                    progressBar.setVisibility(View.VISIBLE);
                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "email";
                            field[1] = "password";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = email;
                            data[1] = password;
                            PutData putData = new PutData("http://192.168.1.106/LoginandSignup/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if(result.equals("Login Success"))
                                    {
                                        startActivity(new Intent(getApplicationContext(), com.task.loginanddashboard.Dashboard.class));
                                        finish();
                                    }
                                    else
                                    {
                                        openDialog();
                                    }
                                }
                            }//End Write and Read data with URL
                        }
                    });
                }
                else
                {
                }
            } // end of onClick method
        }); // end of View.OnClickListener
    } // end of OnCreate method
    private void openDialog(){
        dialog.setContentView(R.layout.dialog_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button dialog_btn_ok = dialog.findViewById(R.id.dialog_btn_ok);
        dialog_btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    // edit text field validation
    private boolean login() {
        boolean isValid = true;
        //email validation
        String login_email_input = login_email.getText().toString().trim();
        if (login_email_input.isEmpty())
        {
            login_email_textfield.setError("Please enter email address");
            isValid= true;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(login_email_input).matches())
        {
            login_email_textfield.setError("Please enter a valid email address");
            return false;
        }
        else
        {
            login_email_textfield.setErrorEnabled(false);
        }
        //password field cannot be empty
        if(login_password.getText().toString().isEmpty())
        {
            login_password_textfield.setError("Please enter the password");
            isValid= true;
        }
        else
        {
            login_password_textfield.setErrorEnabled(false);
        }
        return isValid;
    } // end of validation method

} // end of AppCompatActivity