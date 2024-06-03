package com.example.methodechannelcreaate;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import io.flutter.embedding.android.FlutterActivity;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.List;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;
public class MainActivity extends FlutterActivity {
    private static final String CHANNEL_NAME = "com.github.Arifuljava:GrozziieBlutoothSDk:v1.0.1";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL_NAME).setMethodCallHandler(
                (call, result) -> {
                     if(call.method.equals("attendencemachineAndroidSDK"))
                    {
                        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ccf);
                        MyJavaClass.sayHello(bitmap,getApplicationContext(), new MyJavaClass.SuccessCallback() {
                            @Override
                            public void onSuccess(List<String> processedTextList){
                                result.success(processedTextList);

                            }
                        }, new MyJavaClass.FailureCallback() {
                            @Override
                            public void onFailure(Exception e) {

                            }
                        });
                    }
                }
        );
    }

}


