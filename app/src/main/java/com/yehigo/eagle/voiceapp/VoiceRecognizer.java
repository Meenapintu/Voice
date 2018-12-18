package com.yehigo.eagle.voiceapp;

import android.content.Context;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import static android.speech.SpeechRecognizer.createSpeechRecognizer;

public class VoiceRecognizer {

    private  Context _context;
    private  SpeechRecognizer _speechRecognizer=null;
    public VoiceRecognizer(Context context) {
        this._context = context;
    }

    public void startListeningVoice(RecognitionListener listener)
    {
        if(_speechRecognizer==null) _speechRecognizer =  createSpeechRecognizer (_context);
        _speechRecognizer.setRecognitionListener(listener);
        Intent intent = new Intent(RecognizerIntent.ACTION_VOICE_SEARCH_HANDS_FREE);
        _speechRecognizer.startListening(intent);

    }


}
