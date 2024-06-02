package com.example.methodechannelcreaate;

import io.flutter.embedding.android.FlutterActivity;


import android.content.Context;
import android.content.Intent;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;




public class MainActivity extends FlutterActivity {

    private static final String CHANNEL_NAME = "com.github.Arifuljava:GrozziieBlutoothSDk:v1.0.1";



    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);

        //ActivityLauncher.registerWith(this);

        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL_NAME).setMethodCallHandler(
                (call, result) -> {
                     if(call.method.equals("sendDataToDotPrinter"))
                    {
                        String name = "AAAAA";
                        MyJavaClass.sayHello(name, getApplicationContext(), new MyJavaClass.SuccessCallback() {
                            @Override
                            public void onSuccess(List<String> processedTextList) {
                                // Handle success scenario
                                Log.d("Success", "Processed text list: " + processedTextList);
                                result.success(processedTextList);
                            }
                        }, new MyJavaClass.FailureCallback() {
                            @Override
                            public void onFailure(Exception e) {
                                // Handle failure scenario
                                Log.e("Failure", "Error occurred: " + e.getMessage());
                            }
                        });




                    }

                }
        );
    }

}

//finishActivity
