package com.lionel.viewpagerinfinityp;

import android.app.Activity;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class TimerHelper {

    private final ITimerHelperListener timerHelperListener;
    private final Activity activity;
    private Timer timer;

    public interface ITimerHelperListener {
        void onTimeIsUp();
    }

    public TimerHelper(Activity activity, ITimerHelperListener timerHelperListener) {
        this.activity = activity;
        this.timerHelperListener = timerHelperListener;
    }

    private void initTimer() {
        if (timer == null) {
            timer = new Timer();
        }
    }

    public void startTimerTask(long startAfter, long interval) {
        initTimer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (timerHelperListener != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timerHelperListener.onTimeIsUp();
                        }
                    });
                }
            }
        }, startAfter, interval);
    }

    public void cancelTimer(){
        if(timer!=null) {
            Log.d("<>", "IM DEAD");
            timer.cancel();
            timer = null;
        }
    }
}
