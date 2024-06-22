package com.example.methodechannelcreaate;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class TextRecognitionManager {

    private static final String TAG = "TextRecognitionManager";

    private TextRecognizer textRecognizer;

    public TextRecognitionManager() {
        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
    }

    public void recognizeText(Bitmap bitmap, final TextRecognitionCallback callback) {
        InputImage image = InputImage.fromBitmap(bitmap, 0);
        textRecognizer.process(image)
                .addOnSuccessListener(new OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(Text visionText) {
                        // Task completed successfully
                        String resultText = visionText.getText();
                        resultText =  resultText
                                .replace("L", "1")
                                .replace("l", "|")
                                .replace("s", "5")
                                .replace("S", "5")
                                .replace("a", "8")
                                .replace("A", "8")
                                .replace("o","0")
                                .replace("O","0")
                                .replace("B","3")
                                .replace("b","2")
                                .replace("Þ","2")
                                .replace("$","3");
                        callback.onSuccess(resultText);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Task failed with an exception
                        Log.e(TAG, "Text recognition failed: " + e.getMessage());
                        callback.onError(e.getMessage());
                    }
                });
    }

    public interface TextRecognitionCallback {
        void onSuccess(String resultText);
        void onError(String errorMessage);
    }
}
