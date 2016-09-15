package com.augmentis.ayp.testthread;

import android.content.Context;

import android.content.pm.LauncherApps;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.Editable;
import android.util.Log;

import java.util.Calendar;


/**
 * Created by Theerawuth on 9/15/2016.
 */
public class BackgroundThread extends HandlerThread {

    private static final int MESSAGE_CODE = 1;
    private static final String TAG = "BackgroundThread";


    protected Handler handlerUI;
    private CallBacks callBacks;
    protected Handler handlerBG;
    int i;


    public BackgroundThread(String name, Context ctx, Handler handler) {
        super(name);
        callBacks = (CallBacks) ctx;
        this.handlerUI = handler;
    }

    interface CallBacks {
        void setText(String s);
    }

    @Override
    protected void onLooperPrepared() {
        handlerBG = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                if (msg.what == MESSAGE_CODE) {
                    Log.d(TAG,"onLooperPrepared");
                    i  = Integer.valueOf(msg.obj.toString());
                    while (true) {
                        try {
                            sleep(1000);
                            i--;
                            handlerUI.post(new Runnable() {
                                @Override
                                public void run() {
                                    callBacks.setText(String.valueOf(i));
                                }
                            });

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if(i == 0) {
                            break;
                        }

                    }

                }
            }
        };
    }


    public void sendValueInQueue(String s) {
        Log.d(TAG,"sendValueInQueue");
        Message msg = handlerBG.obtainMessage(MESSAGE_CODE, s);
        msg.sendToTarget();
    }

}
