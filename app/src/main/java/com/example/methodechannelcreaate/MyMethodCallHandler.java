package com.example.methodechannelcreaate;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MyMethodCallHandler implements MethodChannel.MethodCallHandler {
    @Override
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
        switch (call.method) {
            case "sayHello":
                String name = call.argument("name");

                break;
            default:
                result.notImplemented();
        }
    }
}