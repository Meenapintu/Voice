package com.yehigo.eagle.voiceapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yehigo.eagle.voiceapp.DBMS.AsyncDB;
import com.yehigo.eagle.voiceapp.DBMS.Words;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecognitionListener {

    public static final int MULTIPLE_PERMISSIONS = 10; // code needed.
    String[] permissions = new String[]{
            Manifest.permission.RECORD_AUDIO,
    };

     private VoiceRecognizer _voiceRecognizer ;

     private ImageButton _clearBtn,_checkBtn,_menuBtn,_listenBtn;
     private TextView _text;
      AsyncDB _db =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // support.v7

        if ( checkPermissions(MainActivity.this)) {

            _clearBtn = findViewById(R.id.clear);
            _checkBtn = findViewById(R.id.check);
            _text = findViewById(R.id.word);
            _menuBtn = findViewById(R.id.menubtn);
            _listenBtn = findViewById(R.id.listenbtn);

            _voiceRecognizer = new VoiceRecognizer(getApplicationContext());

            _listenBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _text.setHint("Please speak a word ...");

                    _voiceRecognizer.startListeningVoice(MainActivity.this);

                }
            });

            _menuBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(MainActivity.this, ListActivity.class);
                    startActivity(i);
                }
            });

            _checkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (_text.getText().toString().equals("")) {
                        _text.setHint("Press left Top Button");
                        return;
                    }
                    Words words = new Words();
                    words.word = _text.getText().toString();
                    get_db().insert(words);
                    _text.setText("");
                    _text.setHint("Press left Top Button");
                }
            });

            _clearBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _text.setText("");
                    _text.setHint("Press left Top Button");
                }
            });

            _db = new AsyncDB(getApplicationContext());
        }

    }

    public  AsyncDB get_db() {
        if(_db ==null)
            _db = new AsyncDB(getApplicationContext());
        return _db;
    }

    @Override
            public void onReadyForSpeech(Bundle params) {
                Log.d("speech", "onready: " + params.toString());

            }

            @Override
            public void onBeginningOfSpeech() {
                Log.d("speech", "beginspeech: ");

            }

            @Override
            public void onRmsChanged(float rmsdB) {
                Log.d("speech", "rmschangeed: " + rmsdB);


            }

            @Override
            public void onBufferReceived(byte[] buffer) {
                Log.d("speech", "receviedbuffer: " + Arrays.toString(buffer));


            }

            @Override
            public void onEndOfSpeech() {
                Log.d("speech", "endofspeech: ");


            }

            @Override
            public void onError(int error) {
                Log.e("speech", "error: " + error);

            }

            @Override
            public void onResults(Bundle results) {
                String text = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0);
                _text.setText(text);

                Log.d("speech", "onResults: " + results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION));


            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                Log.d("speech", "onPartialResults: " + partialResults.toString());
            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }






    private boolean checkPermissions(Activity activity) {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(activity, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permissions granted.
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                } else {
                    checkPermissions(this);
                    // no permissions granted.
                }
            }
        }
    }

}
