package com.example.methodechannelcreaate;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.mlkit.vision.common.InputImage;



import java.util.function.Consumer;
public class MyJavaClass {

    public static void sayHello(String name, Context context, SuccessCallback successCallback, FailureCallback failureCallback) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ccf);
        InputImage image = InputImage.fromBitmap(bitmap, 0);
        OCRManager ocrManager = new OCRManager();
        List<String> processedTextList = new ArrayList<>();

        ocrManager.performOCR(image, new OCRManager.OCRCallback() {
            @Override
            public List<String> onOCRComplete(List<String> detectedTextList) {
                try {
                    // Process the detectedTextList here
                    detectedTextList = filterdatalist(detectedTextList);
                    detectedTextList = cleanTextList(detectedTextList);
                    detectedTextList = removeWhitespaceFromList(detectedTextList);
                    detectedTextList = modifyTextList(detectedTextList);
                    detectedTextList = sortTextListByPrefix(detectedTextList);
                    detectedTextList = processTextList(detectedTextList);
                    determinePercentage(detectedTextList, resultList -> {
                        System.out.println("Resulting List:" + resultList);
                        System.out.println("Resulting List:" + resultList.size());
                        processedTextList.addAll(resultList);
                    });

                    Log.d("kkkjjjj", "" + detectedTextList.size());
                    successCallback.onSuccess(detectedTextList);
                } catch (Exception e) {
                    failureCallback.onFailure(e);
                }

                return processedTextList;
            }
        });
    }

    public interface SuccessCallback {
        void onSuccess(List<String> processedTextList);
    }

    public interface FailureCallback {
        void onFailure(Exception e);
    }
    public static List<String> filterdatalist(List<String> textList) {
        List<String> updatedTextList = new ArrayList<>();

        for (String text : textList) {
            int length = text.length();
            if (length >=6)
            {
                updatedTextList.add(text);
            }
        }
        return updatedTextList;
    }
    public static void determinePercentage(List<String> detectedTextList, Consumer<List<String>> callback) {
        List<String> newList = new ArrayList<>();
        for (String text : detectedTextList) {
            if (text.length() >= 5) {
                long digitCount = text.chars().filter(Character::isDigit).count();
                long specialCharacterCount = text.chars().filter(c -> !Character.isLetterOrDigit(c) && !Character.isWhitespace(c)).count();
                if (digitCount >= 6) {
                    int digitScore = 4 * 20;
                    int specialCharacterScore = specialCharacterCount > 1 ? (int) (specialCharacterCount * 10) : 20;
                    int total = digitScore + specialCharacterScore;
                    String digitScoreString = total + "%";
                    newList.add(digitScoreString);
                } else {
                    int digitScore = (int) (digitCount * 15);
                    int specialCharacterScore = specialCharacterCount > 1 ? (int) (specialCharacterCount * 5) : 5;
                    int total = digitScore + specialCharacterScore;
                    String digitScoreString = total + "%";
                    newList.add(digitScoreString);
                }
            }
        }
        callback.accept(newList);
    }
    public static List<String> processTextList(List<String> textList) {
        List<String> updatedTextList = new ArrayList<>();

        for (String text : textList) {
            int length = text.length();
            for (int j = 0; j < length; j += 6) {
                int endIndex = Math.min(j + 6, length);
                String substring = text.substring(j, endIndex);
                if (substring.length() >= 6) {
                    StringBuilder sb = new StringBuilder(substring);
                    sb.insert(4, ":");
                    updatedTextList.add(sb.toString());
                } else {
                    updatedTextList.add(substring);
                }
            }
        }

        return updatedTextList;
    }
    public static List<String> sortTextListByPrefix(List<String> textList) {
        List<String> sortedTextList = new ArrayList<>(textList);

        Collections.sort(sortedTextList, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                String s1Prefix = s1.length() >= 2 ? s1.substring(0, 2) : s1;
                String s2Prefix = s2.length() >= 2 ? s2.substring(0, 2) : s2;
                return s1Prefix.compareTo(s2Prefix);
            }
        });

        return sortedTextList;
    }
    public static List<String> modifyTextList(List<String> textList) {
        List<String> modifiedTextList = new ArrayList<>();

        for (String text : textList) {
            if (!text.isEmpty()) {
                int length = text.length();
                int kk = length % 6;
                String modifiedText = text.substring(kk);
                modifiedTextList.add(modifiedText);
            } else {
                modifiedTextList.add("");
            }
        }

        return modifiedTextList;
    }
    public static List<String> removeWhitespaceFromList(List<String> textList) {
        List<String> cleanedTextList = new ArrayList<>();

        for (String text : textList) {
            String cleanedText = text.replaceAll("\\s+", "");
            cleanedTextList.add(cleanedText);
        }

        return cleanedTextList;
    }

    public static List<String> cleanTextList(List<String> textList) {
        List<String> cleanedTextList = new ArrayList<>();

        for (String text : textList) {
            String cleanedText = text.replaceAll("[^a-zA-Z0-9\\s+]", "");
            cleanedTextList.add(cleanedText);
        }

        return cleanedTextList;
    }

}