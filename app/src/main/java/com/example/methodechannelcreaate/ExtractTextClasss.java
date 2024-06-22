package com.example.methodechannelcreaate;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.mlkit.vision.common.InputImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ExtractTextClasss {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void SendBitmap1(Bitmap bitmapw, Context context, MyJavaClass.SuccessCallback successCallback, MyJavaClass.FailureCallback failureCallback) {
        Bitmap bitmap = bitmapw;
        InputImage image = InputImage.fromBitmap(bitmap, 0);

        TextRecognitionManager textRecognitionManager;
        textRecognitionManager = new TextRecognitionManager();
        textRecognitionManager.recognizeText(bitmap, new TextRecognitionManager.TextRecognitionCallback() {
            @Override
            public void onSuccess(String resultText) {
                Log.d("Extracted Text", resultText);
                String[] words = resultText.split("\\s+");
                ArrayList<String> filteredWords = new ArrayList<>();
                for (String word : words) {
                    if (word.length() >= 6) {
                        if (containsEnglishAlphabet(word))
                        {

                        }
                        else {
                            filteredWords.add(word);
                        }
                    }
                }

                //remove special character from first
                removeSpecialCharacters(filteredWords);

                // remove white space
                //detectedTextList= removeWhitespaceFromList(filteredWords);

                //remove special character from list
                processStrings(filteredWords);

                // Print the modified strings
                filteredWords = splitAndProcess(filteredWords);

                // Print the result
                filteredWords = splitBy7(filteredWords);
                filteredWords = checkspecialindexxx(filteredWords);
                List<String> finalDetectedTextList = filteredWords;
                List<String> processedTextList = new ArrayList<>();
                determinePercentage(filteredWords, resultList -> {
                    processedTextList.addAll(resultList);
                    for(int i = 0; i< finalDetectedTextList.size() ; i++)
                    {
                        String text = finalDetectedTextList.get(i).toString() ;
                        String percentage = processedTextList.get(i).toString() ;
                        String newtext = text+"("+percentage+")";
                        finalDetectedTextList.set(i, newtext);

                    }
                });
                successCallback.onSuccess(finalDetectedTextList);
                Log.d("haveeeee", ""+filteredWords);
            }

            @Override
            public void onError(String errorMessage) {
                // Handle error
                Log.e("Text Recognition Error", errorMessage);
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            callback.accept(newList);
        }
        else {
            callback.accept(newList);
        }
    }

    public interface SuccessCallback {
        void onSuccess(List<String> processedTextList);
    }

    public interface FailureCallback {
        void onFailure(Exception e);
    }
    private static ArrayList<String> checkspecialindexxx(ArrayList<String> inputList) {
        ArrayList<String> result = new ArrayList<>();

        for (String input : inputList) {
            if (input.contains(":")) {
                result.add(input);

            }
            else {
                String modifiedString = input.substring(0, 4) + ":" + input.substring(input.length() - 2);
                result.add(modifiedString);
            }


        }

        return result;
    }
    private static ArrayList<String> splitBy7(ArrayList<String> inputList) {
        ArrayList<String> result = new ArrayList<>();

        for (String input : inputList) {
            if (input.length() >= 7) {
                // Split the string into parts of length 7
                for (int i = 0; i < input.length(); i += 7) {
                    // Ensure the substring doesn't exceed the string length
                    String part = input.substring(i, Math.min(i + 7, input.length()));
                    result.add(part);
                }
            } else {
                // If the string is less than 7 characters, add it as is
                result.add(input);
            }
        }

        return result;
    }
    private static ArrayList<String> splitAndProcess(ArrayList<String> inputList) {
        ArrayList<String> result = new ArrayList<>();

        for (String input : inputList) {
            // Split by any special character except ':'
            String[] parts = input.split("[^a-zA-Z0-9:.]");

            // Add each part to result
            result.addAll(Arrays.asList(parts));
        }

        return result;
    }
    private static void processStrings(ArrayList<String> stringsList) {
        for (int i = 0; i < stringsList.size(); i++) {
            String originalString = stringsList.get(i);
            int indexOfColon = originalString.indexOf(':');

            if (indexOfColon != -1) { // Ensure ':' exists in the string
                String beforeColon = originalString.substring(0, indexOfColon);
                if (beforeColon.length() > 4) {
                    // Trim the string before ':' to 4 characters
                    String trimmedString = beforeColon.substring(beforeColon.length() - 4);
                    // Update the original string in the list
                    stringsList.set(i, trimmedString + originalString.substring(indexOfColon));
                }
            }
        }
    }
    private static void removeSpecialCharacters(ArrayList<String> words) {
        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            if (word.length() > 0 && !Character.isLetterOrDigit(word.charAt(0))) {
                // Remove the first character if it's a special character
                words.set(i, word.substring(1));
            }
        }
    }
    private static List<String> splitText(String text) {
        String[] words = text.split(" ");
        List<String> parts = new ArrayList<>();

        int length = words.length;
        int partSize = (length > 7) ? (int) Math.ceil((double) length / 8) : length;

        for (int i = 0; i < length; i += partSize) {
            StringBuilder part = new StringBuilder();
            for (int j = i; j < i + partSize && j < length; j++) {
                if (j > i) {
                    part.append(" ");
                }
                part.append(words[j]);
            }
            parts.add(part.toString());
        }

        return parts;
    }
    private static boolean containsEnglishAlphabet(String word) {
        return word.matches(".*[a-zA-Z]+.*");
    }

        }
