package com.labTask.broadcastcounter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MyReceiver extends BroadcastReceiver{
    private static final String TAG = "MyReceiver";
    public static int count = 0;
    public static String message;



    public void onReceive(Context context, Intent intent) {

        String actionString = intent.getAction();
        Toast.makeText(context, actionString, Toast.LENGTH_LONG).show();
        if (actionString == "android.intent.action.ACTION_POWER_CONNECTED"){
            count = count + 1;
            message = "TOTAL NUMBER OF TIMES POWER CONNECTED: " + count  + " times";
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

        PendingResult pendingResult = goAsync();
        Log.d(TAG, "onreceive: BOOT ACTION");
        new Task(pendingResult, intent).execute();

    }

    private static class Task extends AsyncTask<Void, Void, Void> {

        PendingResult pendingResult;
        Intent intent;

        public Task(PendingResult pendingResult, Intent intent) {
            this.pendingResult = pendingResult;
            this.intent = intent;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Log.d(TAG, "doInBackground: Work Started");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);
            Log.d(TAG, "onPostExecute: Work Finished");
            pendingResult.finish();
        }
    }
}
