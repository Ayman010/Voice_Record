package com.ayman.android.voicerecord;

import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class STT extends AppCompatActivity {
    protected static final int RESULT_SPEECH = 1;
    private EditText text;
    private Button copy, share, speak;
    String copy_content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stt);
        text = (EditText) findViewById(R.id.txtText);
        speak = (Button) findViewById(R.id.Speak);
        copy = (Button) findViewById(R.id.copy);
        share = (Button) findViewById(R.id.share);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-SA");
                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    text.setText("");
                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getApplicationContext(), "Unfortunately, this device does not support talk",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copy_content=text.getText().toString();
                ClipboardManager clipboardManager=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                clipboardManager.setText(copy_content);
                Toast.makeText(getApplicationContext(), "Copies successfully :)", Toast.LENGTH_SHORT).show();


            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copy_content=text.getText().toString();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, copy_content);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Select the application:"));

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> t = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    text.setText(t.get(0));

                }
                break;
            }

        }
    }
}
