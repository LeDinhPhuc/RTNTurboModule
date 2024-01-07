package com.rtncalculator;

import android.util.Log;
public class DownloadTracker {
    interface TrackerListener {
        public void onTracker(String log);
    }
    public static DownloadTracker instance = null;
    TrackerListener listener = null;
    private Thread background;
    private boolean _stop;
    public static DownloadTracker getInstance() {
        if (instance == null) {
            instance = new DownloadTracker();
            instance.initTracker();
        }
        return instance;
    }
    
    public void initTracker(){
//        _stop = false;
//        background = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (!_stop) {
//                    String log = MultiCDN.getTracker();
//                    onLog(log);
//                }
//            }
//        });
//        background.start();
    }
    
    public void stop(){
        synchronized (this) {
            _stop = true;
        }
    }
    
    public void setListener(TrackerListener listener) {
        this.listener = listener;
    }

    private void onLog(String logs){
        Log.e("SigmaPlayer", "Download Tracker" + logs);
        synchronized (this) {
            if (listener != null) {
                listener.onTracker(logs);
            }
        }
    }
}
