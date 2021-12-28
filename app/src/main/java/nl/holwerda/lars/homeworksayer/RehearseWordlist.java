package nl.holwerda.lars.homeworksayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

public class RehearseWordlist extends AppCompatActivity {
    TextToSpeech textToSpeechobject;
    private void speak(String fileName){
        FileManger fileManger = new FileManger();
        try {
            JSONObject jsonData = fileManger.readFile(getApplicationContext(),fileName);
            JSONArray keys = jsonData.names();

            if (keys != null) {
                for (int i = 0; i < keys.length (); i++) {
                    String key = keys.getString (i);
                    String value = jsonData.getString (key);
                    textToSpeechobject.speak(key + " " + value,1,null, "1");
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


    }
    private void initSettings(){
        textToSpeechobject.setPitch(0.9f);
        textToSpeechobject.setSpeechRate(0.1f);
        textToSpeechobject.setLanguage(Locale.getDefault());

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rehearse_wordlist);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.RecycleViewWords);
        Intent intent_from = getIntent();
        String filename = intent_from.getStringExtra("filename");
        textToSpeechobject = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == 0){
                    initSettings();
                    speak(filename);
                }
            }
        });

        FileManger fileManger = new FileManger();
        ArrayList<AdapterRecyclerItem> dataArrayList = new ArrayList<AdapterRecyclerItem>();
        try {
            JSONObject dataJson = fileManger.readFile(getApplicationContext(), filename);
            dataArrayList = fileManger.jsonToArrayListObjects(dataJson);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        AdapterRecyclerToView adapterRecycler = new AdapterRecyclerToView(dataArrayList, getApplicationContext(), filename);
        recyclerView.setAdapter(adapterRecycler);

    }
}