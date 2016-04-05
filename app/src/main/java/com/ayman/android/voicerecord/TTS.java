package com.ayman.android.voicerecord;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TTS extends AppCompatActivity implements TextToSpeech.OnInitListener, AdapterView.OnItemSelectedListener {
    Button button;
    private Spinner speedSpinner,pitchSpinner;
    EditText editText;
    TextToSpeech tts;
    private static String speed="Normal";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);
        button=(Button)findViewById(R.id.voice);
        editText=(EditText)findViewById(R.id.textView4);
        speedSpinner=(Spinner)findViewById(R.id.spinner1);
        tts=new TextToSpeech(this,this);
        loadSpinnerData();
        speedSpinner.setOnItemSelectedListener( this);
    }

    @Override
    public void onInit(int status) {
        if(status==TextToSpeech.SUCCESS){
            //int result=tts.setLanguage(Locale.UK);
            int result =tts.setLanguage(Locale.forLanguageTag("ar-SA"));
            if (result==TextToSpeech.LANG_MISSING_DATA
                    ||result==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","Not Supported");
            }
            else{button.setEnabled(true);
            speakOut();}
        }
        else {Log.e("TTS", "Initialization Fail!");}

    }
        private void speakOut(){
        String text=editText.getText().toString();
        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);
    }
    private void loadSpinnerData(){
        List<String>labels=new ArrayList<String>();
        labels.add("Very Slow");
        labels.add("Slow");
        labels.add("Normal");
        labels.add("Fast");
        labels.add("Very Fast");
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,labels);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        speedSpinner.setAdapter(arrayAdapter);


    }
    private void setSpeed(){
        if (speed.equals("Very Slow")){tts.setSpeechRate(0.1f);}
        if (speed.equals("Slow")){tts.setSpeechRate(0.5f);}
        if (speed.equals("Normal")){tts.setSpeechRate(1.0f);}
        if (speed.equals("Fast")){tts.setSpeechRate(1.5f);}
        if (speed.equals("Very Fast")){tts.setSpeechRate(2.0f);}



    }
    public void voice (View view){
        setSpeed();
        speakOut();

    }
    public void onItemSelected(AdapterView<?>parent,View view,int position, long id){
        speed=parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),"you choose:"+speed,Toast.LENGTH_LONG).show();

    }
    public void onNothingSelected(AdapterView<?>arg0){}
    public void onDestroy(){if (tts !=null){
        tts.stop();
        tts.shutdown();
    }
        super.onDestroy();
    }
}
