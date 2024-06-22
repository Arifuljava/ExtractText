package com.example.methodechannelcreaate;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import io.flutter.embedding.android.FlutterActivity;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
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
                        handleSendImages(call,result);

                    }
                }
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void handleSendImages(MethodCall call, MethodChannel.Result result) {
        List<byte[]> images = call.argument("images");
        DataManager.getInstance().setImageList(images);
        List<byte[]> receivedImages = DataManager.getInstance().getImageList();
        List<Bitmap> bitmapList = new ArrayList<>();
        for (byte[] imageBytes : receivedImages) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            Log.e("data3gg3",""+bitmap);
            bitmapList.add(bitmap);
        }
        Bitmap targetbitmap = bitmapList.get(0) ;
        Bitmap bitmap = targetbitmap;
        ExtractTextClasss.SendBitmap1(bitmap,getApplicationContext(), new MyJavaClass.SuccessCallback() {
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


