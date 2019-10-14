package com.aila.ailahackathon;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.aila.ailahackathon.inspector.Inspector;
import com.aila.ailahackathon.schedule.Schedule;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class Aila extends Service {
    View floatView,mainflo;
    WindowManager windowManager;
    ImageView schedule,inspector,aila,parent;
    WindowManager.LayoutParams params;
    int visibilityMenu =  0;

    public Aila() {
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO do something useful
        //smsHandler.sendEmptyMessageDelayed(DISPLAY_DATA, 1000);
        return Service.START_NOT_STICKY;
    }

    public void onCreate(){
        super.onCreate();
        //View yang mau ditampilkan
        floatView= LayoutInflater.from(this).inflate(R.layout.fragment_aila,null);
        //konfigurasi untuk ditampilkan di window manager
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }
        params = new WindowManager.LayoutParams(
                // Shrink the window to wrap the content rather than filling the screen
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                // Display it on top of other application windows, but only for the current user
//                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                // Don't let it grab the input focus
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                // Make the underlying application window visible through any transparent parts
                PixelFormat.TRANSLUCENT);

// Define the position of the window within the screen
        params.gravity = Gravity.TOP | Gravity.RIGHT;
        params.x = 0;
        params.y = 120;

        windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        windowManager.addView(floatView, params);

//        floatView.findViewById(R.id.aeri).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"Baka Dana",Toast.LENGTH_LONG).show();
//            }
//        });


        aila=floatView.findViewById(R.id.aila);
        mainflo=floatView.findViewById(R.id.aila);
        schedule=floatView.findViewById(R.id.schedule);
        inspector=floatView.findViewById(R.id.inspector);
        parent=floatView.findViewById(R.id.parent);


        floatView.findViewById(R.id.aila).setOnTouchListener(new View.OnTouchListener() {
            int X_Axis, Y_Axis;
            float TouchX, TouchY;
            long lastTouchDown;
            private int CLICK_ACTION_THRESHHOLD = 200;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastTouchDown = System.currentTimeMillis();
                        X_Axis = params.x;
                        Y_Axis = params.y;
                        TouchX = event.getRawX();
                        TouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        if (System.currentTimeMillis() - lastTouchDown < CLICK_ACTION_THRESHHOLD) {
                            Log.w("App",  "X=" + event.getRawX()
                                    + " Y=" + event.getRawY());
                            if (schedule.getVisibility() == View.GONE){
//                                collapsedView.setVisibility(View.GONE);
//                                menuView.setVisibility(View.VISIBLE);
                                if (visibilityMenu == 0) {
                                    schedule.setVisibility(View.VISIBLE);
                                    inspector.setVisibility(View.VISIBLE);
                                    parent.setVisibility(View.VISIBLE);
                                }
                                visibilityMenu++;
                            } else {
                                schedule.setVisibility(View.GONE);
                                inspector.setVisibility(View.GONE);
                                parent.setVisibility(View.GONE);
                                visibilityMenu = 0;
                            }
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = X_Axis + (int) (TouchX - event.getRawX());
                        params.y = Y_Axis + (int) (event.getRawY() - TouchY);
                        windowManager.updateViewLayout(floatView, params);
                        return true;
                }
                return false;
            }
        });
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getBaseContext(), Schedule.class);
                startActivity(in);
            }
        });
        inspector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getBaseContext(), Inspector.class);
                startActivity(in);
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatView != null) windowManager.removeView(floatView);
    }
}
