package com.ayman.android.voicerecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void stt (View view){
        Intent s =new Intent(this,STT.class );
        startActivity(s);
    }
    public void tts (View view){
        Intent s =new Intent(this,TTS.class );
        startActivity(s);
    }


}
