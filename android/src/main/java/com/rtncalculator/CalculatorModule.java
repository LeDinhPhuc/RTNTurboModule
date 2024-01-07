package com.rtncalculator;

import androidx.annotation.NonNull;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMethod;
import android.util.Log;
import androidx.annotation.Nullable;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.xproject.pilot.MultiCDN;
import com.xproject.pilot.PilotClient;
public class CalculatorModule extends NativeCalculatorSpec implements DownloadTracker.TrackerListener {

    public static String NAME = "RTNCalculator";
    private static ReactApplicationContext _reactContext;

    CalculatorModule(ReactApplicationContext context) {
        super(context);
        _reactContext = context;
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

    @Override
    public void add(double a, double b, Promise promise) {
        promise.resolve(a + b);
    }

    @Override
    public void initPilot(String token){
        PilotClient.initialize(_reactContext, token);
    }

    @Override
    @ReactMethod(isBlockingSynchronousMethod = true)
    public void generateUrl(String originalUrl, String propertyId, Promise promise){
        String pilotUrl = PilotClient.generateUrl(originalUrl, propertyId);
        promise.resolve(pilotUrl);
    }

    @ReactMethod
    public void getLog(Promise promise){
        Log.e("Sigma Player", "OnTracker: Call from thread");
        WritableMap params = Arguments.createMap();
//        String log = MultiCDN.getTracker();
//        params.putString("log", log);
//        Log.e("Sigma Player", "OnTracker: " + log);
        promise.resolve(params);
    }

    public void onTracker(String log) {
        Log.e("Sigma Player", "OnTracker: Call from thread");
        _reactContext.runOnJSQueueThread(new Runnable() {
            @Override
            public void run() {
                WritableMap params = Arguments.createMap();
                params.putString("log", log);
                Log.e("Sigma Player", "OnTracker: " + log);
                sendEvent(_reactContext, "downloadTracker", params);
            }
        });
    }

    private void sendEvent(ReactContext context,
                           String eventName,
                           @Nullable WritableMap params) {
        context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}
