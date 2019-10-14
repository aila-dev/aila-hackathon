package com.aila.ailahackathon.inspector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;

public class CustomRecognitionListener implements RecognitionListener {
    public static final String TAG = "RecognitionListener";

    public static void startListening(Context appContext) {
        // Intent to listen to user sound input and
        // return the result to the same activity.
        Intent intentSpeech = new
                Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // Use a language model based on free-form speech recognition.
        intentSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intentSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intentSpeech.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                appContext.getPackageName());

        // add Custom Listener
        CustomRecognitionListener listener = new CustomRecognitionListener();
        SpeechRecognizer sr = SpeechRecognizer.createSpeechRecognizer(appContext);
        sr.setRecognitionListener(listener);
        sr.startListening(intentSpeech);
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {
        Log.d(TAG, "onReadyForSPeech");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d(TAG, "onBeginningOfSPeech");
    }

    @Override
    public void onRmsChanged(float v) {
        Log.d(TAG, "onRmsChanged");
    }

    @Override
    public void onBufferReceived(byte[] bytes) {
        Log.d(TAG, "onBufferedReceive");
    }

    @Override
    public void onEndOfSpeech() {
        Log.d(TAG, "onEndofSpeech");

    }

    @Override
    public void onError(int i) {
        Log.e(TAG, "error " + i);
    }

    @Override
    public void onResults(Bundle bundle) {
        ArrayList<String> result = bundle.
                getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        for (String partial : result)
            text += result + "\n";
        Log.d(TAG, "Result" + text);
    }

    @Override
    public void onPartialResults(Bundle bundle) {
        Log.i(TAG, "onPartialResults");
    }

    @Override
    public void onEvent(int i, Bundle bundle) {
        Log.i(TAG, "onEvent");
    }
}
